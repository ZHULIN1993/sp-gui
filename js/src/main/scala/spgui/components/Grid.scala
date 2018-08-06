package spgui.components

import java.util.UUID

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object Grid {
  case class Widget(id: UUID, children: VdomElement = <.div())
  case class GridProps(widgets: Seq[Widget])

  class Backend($: BackendScope[GridProps, Unit]) {

    def render(p: GridProps) = {
      <.div(
        p.widgets.map{ widget =>
          <.div(^.key := widget.id.toString, widget.children)
        }.toTagMod
      )
    }
  }

  private val component = ScalaComponent.builder[GridProps]("Grid")
    .renderBackend[Backend]
    .build

  def apply() = component(GridProps(Seq()))

  def apply(p: GridProps) = component(p)
}
