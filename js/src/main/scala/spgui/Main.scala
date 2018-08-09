package spgui

import java.util.UUID

import org.scalajs.dom.document
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

/** Main class, initialized with scalaJSUseMainModuleInitializer := true
  *
  */
object Main {
  def main(args: Array[String]): Unit = {
    FrontendOnlyWidgets.loadWidgets
    Layout().renderIntoDOM(document.getElementById("spgui-root"))
  }
}
