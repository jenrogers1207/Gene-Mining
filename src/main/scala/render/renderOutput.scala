package render

import org.scalajs.dom._
import scala.scalajs.js.JSON
import scala.scalajs.js
import org.scalajs.dom
import org.scalajs.dom
import dom.html
import scalatags.JsDom.all._
import scala.scalajs.js.annotation._
import scala.scalajs.js.{Dictionary, JSON}
import scalatags.JsDom.TypedTag

//@JSExport
@JSExportTopLevel("Outputs")
object Outputs extends{
  // @JSExport
  def consequenceRender(varData:scala.scalajs.js.Array[scala.scalajs.js.Dynamic]) = {

    val varDiv = div(
      `class`:="variant-wrapper",
      h4("Associated Phenotypes")
    ).render

    val consDivs: Unit = varData.map(v=> {
      //val consDivs: scala.scalajs.js.Array[scalatags.JsDom.TypedTag[html.Div]] = varData.map(v=> {
      console.log("v", v)
      val sig = v.clinical_significances
      val dn = v.disease_names
      println(sig)
      val spanSig = span(`class`:= "badge badge-info").render
      spanSig.textContent = sig.toString

      val varAllelle = div(dn.toString + " : ", spanSig, `class`:="alle-cons").render
      varDiv.appendChild(varAllelle).render
    })

    document.getElementById("main").appendChild(varDiv).render

  }

  def varDetailRender(varData:scala.scalajs.js.Array[scala.scalajs.js.Dynamic]) = {

    val varDiv = div(
      `class`:="variant-wrapper",
      h4("Associated Phenotypes")
    ).render

    val consDivs: Unit = varData.map(v=> {
      //val consDivs: scala.scalajs.js.Array[scalatags.JsDom.TypedTag[html.Div]] = varData.map(v=> {
    //  console.log("v", v)
      val sig = v.clinical_significances
      val dn = v.disease_names
     // println(sig)
      val spanSig = span(`class`:= "badge badge-info").render
      spanSig.textContent = sig.toString

      val varAllelle = div(dn.toString + " : ", spanSig, `class`:="alle-cons").render
      varDiv.appendChild(varAllelle).render
    })

    document.getElementById("main").appendChild(varDiv).render

  }
}
