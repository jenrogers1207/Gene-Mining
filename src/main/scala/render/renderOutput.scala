package render

import org.scalajs.dom._

import scala.scalajs.js.JSON
import scala.scalajs.js
import org.scalajs.dom
import org.scalajs.dom
import dom.html
import scalatags.JsDom.all.{h4, _}

import scala.scalajs.js.annotation._
import scala.scalajs.js.{Dictionary, JSON}
import scalatags.JsDom.TypedTag
import my._

//@JSExport
@JSExportTopLevel("Outputs")
object Outputs extends{
  // @JSExport
  def consequenceRender(varData:scala.scalajs.js.Array[scala.scalajs.js.Dynamic]) = {

    val varDiv = div(
      `class`:="variant-wrapper",
      h4("Associated Phenotypes")
    ).render

    varData.map(v=> {
      //val consDivs: scala.scalajs.js.Array[scalatags.JsDom.TypedTag[html.Div]] = varData.map(v=> {

      val sig = v.clinical_significances
      val dn = v.disease_names
      val spanSig = span(`class`:= "badge badge-info").render
      spanSig.textContent = sig.toString

      val varAllelle = div(dn.toString + " : ", spanSig, `class`:="alle-cons").render
      varDiv.appendChild(varAllelle).render
    })

    document.getElementById("variant").appendChild(varDiv).render

  }

  def varDetailRender(varData:Variant) = {

    val varDiv = div(
      `class`:="variant-detail-wrapper"
    ).render
    val spanAnch = span(`class`:="badge badge-primary").render
    spanAnch.textContent = "Anchor: "+ varData.anchor
    val spanGene = span(`class`:="badge badge-primary").render
    spanGene.textContent = "Associated Gene: "+ varData.gene
    val spanType = span(`class`:="badge badge-primary").render
    spanType.textContent = "Type: "+ varData.variantType
    val divWrap = div(spanAnch, spanType, spanGene).render

    document.getElementById("variant").appendChild(divWrap).render
  }

  def geneDetailRender(gData:Gene) = {

    val varDiv = div(
      `class`:="gene-detail-wrapper"
    ).render

    console.log(gData.getId("SYMBOL"))

    val spanName = span(`class`:="badge badge-warning").render
    spanName.textContent = "Name: "+ gData.getId("NAME")
    val spanSym = span(`class`:="badge badge-warning").render
    spanSym.textContent = "Symbol: "+ gData.getId("SYMBOL")
    val spanEn = span(`class`:="badge badge-warning").render
    spanEn.textContent = "Entrez Id: "+ gData.getId("ENTREZ")
    val spanTax = span(`class`:="badge badge-warning").render
    spanTax.textContent = "Species Id: "+ gData.getId("TAXON")
    val divWrap = div(spanName, spanSym, spanEn, spanTax).render

    document.getElementById("gene").appendChild(divWrap).render
  }

  def pubRender(pData:PubCollection):html.Div = {

    val divWrap = div(
      `class`:="pub-detail-wrapper",
      h4(pData.idArray.length + " publications found for gene:"),
        h4(pData.pmArray.length + " full text publications found for gene:")
    ).render

    document.getElementById("gene").appendChild(divWrap).render

    return divWrap
  }

  def paperRender(divB: html.Div, pData:String) = {

    val blurb = div(pData).render

    divB.appendChild(blurb).render
  }

  def pathRender(pData:scala.scalajs.js.Array[Pathway]) = {

    val divWrap = div(
      `class`:="path-detail-wrapper",
        h4("Pathway the Gene is Involved in:")
    ).render

    pData.map(v=> {
      //val consDivs: scala.scalajs.js.Array[scalatags.JsDom.TypedTag[html.Div]] = varData.map(v=> {

      val spanP = span(`class`:= "badge badge-dark").render
      spanP.textContent = v.name.toString

      divWrap.appendChild(spanP).render
    })

    document.getElementById("gene").appendChild(divWrap).render

  }
}
