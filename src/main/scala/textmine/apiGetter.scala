package textmine

import org.scalajs.dom
import org.scalajs.dom.console
import upickle.default.read
import upickle.default._
import upickle.default.{macroRW, ReadWriter => RW}
import ujson.Js
import scala.scalajs.js._
import scala.scalajs.js.{Dictionary, JSON}
import scala.util.Random
import requests._

class Gene(name:String, entrezgene:String, symbol:String, taxid:String){

}


object GetUrlContent {

  def requestFile(qvalue:String) = {
    var url = "https://www.ebi.ac.uk/europepmc/webservices/rest/search?query="+qvalue+"&format=json"
    val xhr = new dom.XMLHttpRequest()
    xhr.open("GET", url)
    xhr.onload = { (e: dom.Event) =>
      if (xhr.status == 200) {
        Parser.parseFile(xhr.responseText)
      }
    }
    xhr.send()
  }
  def searchVariant() = {
  println("variant")
  }

  def searchGene(query:String) = {

    var url = "http://mygene.info/v3/query?q="+query
    // val r = requests.get(url)
    val xhr = new dom.XMLHttpRequest()
    var text = ""
    xhr.open("GET", url)
    xhr.onload = { (e: dom.Event) =>
      if (xhr.status == 200) {
        //  Parser.parseFile(xhr.responseText)
        text = text + xhr.responseText
       // println(xhr.responseText)
        val test = JSON.parse(xhr.responseText)
        val hits = test.hits.asInstanceOf[Array[Unit]]
        val ob = hits(0).asInstanceOf[Dictionary[String]]
        //println(ob("symbol"))
        val g = new Gene(ob("name"), ob("entrezgene"), ob("symbol"), ob("taxid").toString)
        println(g)

      }
    }
    xhr.send()

    neo4jTest()


  }
  def neo4jTest(): Unit ={

 //   GET /db/data/ HTTP/1.1
   // Host: localhost:7474
  //  Accept: application/json; charset=UTF-8
   // cache-control: no-cache

    val xhr = new dom.XMLHttpRequest()

    xhr.open("GET", "http://localhost:7474/db/data")
     // xhr.setRequestHeader("content-type", "JSON")
    xhr.setRequestHeader("Access-Control-Allow-Origin", "*")
    xhr.onload = { (e: dom.Event) =>
      if (xhr.status == 200) {
        //  Parser.parseFile(xhr.responseText)
        println(xhr)
      }
    }
    xhr.send()
  }

  def searchTest(test:String):String = {
    var url = "http://mygene.info/v3/query?q="+test
    // val r = requests.get(url)
    val xhr = new dom.XMLHttpRequest()
    var text = ""
    xhr.open("GET", url)
    xhr.onload = { (e: dom.Event) =>
      if (xhr.status == 200) {
      //  Parser.parseFile(xhr.responseText)
        text = text + xhr.responseText
      }
    }
    xhr.send()
    return text
  }
}
