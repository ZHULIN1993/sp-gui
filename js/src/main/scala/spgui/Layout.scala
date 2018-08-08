package spgui

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import spgui.circuit.SPGUICircuit
import spgui.dashboard.Dashboard
import spgui.dragging.Dragging
import spgui.menu.SPMenu

object Layout {
  val widgetsConnection = SPGUICircuit.connect(x => (x.openWidgets.xs, x.globalState))
  val menuConnection = SPGUICircuit.connect(_.settings)
  val draggingConnection = SPGUICircuit.connect(_.draggingState)

  val component = ScalaComponent.builder[Unit]("Layout")
    .render(_ =>
      <.div(
        menuConnection(SPMenu(_)),
        widgetsConnection(Dashboard(_)),
        Dragging.mouseMoveCapture,
        draggingConnection(Dragging(_))
      )
    )
    .build

  def apply() = component()
}