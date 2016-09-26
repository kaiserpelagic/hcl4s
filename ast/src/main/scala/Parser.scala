package hcl

import scalaz._,Scalaz._

object Parser {
  
  val P = new scalaparsers.Parsing[Unit] {}
  import P._

  lazy val parseScope = attempt(skipLWS >> choice(bind | list) << skipHWS)

  lazy val bind : Parser[List[Scope]] = {
    val b = (skipLWS >> bindParse) << skipHWS 
    b.map2(attempt(newline >> b).many)(_ :: _)
  }

  lazy val bindParse =
    attempt(ident << skipLWS << '=' << skipLWS).map2(primitive) { (x, v) => Binding(x,v) }

  lazy val ident: Parser[String] = for {
    n <- satisfy(c => Character.isLetter(c)).map2(takeWhile(Character.isLetter))(_ +: _)
  } yield n.mkString

  lazy val whitespace = satisfy(c => c.isWhitespace && c != '\r' && c != '\n').skip
  
  lazy val digit = satisfy(d => d.isDigit)

  lazy val charEscMagic: Map[Char, Char] = "bfnrt\\\"'".zip("\b\f\n\r\t\\\"'").toMap

  lazy val escapeCode = choice(charEscMagic.toSeq.map { case (c,d) => ch(c) as d } :_*)
  
  lazy val stringEscape = ch('\\') >> {
    (satisfy(_.isWhitespace).skipSome >> ch('\\')).as(None) | escapeCode.map(Some(_))
  }

  lazy val stringLetter = satisfy(c => (c != '"') && (c != '\\') && (c > 22.toChar))

  lazy val stringChar = stringLetter.map(Some(_)) | stringEscape

  lazy val string: Parser[String] = stringChar.many.between('"','"').map(_.sequence[Option,Char].getOrElse(List()).mkString)

  lazy val list = (ch('[') >> (skipLWS >> primitive << skipLWS).sepBy(ch(',') << skipLWS) << ']')

  lazy val primitive: Parser[HCL] = choice(
    word("true") >> unit(HBool(true)),
    word("false") >> unit(HBool(false)),
    string.map(HString(_)),
    list.map(HArray(_)) 
  )

  lazy val newline = satisfy(c => c == '\r' || c == '\n').skip

  lazy val skipHWS: Parser[Unit] = whitespace.skipMany
  
  lazy val skipLWS = (newline | whitespace).skipMany
  
  lazy val parser = parseScope.many

  def takeWhile(p: Char => Boolean): Parser[List[Char]] = satisfy(p).many
}
