package spgui.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala
import japgolly.scalajs.react.internal.Box
import japgolly.scalajs.react.vdom.html_<^._

object Dashboard {
  case class DashboardProps(
                             header: Option[VdomElement] = None,
                             footer: Option[VdomElement] = None,
                             grid: Option[VdomElement] = None,
                             sidebar: Option[VdomElement] = None,
                             sidebarIsAlignedLeft:Option[Boolean] = None
                           )

  class Backend($: BackendScope[DashboardProps, Unit]) {
    def render(p: DashboardProps) = {
      <.div(p.header).when(p.header.nonEmpty)
      <.div(p.grid).when(p.grid.nonEmpty)
      <.div(p.footer).when(p.footer.nonEmpty)
    }
  }

  private val component = ScalaComponent.builder[DashboardProps]("Dashboard")
    .renderBackend[Backend]
    .build

  def apply(props: DashboardProps) = component(props)

  def apply() = component(DashboardProps())
}

