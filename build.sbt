import SPSettings._

lazy val projectName = "sp-gui"

lazy val spDep = Def.setting(Seq(
  PublishingSettings.orgNameFull %%% "sp-domain" % "0.9.1-SNAPSHOT",
  PublishingSettings.orgNameFull %%% "sp-comm" % "0.9.1-SNAPSHOT"
))

lazy val buildSettings = Seq(
  name         := projectName,
  description  := "The core UI for sequence planner",
  version      := "0.9.1-SNAPSHOT",
  libraryDependencies ++= domainDependencies.value,
  libraryDependencies ++= guiDependencies.value,
  libraryDependencies ++= spDep.value,
  scmInfo := Some(ScmInfo(
    PublishingSettings.githubSP(projectName),
    PublishingSettings.githubscm(projectName)
    )
  )
)

lazy val spgui = project.in(file("."))
  .settings(defaultBuildSettings: _*)
  .settings(buildSettings: _*)
  .settings(jsSettings: _*)
  .enablePlugins(ScalaJSPlugin)
