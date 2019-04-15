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
  def searchVariant()
}
