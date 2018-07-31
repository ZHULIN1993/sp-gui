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
    val defaultLayout =
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

    Layout(defaultLayout).renderIntoDOM(document.getElementById("spgui-root"))
  }
}
