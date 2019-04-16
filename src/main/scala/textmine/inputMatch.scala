package textmine

object inputMatch{

  abstract class QueryOb

  case class Gene(name:String)
  case class Variant(name:String)

  def matching(query:String) = {

    query match{
      case x if x startsWith "rs" =>
        GetUrlContent.searchVariant()
      case _ =>
        GetUrlContent.searchGene(query)
    }
  }



}
