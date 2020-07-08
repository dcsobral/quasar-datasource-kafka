import scala.collection.Seq

ThisBuild / crossScalaVersions := Seq("2.12.10")
ThisBuild / scalaVersion := (ThisBuild / crossScalaVersions).value.head

ThisBuild / githubRepository := "quasar-datasource-kafka"

ThisBuild / homepage := Some(url("https://github.com/precog/quasar-datasource-kafka"))

ThisBuild / scmInfo := Some(ScmInfo(
  url("https://github.com/precog/quasar-datasource-kafka"),
  "scm:git@github.com:precog/quasar-datasource-kafka.git"))

ThisBuild / publishAsOSSProject := true

// Include to also publish a project's tests
lazy val publishTestsSettings = Seq(
  Test / packageBin / publishArtifact := true)

lazy val quasarVersion = Def.setting[String](
  managedVersions.value("precog-quasar"))

val specs2Version = "4.8.3"

lazy val root = project
  .in(file("."))
  .settings(noPublishSettings)
  .aggregate(core)

lazy val core = project
  .in(file("core"))
  .settings(addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"))
  .settings(
    name := "quasar-datasource-kafka",

    quasarPluginName := "url",

    quasarPluginQuasarVersion := quasarVersion.value,

    quasarPluginDatasourceFqcn := Some("quasar.datasource.kafka.KafkaDatasourceModule$"),

    quasarPluginDependencies ++= Seq(
      "com.github.fd4s" %% "fs2-kafka" % "1.0.0",
      "org.specs2" %% "specs2-core" % specs2Version % Test,
      "org.specs2" %% "specs2-scalacheck" % specs2Version % Test,
      "org.specs2" %% "specs2-scalaz" % specs2Version % Test,
      "com.precog" %% "quasar-foundation" % quasarVersion.value % Test classifier "tests"))
  .enablePlugins(QuasarPlugin)