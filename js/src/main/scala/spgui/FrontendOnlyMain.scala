package spgui

import org.scalajs.dom.document
import scala.scalajs.js.annotation.{JSExport,JSExportTopLevel}

/** This is only for pure frontend development in sp-gui, of things that don't need a backend turned on
  */
object FrontendOnlyMain extends App {
  /** Export instance to be visible as raw JS-name to the top-level module */
  @JSExportTopLevel("spgui.FrontendOnlyMain")
  protected def getInstance(): this.type = this

  /** Load the widgets and render the layout
    * into the index.html-section with id
    * Export main() to be visible as raw JS-name
    */
  @JSExport
  def main(): Unit = {
    FrontendOnlyWidgets.loadWidgets()
    Layout().renderIntoDOM(document.getElementById("spgui-root"))
  }
}