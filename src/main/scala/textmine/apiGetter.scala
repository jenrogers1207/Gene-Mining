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


import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow

class Gene(name:String, entrezgene:String, symbol:String, taxid:String){


  def idToMim(callback: (String) => Unit):Unit = {
    var url = "http://mygene.info/v2/gene/"+entrezgene+"?fields=MIM"
    val f = Ajax.get (url)
    f.onComplete {
      case Success (xhd) =>
        val json = js.JSON.parse (xhd.responseText)

        callback(json.MIM.toString)

      case Failure (e) => println (e.toString)
    }
  }

  def findReactomePathways(urlVal:String):Unit={
    val url = "https://reactome.org/ContentService/data/mapping/OMIM/"+urlVal+"/pathways?species=9606"
    val f = Ajax.get (url)
    f.onComplete{
      case Success (xhd) =>
        val json = js.JSON.parse(xhd.responseText)
        console.log(json)


      case Failure (e) =>  println (e.toString)
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

object GetUrlContent {

  def searchGene(query:String) = {
    var url = "http://mygene.info/v3/query?q="+query
    val f = Ajax.get(url)

    f.onComplete{
      case Success(xhd) =>
        val json=js.JSON.parse(xhd.responseText)
        val hits = json.hits.asInstanceOf[Array[Unit]]
        val ob = hits(0).asInstanceOf[Dictionary[Dynamic]]
        val geneQuery = new Gene(ob("name").toString, ob("entrezgene").toString, ob("symbol").toString, ob("taxid").toString)
       // geneQuery.idToMim(ob("entrezgene").toString)
        geneQuery.idToMim(geneQuery.findReactomePathways)
        geneQuery.requestPub(ob("symbol").toString)
      case Failure(e) => println(e.toString)
    }
  }


  def searchVariant() = {
  println("variant")
  }


}
