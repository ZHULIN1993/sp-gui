package spgui.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object Dashboard {
  case class DashboardProps(
                             header: Option[Content] = None,
                             footer: Option[Content] = None,
                             grid: Option[Seq[Content]] = None,
                             sidebar: Option[Content] = None,
                             sidebarAlignLeft: Option[Boolean] = None
                           )

  class Backend($: BackendScope[DashboardProps, Unit]) {
    def render(p: DashboardProps) = {
//      <.div(p.header).when(p.header.nonEmpty)
      <.div("Hi Mathew!!!")
    }
  }

  private val component = ScalaComponent.builder[DashboardProps]("Dashboard")
    .renderBackend[Backend]
    .build

  def apply(props: DashboardProps) = component(props)

  def apply() = component(DashboardProps())
}

