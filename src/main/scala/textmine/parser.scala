package textmine

import org.scalajs.dom
import org.scalajs.dom.console
import upickle.default.read
import upickle.default._
import upickle.default.{macroRW, ReadWriter => RW}
import ujson.Js
import scala.scalajs.js._
import scala.scalajs.js.{Dictionary, JSON}
import ProcessorMachine._
import org.clulab.processors.corenlp.CoreNLPProcessor
import org.clulab.processors.shallownlp.ShallowNLPProcessor
import org.clulab.serialization.DocumentSerializer
import org.clulab.processors.{Document, Processor}
import org.clulab.struct.DirectedGraphEdgeIterator

object Parser {

  def parseFile(response:String): Unit ={

    val file = JSON.parse(response)

    def getArray(fi:Array[Dictionary[String]],check:String, keyVal:String):Array[Vector[String]]  = {
      def checker(f: Dictionary[String], c:String, k:String): Boolean = {
        if(c == k) {
          f.contains(k)}
        else f(c) == "Y"
      }
      fi.map(x=> {
        if(checker(x, check, keyVal)) Vector(x("source"), x(keyVal))
        else Vector("null")
      }).filter(f=> f(0) != "null")
    }

    val ids = getArray(file.resultList.result.asInstanceOf[Array[Dictionary[String]]],"id", "id")
    val pmcids = getArray(file.resultList.result.asInstanceOf[Array[Dictionary[String]]], "pmcid", "pmcid")
    val textMineIds = getArray(file.resultList.result.asInstanceOf[Array[Dictionary[String]]], "hasTextMinedTerms", "id")


    val papers = pmcids.map(x=> {
      val queryId = x(1)
      println(queryId)
      val url = "https://www.ebi.ac.uk/europepmc/webservices/rest/"+queryId+"/fullTextXML"
      val xhr = new dom.XMLHttpRequest()
      xhr.open("GET", url)
      xhr.onload = { (e: dom.Event) =>
        if (xhr.status == 200) {
          var xml = xhr.responseXML

          var title = xml.getElementsByTagName("article-title")
          var body = xml.getElementsByTagName("body")

          //println("papes", xml.getElementsByTagName("article-title"))

        //  println(title(0).textContent)
        //  println("body", body(0).textContent)
        //  ProcessorMachine.processMain(body(0).textContent)
        //  val proc:Processor = new CoreNLPProcessor(withDiscourse = ShallowNLPProcessor.WITH_DISCOURSE)

         // val doc = proc.annotate(body(0).textContent)

         // println(doc)
        }
      }
      xhr.send()
    })

  }

  def getPubs(pm:Array[Vector[String]]): Unit = {

   // textmine.GetUrlContent.requestFile("https://www.ebi.ac.uk/europepmc/webservices/rest/search?query="+box.value+"&format=json")
  }
}

class pubQuery
