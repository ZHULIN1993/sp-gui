package spgui.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala
import japgolly.scalajs.react.internal.Box
import japgolly.scalajs.react.vdom.html_<^._
import spgui.components.Content.SPContent

object Dashboard {
  case class DashboardProps(
                             header: Option[SPContent] = None,
                             footer: Option[SPContent] = None,
                             grid: Option[SPContent] = None,
                             sidebar: Option[SPContent] = None,
                             sidebarIsAlignedLeft:Option[Boolean] = None
                           )

  class Backend($: BackendScope[DashboardProps, Unit]) {
    def render(p: DashboardProps) = {
      <.div(
        p.header(
          <.p("hi")
        )
      ).when(p.header.nonEmpty)
      <.div("Hi Mathew!!!")
    }
  }

  private val component = ScalaComponent.builder[DashboardProps]("Dashboard")
    .renderBackend[Backend]
    .build

  def apply(props: DashboardProps) = component(props)

  def apply() = component(DashboardProps())
}

