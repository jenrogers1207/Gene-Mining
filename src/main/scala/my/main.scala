package my

import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.dom._



@JSExportTopLevel("my.Main")
object Main{

  def main(args: Array[String]): Unit = {
   // textmine.ProcessorMachine.processMain()
    val div = document.createElement("div")
    val searchDiv = document.getElementById("main").appendChild(div)

    render.Inputs.main(searchDiv)
  }
}


