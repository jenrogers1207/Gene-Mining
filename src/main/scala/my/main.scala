package my

import scala.scalajs.js.annotation.JSExportTopLevel
import render.ScalaJSExample
import org.scalajs.dom
import dom.html
import org.scalajs.dom.html.{Div, Button, Input}
import org.scalajs.dom.raw.MouseEvent
import scala.util.Try
import scalajs.js.annotation.JSExport
import scalatags.JsDom.all._
import textmine.GetUrlContent

@JSExportTopLevel("my.Main")
object Main{

  def main(args: Array[String]): Unit = {

    textmine.GetUrlContent.requestFile("GJB2")
    //render.ScalaJSExample.main(document.getElementById("main"));
  }
}


