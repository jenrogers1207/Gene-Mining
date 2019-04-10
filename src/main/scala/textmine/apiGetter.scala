package textmine

import org.scalajs.dom
import org.scalajs.dom.console
import upickle.default.read
import upickle.default._
import upickle.default.{macroRW, ReadWriter => RW}
import ujson.Js

import scala.scalajs.js.{Dictionary, JSON}
import scala.util.Random

object GetUrlContent {

  def requestFile(queryVal:String) = {

    val xhr = new dom.XMLHttpRequest()
    xhr.open("GET",
      "https://www.ebi.ac.uk/europepmc/webservices/rest/search?query="+queryVal+"&format=json"
    )
    xhr.onload = { (e: dom.Event) =>
      if (xhr.status == 200) {
        // buildBoard(xhr.responseText)
       // println("status good", xhr.responseText)
        println(xhr.responseText)
       // var test = xhr.responseText
        val file = JSON.parse(xhr.responseText)
        val ujsontest = ujson.read(xhr.responseText)

        val resultList:Array[Dictionary[Unit]] = file.resultList.result.asInstanceOf[Array[Dictionary[Unit]]]
        val idArray:Array[Array[String]] = resultList.map((d:Dictionary[Unit])=> {
          val id = d("id").asInstanceOf[String]
         // val name = data.name.asInstanceOf[String]
          val source = d("source").asInstanceOf[String]
          val title = d("title").asInstanceOf[String]
          Array(id, source, title)
        })
       // val test =
        println(idArray)

      }
    }
    xhr.send()
  }

  def parseFile(fileString:String) = {
    var test = read[qFormat](fileString)
    println(test)
  }

  case class qFormat(version: String, hitCount: Int, nextCursorMark: String, request: requestFormat, resultList:listFormat)
  object qFormat{
    implicit def rw: RW[qFormat] = macroRW
  }

  case class listFormat(result:Array[resultFormat])
  object listFormat{
    implicit def rw: RW[listFormat] = macroRW
  }
//"pubYear":"2019","journalIssn":"0165-5876; 1872-8464; ","pageInfo":"136-140","pubType":"review; journal article","isOpenAccess":"N","inEPMC":"N","inPMC":"N","hasPDF":"N","hasBook":"N","citedByCount":0,"hasReferences":"N","hasTextMinedTerms":"N","hasDbCrossReferences":"N","hasLabsLinks":"N","hasTMAccessionNumbers":"N","firstPublicationDate":"2019-01-25"},
  case class resultFormat(id:String, source:String, pmid:String, doi:String, title:String, authorString:String, journalTitle:String, journalVolume:Option[String])
  object resultFormat{
    implicit def rw: RW[resultFormat] = macroRW
  }
//"request":{"query":"GJB2","resultType":"lite","synonym":false,"cursorMark":"*","pageSize":25,"sort":""},
  case class requestFormat(query:String, resultType:String, synonym:Boolean, cursorMark:String, pageSize:Int, sort:String)
  object requestFormat{
    implicit def rw: RW[requestFormat] = macroRW
  }
}
