package spgui

import java.util.UUID

import org.scalajs.dom.document
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import spgui.components.{Dashboard, Grid, Header, Layout}

/** Main class, initialized with scalaJSUseMainModuleInitializer := true
  *
  */
object Main {
  def main(args: Array[String]): Unit = {
    val Hello = ScalaComponent.builder[String]("Hello")
      .render_P(name => <.div("Hello there ", name))
      .build

    <.div(Hello("Frank")).renderIntoDOM(document.getElementById("root"))
    val defaultLayout =
      Layout(
        Some(
          <.div(
            Dashboard(props =
              Dashboard.DashboardProps(
                header = Some(Header("Head")),
                footer = Some(Header("Footer")),
                grid = Some(Grid())
              )
            )
          ).render
        )
      )
    defaultLayout.renderIntoDOM(document.getElementById("root"))
  }
}
