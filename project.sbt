
organization in Global := "kaiserpelagic"

scalaVersion in Global := "2.11.8"

lazy val hcl4s = project.in(file(".")).aggregate(ast)

lazy val ast = project
