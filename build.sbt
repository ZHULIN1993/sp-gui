import SPSettings._

lazy val projectName = "sp-gui"
lazy val projectVersion = "0.9.2-SNAPSHOT"

lazy val spDep = Def.setting(Seq(
  PublishingSettings.orgNameFull %%% "sp-domain" % "0.9.1-SNAPSHOT",
  PublishingSettings.orgNameFull %%% "sp-comm" % "0.9.1-SNAPSHOT"
))

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

val jsFiles = Seq(
  ProvidedJS / "ganttApp.js",
  ProvidedJS / "bundle.js",
  ProvidedJS / "bundle.min.js"
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
  .jsSettings(
    jsSettings,
    jsDependencies ++= jsFiles,
    libraryDependencies ++= domainDependencies.value,
    libraryDependencies ++= guiDependencies.value,
    libraryDependencies ++= spDep.value
  )

lazy val spgui_jvm = spgui.jvm
lazy val spgui_js = spgui.js