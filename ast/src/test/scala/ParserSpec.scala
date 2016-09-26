package hcl

import scalaparsers.{ParseState,Pos,Supply}
import org.scalatest.{FlatSpec,Matchers}
import scalaz.\/

class ParserSpec extends FlatSpec with Matchers {

  it should "parse an hcl file with bindings" in {
    val input = """
      a = true 
      x = false
      y = "foo"
    """
    val res = runParse(input)
    res should equal (\/.right(List(List(Binding("a",HBool(true)), Binding("x",HBool(false)), Binding( "y",HString("foo")))))) 
  }

  it should "parse and hcl file with an array" in {
    val input = """
      ["foo","bar","baz"]
    """

    val res = runParse(input)
    res should equal (\/.right(List(List(HString("foo"), HString("bar"), HString("baz")))))
  }

  def runParse(i: String) = parse(i) match {
    case Left(e) => \/.left(e.pretty.toString)
    case Right((_, r)) => \/.right(r)
  }

  def parse (i: String) = 
    Parser.parser.run(ParseState(loc = Pos.start("", ""), input = i,s = (), layoutStack = List()), Supply.create)
}
