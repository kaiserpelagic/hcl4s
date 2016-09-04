package hcl

abstract class Parser[A] {
  def parse(a: A): Either[String, HCL]
}

