package spgui.widgets.bootstrap

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import Components._

object TestBootstrap {

  private class Backend($: BackendScope[Unit, Unit]) {
    def render() = {
      <.div(
        primaryButton("Hi"),

        button("Hi", "btn btn-primary", Callback.empty),
        button("hi-outlined", "btn btn-outline-primary", Callback.empty),
        outlineInfoButton("hi")
      )
    }
  }

  private val component = ScalaComponent.builder[Unit]("Bootstrap Test")
    .renderBackend[Backend]
    .build

  def apply() = spgui.SPWidget(spwb => component())
}
