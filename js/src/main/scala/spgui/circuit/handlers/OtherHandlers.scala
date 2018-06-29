package spgui.circuit.handlers

import diode.{ActionHandler, ModelRW}
import spgui.circuit._

class ModalHandler[M](modelRW: ModelRW[M, ModalState]) extends ActionHandler(modelRW) {
  override def handle = {
    case OpenModal(title, component, onComplete) => {
      updated(value.copy(
        modalVisible = true,
        title = title,
        component = Some(component),
        onComplete = Some(onComplete)
      ))
    }

    case CloseModal => {
      updated(value.copy(modalVisible = false, component = None, onComplete = None))
    }
  }
}

class GlobalStateHandler[M](modelRW: ModelRW[M, GlobalState]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateGlobalState(state) =>
      updated(state)
    case UpdateGlobalAttributes(key, v) =>
      val attr = value.attributes + (key->v)
      updated(value.copy(attributes = attr))
  }
}

class WidgetDataHandler[M](modelRW: ModelRW[M, WidgetData]) extends ActionHandler(modelRW) {
  override def handle = {
    case UpdateWidgetData(id, d) =>
      val updW = value.dataMap + (id -> d)
      updated(WidgetData(updW))
  }
}

class SettingsHandler[M](modelRW: ModelRW[M, Settings]) extends ActionHandler(modelRW) {
  override def handle = {
    case SetTheme(newTheme) => {
      updated(value.copy(
        theme = newTheme
      ))
    }
    case ToggleHeaders => {
      updated(value.copy(
        showHeaders = !value.showHeaders
      ))
    }
  }
}

class DraggingHandler[M](modelRW: ModelRW[M, DraggingState]) extends ActionHandler(modelRW) {
  override def handle = {
    case SetDraggableRenderStyle(renderStyle) => updated(value.copy(renderStyle = renderStyle))
    case SetDraggableData(data) => updated(value.copy(data = data))
    case SetCurrentlyDragging(dragging) => updated(value.copy(dragging = dragging))
    case SetDraggingTarget(id) => updated(value.copy(target = Some(id)))
    case UnsetDraggingTarget => updated(value.copy(target = None))
    case DropEvent(struct, target) =>
      updated(value.copy(latestDropEvent = Some(DropEventData(struct, target))))
  }
}