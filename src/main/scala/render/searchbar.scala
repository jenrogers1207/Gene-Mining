package render

import scala.scalajs.js.Date

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
import textmine.GetUrlContent
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
      placeholder:="Search by gene symbol..."
    ).render

    val output = span.render

    box.onkeyup = (e: dom.Event) => {
      //println("e",e)
      output.textContent =
        box.value.toUpperCase
    }

    val searchB =  button("GO").render

    searchB.onclick = (e: dom.Event) => {
      textmine.GetUrlContent.requestFile(box.value)
    }

    target.appendChild(
      div(
        h1("What's that Gene?"),
       // div(),
        div(box, searchB),
        div(output)
      ).render
    )
  }
}