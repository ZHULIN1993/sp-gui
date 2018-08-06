package spgui.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object Header {
  private val component = ScalaComponent.builder[String]("Header")
    .render_P(r =>
      <.div(r)
    )
    .build

  def apply() = component("Empty Header")

  def apply(text: String) = component(text)
}

