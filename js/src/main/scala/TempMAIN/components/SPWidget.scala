package TempMAIN.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import spgui.circuit._
import sp.domain._
import java.util.UUID

/** A SPWidgetBase connected with a circuit for update and get widget-data,
  * update the frontend-state + open a new widget
  *
  * @param widgetBaseId A id for the base
  * @param frontEndState A GlobalState for the frontend
  */
case class SPWidgetBase(widgetBaseId: UUID, frontEndState: GlobalState) {

  def updateWidgetData(data: SPValue): Unit = {
    SPGUICircuit.dispatch(UpdateWidgetData(widgetBaseId, data))
  }

  def getWidgetData = {
    SPGUICircuit.zoom(_.widgetData.dataMap.get(widgetBaseId)).value.getOrElse(SPValue.empty)
  }

  def updateGlobalState(state: GlobalState): Unit = {
    SPGUICircuit.dispatch(UpdateGlobalState(state))
  }

  def openNewWidget(widgetType: String, initialData: SPValue = SPValue.empty) = {
    val widget = AddWidget(widgetType = widgetType)
    val data = UpdateWidgetData(widget.id, initialData)

    SPGUICircuit.dispatch(widget)
    SPGUICircuit.dispatch(data)
  }

  def closeSelf() = SPGUICircuit.dispatch(CloseWidget(widgetBaseId))
}

/** Defines a React-component for a Widget */
object SPWidget {
  case class Props(spwb: SPWidgetBase, renderWidget: SPWidgetBase => VdomElement)
  private val component = ScalaComponent.builder[Props]("SPWidgetComponent")
    .render_P(p => p.renderWidget(p.spwb))
    .build

  def apply(renderWidget: SPWidgetBase => VdomElement): SPWidgetBase => VdomElement =
    spwb => component(Props(spwb, renderWidget))
}