package textmine

import org.scalajs.dom
import org.scalajs.dom.experimental._
import org.scalajs.dom.{console, document}
import scala.scalajs.js._
import scala.scalajs.js.{Dictionary, JSON}
import scala.util.Random
import requests._
import scala.scalajs.js
import scala.scalajs.js.Dynamic.global
import util._
import dom.ext._
import scala.concurrent.Future
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import my._



object GetRestContent {


  def urlBuilder(query: String, id: String): String = {
    query match {
      case "getOmim" => "http://mygene.info/v2/gene/" + id + "?fields=MIM"
      case "getPathIds" => "https://reactome.org/ContentService/data/mapping/OMIM/" + id + "/pathways?species=9606"
      case "getGeneIds" => "http://mygene.info/v3/query?q=" + id + "&fields=symbol,name,taxid,entrezgene,ensemblgene,MIM"
      case "getPubIds" => "https://www.ebi.ac.uk/europepmc/webservices/rest/search?query="+id+"&format=json"
      case "getVarSNP" => "https://api.ncbi.nlm.nih.gov/variation/v0/beta/refsnp/" + id
      case "getPubXML" => "https://www.ebi.ac.uk/europepmc/webservices/rest/"+id+"/fullTextXML"
    }
  }

    def httpCall(url: String, callback: (String) => Unit): Unit = {
      val f = Ajax.get(url)
      f.onComplete {
        case Success(xhr) => callback(xhr.responseText)
        case Failure(e) => println(e.toString)
      }
    }

    def searchGene(query: String, variant: Variant = null): Unit = {
      println("variant in search gene ", variant);
      httpCall(urlBuilder("getGeneIds", query), responseText => {
        //The callback hells begins
        //Initial search for gene ids. create new gene
        val json = js.JSON.parse(responseText)
        val hits = json.hits.asInstanceOf[Array[Unit]]
        val ob = hits(0).asInstanceOf[Dictionary[Dynamic]]
        val geneQuery = new Gene(ob("name").toString, ob("entrezgene").toString, ob("symbol").toString, ob("taxid").toString, ob("MIM").toString)

        render.Outputs.geneDetailRender(geneQuery)

          //GET PATHWAYS ThAT GENE IS INVOLVED IN
          httpCall(urlBuilder("getPathIds", geneQuery.getId("OMIM")), responseText=> {
            val json = js.JSON.parse(responseText).asInstanceOf[Array[Dictionary[Dynamic]]]

            val pathways = json.map(p=> {
              new Pathway(p("dbId").toString, p("stId").toString, p("displayName").toString, p("hasDiagram").asInstanceOf[Boolean], p("speciesName").toString)
            })
            render.Outputs.pathRender(pathways)
          })
          //GET PUBLICATION IDS
          httpCall(urlBuilder("getPubIds", geneQuery.getId("ENTREZ")), responseText => {

            val pubCollection = Parser.parseFile(responseText)
            val pubCountDiv = render.Outputs.pubRender(pubCollection)

            val papers = pubCollection.pmArray.map(pm=> {
              val queryId = pm(1)
              val url = urlBuilder("getPubXML", queryId)

              val f = Ajax.get(url)
              f.onComplete({
                case Success(xhr) => Parser.parseXML(xhr, (titleText, absText)=>{
                  render.Outputs.paperRender(pubCountDiv, titleText)
                  render.Outputs.paperRender(pubCountDiv, absText)
                })
                case Failure(e) => println(e.toString)
              })
            })

            val terms = pubCollection.tmArray.map(pm=> {
              val queryId = pm(1)
              println(pm(0), pm(1))


             // val url = "https://www.ebi.ac.uk/europepmc/webservices/rest/"+pm(0)+"/"+queryId+"/textMinedTerms&format=json"
              val url = "https://www.ebi.ac.uk/europepmc/webservices/rest/"+pm(0)+"/"+queryId+"/textMinedTerms?page=1&pageSize=25&format=json"
              val xhr = new dom.XMLHttpRequest()
              xhr.open("GET", url)
              xhr.onload = { e: dom.Event =>
                if (xhr.status == 200) {
                  val json = js.JSON.parse(xhr.responseText)
                  console.log("parsing??",json.semanticTypeCountList)

                }
              }
              xhr.send()

            })
          })
      })
    }


    def searchVariant(rsValue: String) = {
      println("variant", rsValue)
      val rsdigit = rsValue.split("[Ss]")
      httpCall(urlBuilder("getVarSNP", rsdigit(1)), responseText => {
        val json = js.JSON.parse(responseText)
        val snapshot = json.primary_snapshot_data
        val alleleAn = snapshot.allele_annotations.asInstanceOf[Array[Dictionary[Dynamic]]]
        val assembly = alleleAn(0)("assembly_annotation").asInstanceOf[Array[Dictionary[Dynamic]]]
        val associatedGenes = assembly(0)("genes").asInstanceOf[Array[Dictionary[Dynamic]]]
        val clinicalSignificance = alleleAn.filter(f=> {
         val clinical = f("clinical").asInstanceOf[Array[Dynamic]]
          clinical.length != 0
        }).flatMap(m=> m("clinical").asInstanceOf[Array[Dynamic]])


        val variant = new Variant(rsValue, associatedGenes(0)("locus").toString, clinicalSignificance, snapshot.variant_type.toString, snapshot.anchor.toString)

        render.Outputs.varDetailRender(variant)

        render.Outputs.consequenceRender(clinicalSignificance)

        searchGene(variant.gene, variant)

      })
    }


  /*
    def neo4jTest(): Unit ={

      val xhr = new dom.XMLHttpRequest()
      val body = ""
      xhr.open("GET", "https://cors-anywhere.herokuapp.com/http://http://192.168.155.98.131.4:7474/db/data")

      xhr.onload = { (e: dom.Event) =>
        if (xhr.status == 200) {

          println(xhr)
        }
      }
      xhr.send(body)
    }*/


  }
