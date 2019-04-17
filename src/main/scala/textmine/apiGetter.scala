package textmine

import org.scalajs.dom
import org.scalajs.dom.experimental._
import org.scalajs.dom.console
import upickle.default.read
import upickle.default._
import upickle.default.{macroRW, ReadWriter => RW}
import ujson.Js

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

class Gene(n:String, entrez:String, sym:String, taxon:String, omim:String){

  def getId(value:String): String = {
    value match {
      case "OMIM" =>  omim
      case "ENTREZ" => entrez
      case "SYMBOL" => sym
      case "TAXON" => taxon
      case "NAME" => n
    }
  }


  def requestPub(qvalue:String) = {
    var url = "https://www.ebi.ac.uk/europepmc/webservices/rest/search?query="+qvalue+"&format=json"

    val f = Ajax.get(url)
    f.onComplete{
      case Success(xhd) =>
        val json=js.JSON.parse(xhd.responseText)
        console.log(json)
      case Failure(e) => println(e.toString)
    }
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

    def searchGene(query: String): Unit = {

      httpCall(urlBuilder("getGeneIds", query), responseText => {

        //The callback hells begins
        //Initial search for gene ids. create new gene
        val json = js.JSON.parse(responseText)
        val hits = json.hits.asInstanceOf[Array[Unit]]
        val ob = hits(0).asInstanceOf[Dictionary[Dynamic]]
        val geneQuery = new Gene(ob("name").toString, ob("entrezgene").toString, ob("symbol").toString, ob("taxid").toString, ob("MIM").toString)
        println("match?",geneQuery.getId("SYMBOL"))

          //GET PATHWAYS ThAT GENE IS INVOLVED IN
          httpCall(urlBuilder("getPathIds", geneQuery.getId("OMIM")), responseText=> {
            val json = js.JSON.parse(responseText).asInstanceOf[Array[Dictionary[Dynamic]]]
            console.log(json)
            val pathways = json.map(p=> {
              new Pathway(p("dbId").toString, p("stId").toString, p("displayName").toString, p("hasDiagram").asInstanceOf[Boolean], p("speciesName").toString)
            })
            console.log(pathways)
          })
          //GET PUBLICATION IDS
          httpCall(urlBuilder("getPubIds", geneQuery.getId("ENTREZ")), responseText => {
            println("responseText", responseText)
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
       
        console.log(alleleAn)
        console.log(typeOf(alleleAn))


      })
    }


  }
