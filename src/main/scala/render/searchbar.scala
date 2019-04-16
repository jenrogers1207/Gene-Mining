package render

import scala.scalajs.js.Date

import org.scalajs.dom._
import scala.scalajs.js.JSON
import scala.scalajs.js
import org.scalajs.dom
import org.scalajs.dom.experimental._
import dom.html
import org.scalajs.dom.html.{Div, Button, Input}
import org.scalajs.dom.raw.MouseEvent
import scala.util.Try
import scalajs.js.annotation.JSExport
import scalatags.JsDom.all._
import textmine._
import org.scalajs.dom
import dom.html
import scalajs.js.annotation.JSExport
import scalatags.JsDom.all._

@JSExport
object Inputs extends{
  @JSExport
  def main(target: Node) = {
    val box = input(
      `type`:="text",
      `class`:="form-control",
      placeholder:="Search by gene symbol or variant id..."
    ).render

    val output = span.render

    box.onkeyup = (e: dom.Event) => {
      output.textContent =
        box.value.toUpperCase
    }

    val searchB =  button(
      `class`:="btn btn-outline-secondary",
      "GO"
    ).render

    searchB.onclick = (e: dom.Event) => {
    //  textmine.GetUrlContent.requestFile(box.value)
      textmine.inputMatch.matching(box.value)
    }

    target.appendChild(
      div(
        h1("What's that Gene?"),
        div(
          `class`:= "input-group mb-3",
          box, searchB),
        div(output)
      ).render
    )
  }
}