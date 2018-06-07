package spgui.dashboard

import japgolly.scalajs.react.vdom.VdomElement
import sp.domain.{SPHeader, SPMessage}
import spgui.circuit.{DashboardPresets, SPGUICircuit}
import spgui.communication.BackendCommunication

/**
  * Sets up and connects the preset menu, and emits DashboardPreset events to the backend.
  */
abstract class DashboardPresetsComponent {
  def AkkaTopic: String
  BackendCommunication.getMessageObserver(processMessage, topic = AkkaTopic)

  private def processMessage(message: SPMessage): Unit = {
    import DashboardPresets.Formats._
    message.getBodyAs[DashboardPresets].foreach(presets => onReceivePresetSnapshot(presets))
  }

  def onReceivePresetSnapshot(presets: DashboardPresets): Unit

  private def connectToCircuit = {
    SPGUICircuit.connect(model => DashboardPresetsMenu.ProxyContents(
      presets = model.presets.presets,
      widgets = model.openWidgets,
      widgetData = model.widgetData))
  }

  /**
    * Emit events to the backend.
    */
  private def emit(event: DashboardPresets.Event) = {
    val header = SPHeader(from = "Main", to = "GUIStatePersistence")
    BackendCommunication.publish(SPMessage.make(header, event), "dashboard-preset-event")
  }

  def render(): VdomElement = {
    val presetConfig = DashboardPresetsMenu.Config
      .onMount(() => emit(DashboardPresets.MenuMounted))
      .onLoad(p => emit(DashboardPresets.LoadPreset(p)))
      .onSave(p => emit(DashboardPresets.SavePreset(p)))
      .onDelete(p => emit(DashboardPresets.DeletePreset(p)))

    connectToCircuit(proxy => DashboardPresetsMenu(proxy, presetConfig)).vdomElement
    // SPMenu.addNavElem(presetsMenu)
  }
}