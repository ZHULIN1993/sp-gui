package spgui

import org.scalajs.dom.document
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

/**
  * Created by alexa on 26/07/2018.
  */

object Main {
  def main(args: Array[String]): Unit = {
    val Hello = ScalaComponent.builder[String]("Hello")
      .render_P(name => <.div("Hello there ", name))
      .build

    <.div(Hello("Frank")).renderIntoDOM(document.getElementById("root"))
    /*val Hello =
      ScalaComponent.builder[String]("Hello")
        .render_P(name => <.div("Hello there ", name))
        .build

    // Usage:
    <.div(Hello("Mathew")).renderIntoDOM(document.body)*/
  }
}
