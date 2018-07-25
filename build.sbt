import SPGuiSettings._

import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin.autoImport.npmDevDependencies


enablePlugins(ScalaJSPlugin)
// Enable the plugin from lihaoy
enablePlugins(WorkbenchPlugin)
// Enable the scalajs-bundler plugin from scalacenter
/* leads to problems with one resource or the other
workbenchDefaultRootObject := Some((
  "js/target/scala-2.12/classes/index.html",
  "js/target/scala-2.12/"
))
*/

lazy val projectName = "sp-gui"
lazy val projectVersion = "0.9.11"

// Dependencies on other SP-projects
lazy val spDep = Def.setting(Seq(
  PublishingSettings.orgNameFull %%% "sp-domain" % "0.9.10",
  PublishingSettings.orgNameFull %%% "sp-comm" % "0.9.11"
))

// Build Settings
lazy val buildSettings = Seq(
  name         := projectName,
  description  := "The core UI for sequence planner",
  version      := projectVersion,
  scmInfo := Some(ScmInfo(
    PublishingSettings.githubSP(projectName),
    PublishingSettings.githubscm(projectName)
    )
  )
)

lazy val root = project.in(file("."))
  .aggregate(spgui_jvm, spgui_js)
  .settings(defaultBuildSettings)
  .settings(buildSettings)
  .settings(
    publish              := {},
    publishLocal         := {},
    publishArtifact      := false,
    Keys.`package`       := file("")
  )

lazy val spgui = crossProject.crossType(CrossType.Full).in(file("."))
  .settings(defaultBuildSettings: _*)
  .settings(buildSettings: _*)
  .jvmSettings()
  .jsConfigure(_.enablePlugins(ScalaJSBundlerPlugin))
  .jsSettings(
    jsSettings,
    libraryDependencies ++= domainDependencies.value,
    libraryDependencies ++= guiDependencies.value,
    libraryDependencies ++= spDep.value,
    // Add npm dependenices with scalajs-bundler to be used with `sbt compile`
    npmDependencies in Compile ++= npmBundlerDependencies,
    // Add npm devDependenices with scalajs-bundler to be used with `sbt compile`
    npmDevDependencies in Compile ++= npmBundlerDevDependencies,
    webpackBundlingMode := BundlingMode.LibraryAndApplication()
    // Add webpack-configuration file to be used with `sbt fastOptJS`
    //webpackConfigFile := Some(baseDirectory.value / "webpack.config.js")
  )

lazy val spgui_jvm = spgui.jvm
lazy val spgui_js = spgui.js
