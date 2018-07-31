package spgui

import org.scalajs.dom.document
import scala.scalajs.js.annotation.{JSExport,JSExportTopLevel}

/**
  *  This is only for pure frontend development, of things that don't need a backend turned on
  */
object FrontendOnlyMain {
  def main(args: Array[String]): Unit = {
    FrontendOnlyLoadingWidgets.loadWidgets
    Layout().renderIntoDOM(document.getElementById("spgui-root"))
  }
}
