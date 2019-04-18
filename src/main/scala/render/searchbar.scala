package render

import scala.scalajs.js.Date

import org.scalajs.dom._
import scala.scalajs.js.JSON
import scala.scalajs.js
import org.scalajs.dom
import org.scalajs.dom
import dom.html
import scalatags.JsDom.all._
import scala.scalajs.js.annotation._
import my._


//@JSExport
@JSExportTopLevel("Inputs")
object Inputs extends{
 // @JSExport
  def main(target: Node) = {
    val box = input(
      `type`:="text",
      `class`:="form-control",
      placeholder:="Search by gene symbol or variant id..."
    ).render

    val output = span.render


    val searchB =  button(
      `class`:="btn btn-outline-secondary",
      "GO"
    ).render

    searchB.onclick = (e: dom.Event) => {
      textmine.inputMatch.matching(box.value)
    }

    val title = span.render
    title.textContent= "What's That Gene?"

    box.onkeyup = (e: dom.Event) => {
      output.textContent =
        box.value.toUpperCase
      if (box.value.startsWith("rs")) title.textContent = "What's That Variant?"
      else title.textContent = "What's That Gene?"
    }

    target.appendChild(
      div(
       h1(title),
        div(
          `class`:= "input-group mb-3",
          box, searchB),
        div(output)
      ).render
    )
  }
}