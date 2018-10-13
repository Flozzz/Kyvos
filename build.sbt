import sbt.Keys.{libraryDependencies, _}

name := "KyvosGatling"

version := "0.1"

scalaVersion := "2.12.7"

/*
resolvers in ThisBuild ++= Seq(
  "Quantium Remote Mirror" at "https://repo.build.quantium.com.au/artifactory/simple/cs-maven-remotes/"
)*/

lazy val root = (project in file("."))
  .settings(
    publishArtifact := false,
//    libraryDependencies += "org.apache.hive" % "hive-jdbc" % "3.1.0",
    libraryDependencies += "org.apache.logging.log4j" % "log4j" % "1.2.17"
  /* libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.25",
    libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.25" % Test*/
  )
