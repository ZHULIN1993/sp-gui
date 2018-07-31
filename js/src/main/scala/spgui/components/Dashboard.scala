package spgui.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala
import japgolly.scalajs.react.internal.Box
import japgolly.scalajs.react.vdom.html_<^._

object Dashboard {
  case class DashboardProps(
                             header: Option[VdomNode] = None,
                             footer: Option[VdomNode] = None,
                             grid: Option[VdomNode] = None,
                             sidebar: Option[VdomNode] = None,
                             sidebarIsAlignedLeft: Option[Boolean] = None
                           )

  class Backend($: BackendScope[DashboardProps, Unit]) {
    def render(props: DashboardProps) = {
      <.div(
        <.div(props.header.whenDefined(header => header)),
        <.div(props.grid.whenDefined(grid => grid)),
        <.div(props.footer.whenDefined(footer => footer))
      )
    }
  }

  private val component = ScalaComponent.builder[DashboardProps]("Dashboard")
      .renderBackend[Backend]
    .build

  def apply(props: DashboardProps) = component(props)

  def apply() = component(DashboardProps())
}

