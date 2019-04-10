package my

import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom._
import scala.scalajs.js.JSON
import scala.scalajs.js
import org.scalajs.dom
import dom.html
import org.scalajs.dom.html.{Div, Button, Input}
import org.scalajs.dom.raw.MouseEvent
import scala.util.Try
import scalajs.js.annotation.JSExport
import scalatags.JsDom.all._
import org.scalajs.dom.raw.MouseEvent
import scalajs.js.annotation.JSExport
import textmine.GetUrlContent
import render.Inputs

@JSExportTopLevel("my.Main")
object Main{

  def main(args: Array[String]): Unit = {

   // textmine.GetUrlContent.requestFile("GJB2")

    val div = document.createElement("div");
    val searchDiv = document.getElementById("main").appendChild(div)

    render.Inputs.main(searchDiv)
  }
}


