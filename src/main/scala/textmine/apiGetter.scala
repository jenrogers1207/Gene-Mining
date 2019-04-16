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
    println("Gene")
    val test = searchTest(query)

    println(test)

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
