package spgui.components

import java.util.UUID
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object Layout {

  case class LayoutProps(id: UUID)

  class Backend($: BackendScope[LayoutProps, Unit]) {
    def render(p: LayoutProps) = {
      <.div(Dashboard())
    }
  }

  private val component = ScalaComponent.builder[LayoutProps]("Layout")
    .renderBackend[Backend]
    .build

  def apply(id: UUID) = component(LayoutProps(id))

  def apply() = component(LayoutProps(UUID.randomUUID()))
}

