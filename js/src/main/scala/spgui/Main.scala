package spgui

import org.scalajs.dom.document
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import spgui.components.Layout

/** Main class, initialized with scalaJSUseMainModuleInitializer := true
  *
  */
object Main {
  def main(args: Array[String]): Unit = {
    val Hello = ScalaComponent.builder[String]("Hello")
      .render_P(name => <.div("Hello there ", name))
      .build

    <.div(Hello("Frank")).renderIntoDOM(document.getElementById("root"))
    Layout().renderIntoDOM(document.getElementById("root"))
  }
}
