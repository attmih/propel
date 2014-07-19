name := "propel"

version := "1.0"

scalaVersion := "2.10.4"

// --- Dependencies ---

libraryDependencies ++= Seq(
  "com.googlecode.kiama" %% "kiama" % "1.6.0",
  "org.scalatest" %% "scalatest" % "2.2.0" % "test",
  "com.h2database" % "h2" % "1.4.178"
)
