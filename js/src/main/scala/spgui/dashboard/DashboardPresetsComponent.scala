package spgui.dashboard

import japgolly.scalajs.react.vdom.VdomElement
import play.api.libs.json.Json
import sp.domain._
import spgui.circuit.{DashboardPreset, DashboardPresets, SPGUICircuit, SetDashboardPresets}
import spgui.communication.BackendCommunication

/**
  * Sets up and connects the preset menu, and emits DashboardPreset events to the backend.
  */
abstract class DashboardPresetsComponent {
  import DashboardPresets.Formats._
  case class Wrapper(list: List[DashboardPreset])

  implicit val fWrapper: JSFormat[Wrapper] = Json.format[Wrapper]

  def AkkaTopic = "gui-snapshot"
  BackendCommunication.getMessageObserver(processMessage, topic = AkkaTopic)

  private def processMessage(message: SPMessage): Unit = {
    println(s"DashboardPresetsComponent: processMessage($message)")
    import DashboardPresets.Formats._
    message.getBodyAs[DashboardPresets].foreach(presets => onReceivePresetSnapshot(presets))
  }

  def onReceivePresetSnapshot(presets: DashboardPresets): Unit = {
    SPGUICircuit.dispatch(SetDashboardPresets(presets))
  }

  private def connectToCircuit = {
    SPGUICircuit.connect(model => DashboardPresetsMenu.ProxyContents(
      presets = model.presets.presets,
      widgets = model.openWidgets,
      widgetData = model.widgetData))
  }

  def config: DashboardPresetsMenu.Config

  /**
    * Emit events to the backend.
    */
  def emit[A](value: A)(implicit writes: JSWrites[A]) = {
    val header = SPHeader(from = "Main", to = "GUIStatePersistence")
    BackendCommunication.publish(SPMessage.make(header, value), "persist-gui-state")
  }

  def render(): VdomElement = {
    connectToCircuit(proxy => DashboardPresetsMenu(proxy, config)).vdomElement
  }
}