package spgui

import org.scalajs.dom.document
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

import scala.scalajs.js.annotation.{JSExport, JSExportTopLevel}

object Main extends App {
  @JSExportTopLevel("spgui.Main")
  protected def getInstance(): this.type = this

  @JSExport
  def main(): Unit = {
    val Hello =
      ScalaComponent.builder[String]("Hello")
        .render_P(name => <.div("Hello there ", name))
        .build

    <.div(Hello("Mathew")).renderIntoDOM(document.getElementById("spgui-root"))
  }
}

