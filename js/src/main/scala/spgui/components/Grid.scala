package spgui.components

import spgui.components.Content.SPContent
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object Grid {
  case class GridProps(widgets: Seq[SPContent])

  class Backend($: BackendScope[GridProps, Unit]) {

    def render(p: GridProps) = {
      p.widgets.foreach{c => <.div(c)}
    }
  }

  private val component = ScalaComponent.builder[GridProps]("Grid")
    .renderBackend[Backend]
    .build

  def apply() = component
}
