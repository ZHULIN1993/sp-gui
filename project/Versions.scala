import sbt._
import Keys._

/**
  * Created by alexa on 24/07/2018.
  * All Dependency-versions in one file.
  * If you need to update one dependency,
  * this is the place to be.
  *
  *
  * Declare global dependency versions here to avoid mismatches in multi part dependencies.
  *
  */
object Versions {
  /** VersionNumbers for Project-Dependencies */
  object ProjectVersion {
    lazy val scala = "2.12.3"
    lazy val log4js = "1.4.10"
  }

  /** VersionNumbers for Domain-Dependencies */
  object DomainVersion {
    lazy val playJson = "2.6.0"
    lazy val playJsonDerivedCodecs = "4.0.0"
    lazy val scalaJavaTime = "2.0.0-M12"
    lazy val scalaParser = "1.0.5"
  }

  /** VersionNumbers for Dependencies used in different */
  object CrossVersion {
    lazy val scalaTest = "3.0.1"
  }

  /** VersionNumbers for Comm-Dependencies */
  object CommVersion {
    lazy val akka = "2.5.3"
    lazy val slf4j = "1.7.7"
    lazy val akkaKryoSerialization = "0.5.1"
    lazy val avro4s = "1.8.0"
  }

  /** VersionNumbers for Gui-Dependencies */
  object GuiVersion {
    lazy val scalajsReact = "1.1.1"
    lazy val scalaCSS = "0.5.3"
    lazy val diode = "1.1.2"
    lazy val scalarx = "0.3.2"
    lazy val scalajsD3 = "0.3.4"
    lazy val uTest = "0.4.7"
    lazy val scalaDom = "0.9.3"
    lazy val monocleCore = "1.4.0"
    lazy val monocleMacro = "1.4.0"
    lazy val fs2Core = "0.10.0-M7"
  }

  /** VersionNumbers for Npm-Dependencies added by scalajs-bundler*/
  object NpmVersion {

  }

}
