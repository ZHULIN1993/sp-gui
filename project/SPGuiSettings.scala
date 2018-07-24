import sbt._
import Keys._
import sbt.{Developer, ScmInfo, url}
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import com.typesafe.sbt.SbtPgp.autoImport._
import xerial.sbt.Sonatype.autoImport._
import Versions.{CrossVersion, ProjectVersion, GuiVersion, CommVersion, DomainVersion}

/**
  * Scala-sbt project settings.
  * Declares dependencies and versions, scala compiler options and default sbt-setting
  */
object SPGuiSettings {

  val defaultBuildSettings = Seq(
    organization := PublishingSettings.orgNameFull,
    homepage     := Some(PublishingSettings.githubSP("")),
    licenses     := PublishingSettings.mitLicense,
    scalaVersion := ProjectVersion.scala,
    scalacOptions ++= scalacOpt,
    resolvers ++= projectResolvers,
    useGpg := true,
    publishArtifact in Test := false,
    publishMavenStyle := true,
    publishTo := PublishingSettings.pubTo.value,
    pomIncludeRepository := { _ => false },
    sonatypeProfileName := PublishingSettings.groupIdSonatype,
    developers := creditsTo
  )

  /** Options for the scala compiler */
  lazy val scalacOpt = Seq(
    //"-Xlint",
    "-unchecked",
    "-deprecation",
    "-feature",
    "-language:implicitConversions",
    "-language:postfixOps",
    "-Ypartial-unification"
  )

  lazy val projectResolvers: Seq[Resolver] = Seq(
    //Resolver.file("local", file(Path.userHome.absolutePath + "/.ivy2/local"))(Resolver.ivyStylePatterns),
    Resolver.sonatypeRepo("public"),
    Resolver.typesafeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  )

  /**
    * These dependencies are shared between JS and JVM projects
    * the special %%% function selects the correct version for each project
    */
  lazy val domainDependencies = Def.setting(Seq(
    "org.scalatest" %%% "scalatest" % CrossVersion.scalaTest % "test",
    "org.scala-lang.modules" %%% "scala-parser-combinators" % DomainVersion.scalaParser,
    "com.typesafe.play" %%% "play-json" % DomainVersion.playJson,
    "org.julienrf" %%% "play-json-derived-codecs" % DomainVersion.playJsonDerivedCodecs,
    "io.github.cquiroz" %%% "scala-java-time" % DomainVersion.scalaJavaTime
  ))
  // "org.joda" % "joda-convert" % "1.8.2" add this to jvm-side

  /** Dependencies use for comm */
  lazy val commDependencies = Def.setting(Seq(
    "com.typesafe.akka" %% "akka-actor" % CommVersion.akka,
    "com.typesafe.akka" %% "akka-cluster" % CommVersion.akka,
    "com.typesafe.akka" %% "akka-cluster-tools" % CommVersion.akka,
    "com.typesafe.akka" %% "akka-testkit" % CommVersion.akka,
    "org.slf4j" % "slf4j-simple" % CommVersion.slf4j,
    "com.github.romix.akka" %% "akka-kryo-serialization" % CommVersion.akkaKryoSerialization,
    "org.scalatest" %% "scalatest" % CrossVersion.scalaTest % "test",
    "com.sksamuel.avro4s" %% "avro4s-core" % CommVersion.avro4s
  ))

  /** Dependencies only used by the JS project (note the use of %%% instead of %%) */
  val guiDependencies = Def.setting(Seq(
    "com.github.japgolly.scalajs-react" %%% "core" % GuiVersion.scalajsReact,
    "com.github.japgolly.scalajs-react" %%% "extra" % GuiVersion.scalajsReact,
    "com.github.japgolly.scalacss" %%% "core" % GuiVersion.scalaCSS,
    "com.github.japgolly.scalacss" %%% "ext-react" % GuiVersion.scalaCSS,
    "io.suzaku" %%% "diode" % GuiVersion.diode,
    "io.suzaku" %%% "diode-react" % GuiVersion.diode,
    "org.scala-js" %%% "scalajs-dom" % GuiVersion.scalaDom,
    "com.lihaoyi" %%% "scalarx" % GuiVersion.scalarx,
    "org.singlespaced" %%% "scalajs-d3" % GuiVersion.scalajsD3,
    "org.scalatest" %%% "scalatest" % CrossVersion.scalaTest % "test",
    "com.lihaoyi" %%% "utest" % GuiVersion.uTest % Test,
    "com.github.julien-truffaut" %%%  "monocle-core"  % GuiVersion.monocleCore,
    "com.github.julien-truffaut" %%%  "monocle-macro" % GuiVersion.monocleMacro,
    "co.fs2" %%% "fs2-core" % GuiVersion.fs2Core
  ))

  /** Dependencies only used by the scalajs-bundler,
    * as would be installed by webpack and npm in other cases
    */
  lazy val npmBundlerDependencies = Seq(

  )

  lazy val jsSettings = Seq(
    testFrameworks += new TestFramework("utest.runner.Framework")
    // addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
  )

  lazy val creditsTo = List(
    Developer(
      id = "kristoferb",
      name = "kristofer Bengtsson",
      email = "kristofer@sekvensa.se",
      url   = url("https://github.com/kristoferB")
    ),
    Developer(
      id = "m-dahl",
      name = "Martin Dahl",
      email = "martin.dahl@chalmers.se",
      url   = url("https://github.com/m-dahl")
    ),
    Developer(
      id = "patrikm",
      name = "Patrik Bergagård",
      email = "Patrik.Bergagard@alten.se",
      url   = url("https://github.com/patrikm")
    ),
    Developer(
      id = "ashfaqfarooqui",
      name = "Ashfaq Farooqui",
      email = "ashfaqf@chalmers.se",
      url   = url("https://github.com/ashfaqfarooqui")
    ),
    Developer(
      id = "edvardlindelof",
      name = "Edvard Lindelöf",
      email = "edvardlindelof@gmail.com",
      url   = url("https://github.com/edvardlindelof")
    ),
    Developer(
      id = "marptt",
      name = "Martin Petterson",
      email = "laxenmeflaxen@gmail.com",
      url   = url("https://github.com/marptt")
    ),
    Developer(
      id = "ellenkorsberg",
      name = "Ellen Korsberg",
      email = "korsberg.ellen@gmail.com",
      url   = url("https://github.com/ellenkorsberg")
    ),
    Developer(
      id = "alfredbjork",
      name = "Alfred Björk",
      email = "-",
      url = url("https://github.com/alfredbjork")
    ),
    Developer(
      id = "giuinktse7",
      name = "Jonathan Krän",
      email = "-",
      url = url("https://github.com/giuinktse7")
    ),
    Developer(
      id    = "aleastChs",
      name  = "Alexander Åstrand",
      email = "aleast@student.chalmers.se",
      url   = url("https://github.com/aleastChs")
    )
  )
}
