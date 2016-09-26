

name := "ast"

resolvers += Resolver.bintrayRepo("oncue", "releases")

scalacOptions ++= Seq(
  "-feature",
  "-deprecation",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials",
  "-language:experimental.macros"
)

libraryDependencies ++= Seq(
  "org.slf4j"       % "slf4j-api"                 % "1.7.12",
  "ch.qos.logback"  % "logback-classic"           % "1.1.3",
  "org.scalaz"     %% "scalaz-core"               % "7.1.2",
  "org.scalaz"     %% "scalaz-concurrent"         % "7.1.2",
  "org.scalacheck" %% "scalacheck"                % "1.12.3" % "test",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "oncue.ermine"   %% "ermine-parser" % "0.2.1-2"
)

