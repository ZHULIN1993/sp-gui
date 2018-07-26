package spgui.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object Header extends Content("Header") {

  class Backend($: BackendScope[Unit, Unit]) {
    def render() = {
      <.div()
    }
  }

  private val component = ScalaComponent.builder[Unit]("Header")
    .renderBackend[Backend]
    .build

  def apply() = component
}

