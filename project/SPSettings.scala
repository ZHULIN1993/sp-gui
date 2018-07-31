import sbt._
import Keys._
import sbt.{Developer, ScmInfo, url}

import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import com.typesafe.sbt.SbtPgp.autoImport._
import xerial.sbt.Sonatype.autoImport._

object SPSettings {

  val defaultBuildSettings = Seq(
    organization := PublishingSettings.orgNameFull,
    homepage     := Some(PublishingSettings.githubSP("")),
    licenses     := PublishingSettings.mitLicense,
    scalaVersion := versions.scala,
    scalacOptions ++= scalacOpt,
    resolvers ++= projectResolvers,
    useGpg := true,
    publishArtifact in Test := false,
    publishMavenStyle := true,
    publishTo := PublishingSettings.pubTo.value,
    pomIncludeRepository := { _ => false },
    sonatypeProfileName := PublishingSettings.groupIdSonatype,
    developers := List(
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
        id    = "aleastChs",
        name  = "Alexander Åstrand",
        email = "aleast@student.chalmers.se",
        url   = url("https://github.com/aleastChs")
      )
    )
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

  /** Declare global dependency versions here to avoid mismatches in multi part dependencies */
  object versions {
    val scala = "2.12.3"
    val scalaDom = "0.9.3"
    val scalajsReact = "1.1.1"
    val scalaCSS = "0.5.3"
    val log4js = "1.4.10"
    val diode = "1.1.2"
    val uTest = "0.4.7"
    val scalarx = "0.3.2"
    val scalaD3 = "0.3.4"
    val scalaTest = "3.0.1"
    val akka = "2.5.3"
  }

  /**
    * These dependencies are shared between JS and JVM projects
    * the special %%% function selects the correct version for each project
    */
  lazy val domainDependencies = Def.setting(Seq(
    "org.scalatest" %%% "scalatest" % versions.scalaTest % "test",
    "org.scala-lang.modules" %%% "scala-parser-combinators" % "1.0.5",
    "com.typesafe.play" %%% "play-json" % "2.6.0",
    "org.julienrf" %%% "play-json-derived-codecs" % "4.0.0",
    "io.github.cquiroz" %%% "scala-java-time" % "2.0.0-M12"
  ))
  // "org.joda" % "joda-convert" % "1.8.2" add this to jvm-side



  /** Dependencies use for comm */
  lazy val commDependencies = Def.setting(Seq(
    "com.typesafe.akka" %% "akka-actor" % versions.akka,
    "com.typesafe.akka" %% "akka-cluster" % versions.akka,
    "com.typesafe.akka" %% "akka-cluster-tools" % versions.akka,
    "com.typesafe.akka" %% "akka-testkit" % versions.akka,
    "org.slf4j" % "slf4j-simple" % "1.7.7",
    "com.github.romix.akka" %% "akka-kryo-serialization" % "0.5.1",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
    "com.sksamuel.avro4s" %% "avro4s-core" % "1.8.0"
  ))

  /** Dependencies only used by the JS project (note the use of %%% instead of %%) */
  val guiDependencies = Def.setting(Seq(
    "com.github.japgolly.scalajs-react" %%% "core" % versions.scalajsReact,
    "com.github.japgolly.scalajs-react" %%% "extra" % versions.scalajsReact,
    "com.github.japgolly.scalacss" %%% "core" % versions.scalaCSS,
    "com.github.japgolly.scalacss" %%% "ext-react" % versions.scalaCSS,
    "io.suzaku" %%% "diode" % versions.diode,
    "io.suzaku" %%% "diode-react" % versions.diode,
    "org.scala-js" %%% "scalajs-dom" % versions.scalaDom,
    "com.lihaoyi" %%% "scalarx" % versions.scalarx,
    "org.singlespaced" %%% "scalajs-d3" % versions.scalaD3,
    "org.scalatest" %%% "scalatest" % versions.scalaTest % "test",
    "com.lihaoyi" %%% "utest" % versions.uTest % Test,
    "com.github.julien-truffaut" %%%  "monocle-core"  % "1.4.0",
    "com.github.julien-truffaut" %%%  "monocle-macro" % "1.4.0",
    "co.fs2" %%% "fs2-core" % "0.10.0-M7"
  ))

  /** Dependencies only used by the scalajs-bundler,
    * as would be installed by webpack and npm in other cases
    */
  lazy val npmBundlerDependencies = Seq(
    "bootstrap" -> "~3.3.7",
    "chart.js" -> "~2.5.0",
    "paths-js" -> "~0.4.5",
    "font-awesome" -> "~4.7.0",
    "jquery" -> "~3.1.1",
    "jsoneditor" -> "~5.6.0",
    "react" -> "~15.3.2",
    "react-dom" -> "~15.3.2",
    "react-grid-layout" -> "~0.13.9",
    "angular" -> "~1.6.4",
    "angular-gantt" -> "~1.3.3",
    "moment" -> "~2.18.1",
    "angular-moment" -> "~1.0.1"
  )

  lazy val npmDevBundlerDependencies = Seq(
    "css-loader" -> "~0.26.1",
    "file-loader" -> "~0.9.0",
    "json-loader" -> "~0.5.4",
    "style-loader" -> "~0.13.1",
    "url-loader" -> "~0.5.7",
    "webpack" -> "~3.5.5"
  )

  lazy val jsSettings = Seq(
    testFrameworks += new TestFramework("utest.runner.Framework"),
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
  )
}
