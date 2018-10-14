
name := "Kyvos"

version := "0.2"

scalaVersion := "2.12.7"

val http4sVersion = "0.18.19"

/*
resolvers in ThisBuild ++= Seq(
  "Quantium Remote Mirror" at "https://repo.build.quantium.com.au/artifactory/simple/cs-maven-remotes/"
)*/

lazy val root = (project in file("."))
  .settings(
    publishArtifact := false,
    libraryDependencies++=Seq("org.http4s" %% "http4s-dsl" % http4sVersion,
    "org.http4s" %% "http4s-blaze-server" % http4sVersion,
    "org.http4s" %% "http4s-blaze-client" % http4sVersion),
    scalacOptions ++= Seq("-Ypartial-unification")
  )


