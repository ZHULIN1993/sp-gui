import Versions._
import com.typesafe.sbt.SbtPgp.autoImport._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt.{Developer, url, _}
import xerial.sbt.Sonatype.autoImport._

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

  lazy val commDependencies = Def.setting(Seq(
    "com.typesafe.akka" %% "akka-actor" % CommVersion.akka,
    "com.typesafe.akka" %% "akka-cluster" % CommVersion.akka,
    "com.typesafe.akka" %% "akka-cluster-tools" % CommVersion.akka,
    "com.typesafe.akka" %% "akka-testkit" % CommVersion.akka
  ))

  /**
    * These dependencies are shared between JS and JVM projects
    * the special %%% function selects the correct version for each project
    */
  lazy val domainDependencies = Def.setting(Seq(
    "com.typesafe.play" %% "play-json" % "2.6.7",
    "org.julienrf" %%% "play-json-derived-codecs" % "4.0.1",
    "org.scala-lang.modules" %%% "scala-parser-combinators" % "1.1.1",
    "io.github.cquiroz" %%% "scala-java-time" % "2.0.0-M13"
  ))


  // "org.joda" % "joda-convert" % "1.8.2" add this to jvm-side

  /** Dependencies only used by the JS project (note the use of %%% instead of %%) */
  val guiDependencies = Def.setting(Seq(
    "org.scala-js" %%% "scalajs-dom" % GuiVersion.scalaDom,
    "com.github.japgolly.scalajs-react" %%% "core" % GuiVersion.scalajsReact,
    "com.github.japgolly.scalajs-react" %%% "extra" % GuiVersion.scalajsReact,
    "com.github.japgolly.scalacss" %%% "ext-react" % GuiVersion.scalaCSS,
    "com.github.japgolly.scalacss" %%% "core" % GuiVersion.scalaCSS,
    "io.suzaku" %%% "diode" % GuiVersion.diode,
    "io.suzaku" %%% "diode-react" % GuiVersion.diode,
    "com.lihaoyi" %%% "scalarx" % GuiVersion.scalarx,
    "co.fs2" %%% "fs2-core" % GuiVersion.fs2Core
  ))

  /** Dependencies only used by the scalajs-bundler,
    * as would be installed by webpack and npm in other cases
    */
  lazy val npmBundlerDependencies = Seq(
    "react" -> NpmVersion.react,
    "react-dom" -> NpmVersion.reactDom,
    "react-grid-layout" -> NpmVersion.reactGridLayout,
    "bootstrap" -> NpmVersion.bootstrap,
    "jquery" -> NpmVersion.jQuery,
    "popper.js" -> NpmVersion.popperJS
  )

  lazy val npmBundlerDevDependencies = Seq()

  lazy val jsSettings = Seq()

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
