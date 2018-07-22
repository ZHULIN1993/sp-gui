package spgui.circuit.handlers

import diode.{ActionHandler, ModelRW}
import spgui.circuit._

class PresetsHandler[M](modelRW: ModelRW[M, PresetsHandlerScope]) extends ActionHandler(modelRW) {

  override def handle = {
    case AddDashboardPreset(name) => { // Takes current state of dashboard and saves in list of presets
      val newPreset = DashboardPreset(
        value.openWidgets,
        WidgetData(value.widgetData.dataMap.filterKeys(value.openWidgets.widgetMap.keySet.contains(_)))
      )

      // Tell persistent storage to add preset
      //SPGUICircuit.dashboardPresetHandler.flatMap(h => {h.storePresetToPersistentStorage(name, newPreset);None})
      SPGUICircuit.dashboardPresetHandler.foreach(h => h.storePresetToPersistentStorage(name, newPreset))

      updated(value.copy(presets = DashboardPresets(value.presets.presetsMap + (name -> newPreset))))
    }

    case RemoveDashboardPreset(name) => { // Removes the preset corresponding to the given name

      // Tell persistent storage to remove preset
      //SPGUICircuit.dashboardPresetHandler.flatMap(h => {h.removePresetFromPersistentStorage(name);None})
      SPGUICircuit.dashboardPresetHandler.foreach(h => h.removePresetFromPersistentStorage(name))

      updated(value.copy(presets = DashboardPresets(value.presets.presetsMap - name)))
    }

    case SetDashboardPresets(presetsMap: Map[String, DashboardPreset]) => {
      updated(value.copy(presets = DashboardPresets(presetsMap)))
    }

    case RecallDashboardPreset(preset) => {
      updated(value.copy(openWidgets = OpenWidgets())) //First remove all widgets to let them unmount
      updated(value.copy(openWidgets = preset.widgets, widgetData = preset.widgetData))
    }
  }
}

