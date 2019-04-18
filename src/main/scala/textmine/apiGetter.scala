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

class Pathway(dbId:String, stId:String, name:String, hasDiagram: Boolean, species:String){

 def loadPathway() = {

 }

}

class Gene(n:String, entrez:String, sym:String, taxon:String, omim:String) {

  def getId(value: String): String = {
    value match {
      case "OMIM" => omim
      case "ENTREZ" => entrez
      case "SYMBOL" => sym
      case "TAXON" => taxon
      case "NAME" => n
    }
  }
}


class Variant(rsId:String, associatedGene:String, clinical:Array[Dynamic], varType:String, anchor:String)





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


object GetRestContent {


  def urlBuilder(query: String, id: String): String = {
    query match {
      case "getOmim" => "http://mygene.info/v2/gene/" + id + "?fields=MIM"
      case "getPathIds" => "https://reactome.org/ContentService/data/mapping/OMIM/" + id + "/pathways?species=9606"
      case "getGeneIds" => "http://mygene.info/v3/query?q=" + id + "&fields=symbol,name,taxid,entrezgene,ensemblgene,MIM"
      case "getPubIds" => "https://www.ebi.ac.uk/europepmc/webservices/rest/search?query="+id+"&format=json"
      case "getVarSNP" => "https://api.ncbi.nlm.nih.gov/variation/v0/beta/refsnp/" + id
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
       // println("match?",geneQuery.getId("SYMBOL"))

          //GET PATHWAYS ThAT GENE IS INVOLVED IN
          httpCall(urlBuilder("getPathIds", geneQuery.getId("OMIM")), responseText=> {
            val json = js.JSON.parse(responseText).asInstanceOf[Array[Dictionary[Dynamic]]]
           // console.log(json)
            val pathways = json.map(p=> {
              new Pathway(p("dbId").toString, p("stId").toString, p("displayName").toString, p("hasDiagram").asInstanceOf[Boolean], p("speciesName").toString)
            })
           // console.log(pathways)
          })
          //GET PUBLICATION IDS
          httpCall(urlBuilder("getPubIds", geneQuery.getId("ENTREZ")), responseText => {
           // println("responseText", responseText)
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
        console.log("snapshot",snapshot)
        val clinicalSignificance = alleleAn.filter(f=> {
         val clinical = f("clinical").asInstanceOf[Array[Dynamic]]
          clinical.length != 0
        }).flatMap(m=> m("clinical").asInstanceOf[Array[Dynamic]])

        render.Outputs.consequenceRender(clinicalSignificance)

        val variant = new Variant(rsValue, associatedGenes(0)("locus").toString, clinicalSignificance, snapshot.variant_type.toString, snapshot.anchor.toString)
        searchGene(associatedGenes(0)("locus").toString, variant)

      })
    }


  }
