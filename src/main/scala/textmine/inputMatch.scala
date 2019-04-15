package textmine

object inputMatch{

  abstract class QueryOb

  case class Gene(name:String)
  case class Variant(name:String)

  def matching(query:String) = {

    match{
      case Variant(query) if query.startsWith("rs") =>
        GetUrlContent
      case Gene(query) if
    }
  }

}
