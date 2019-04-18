package my

import scala.scalajs.js.{Array, Dynamic}

class Pathway(dbId:String, st:String, n:String, hasDiagram: Boolean, species:String){

  val name = n
  val stId = st

  def loadPathway() = {

  }
}

class PubCollection(id:Array[Vector[String]], pm:Array[Vector[String]], tm:Array[Vector[String]]){
  val idArray = id
  val pmArray = pm
  val tmArray = tm
}

class Gene(n:String, entrez:String, sym:String, taxon:String, omim:String) {

  def getId(value: String): String = {
    value match {
      case "OMIM" => omim
      case "ENTREZ" => entrez
      case "SYMBOL" => sym
      case "TAXON" => taxon
      case "NAME" => n
    }
  }
}

class Variant(rs:String, assG:String, clinical:Array[Dynamic], varType:String, anc:String){
  val rsId = rs
  val gene = assG
  val clinRel = clinical
  val variantType = varType
  val anchor = anc
}
