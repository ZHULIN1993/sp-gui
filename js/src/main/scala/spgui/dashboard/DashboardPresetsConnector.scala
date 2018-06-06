package spgui.dashboard

import sp.domain.{SPHeader, SPMessage}
import spgui.circuit.{DashboardPresets, SPGUICircuit}
import spgui.communication.BackendCommunication
import spgui.menu.SPMenu

/**
  * Sets up and connects the preset menu, and emits DashboardPreset events to the backend.
  */
object DashboardPresetsConnector {
  /**
    * Adds a layout preset menu to the SP header menu
    */
  def addPresetMenu() = {
    val presetConfig = DashboardPresetsMenu.Config
      .onMount(() => emit(DashboardPresets.MenuMounted))
      .onLoad(p => emit(DashboardPresets.LoadPreset(p)))
      .onSave(p => emit(DashboardPresets.SavePreset(p)))
      .onDelete(p => emit(DashboardPresets.DeletePreset(p)))

    val presetsMenu = connectToCircuit(proxy => DashboardPresetsMenu(proxy, presetConfig)).vdomElement
    SPMenu.addNavElem(presetsMenu)
  }

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
}
