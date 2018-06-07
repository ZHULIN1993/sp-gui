package spgui.circuit

import diode._
import java.util.UUID

import japgolly.scalajs.react.Callback
import japgolly.scalajs.react.vdom.VdomElement
import play.api.libs.json._
import sp.domain.{Action => _, _}
import spgui.modal.ModalResult
import spgui.theming.Theming.Theme

import scala.collection.immutable.HashSet

// state
case class SPGUIModel(
                       presets: DashboardPresets = DashboardPresets(),
                       openWidgets: OpenWidgets = OpenWidgets(),
                       globalState: GlobalState = GlobalState(),
                       widgetData: WidgetData = WidgetData(Map()),
                       settings: Settings = Settings(),
                       draggingState: DraggingState = DraggingState(),
                       modalState: ModalState = ModalState()
)
case class OpenWidgets(xs: Map[UUID, OpenWidget] = Map())
case class OpenWidget(id: UUID, layout: WidgetLayout, widgetType: String)
case class WidgetLayout(x: Int, y: Int, w: Int, h: Int, collapsedHeight: Int = 1)

case class DashboardPresets(presets: Set[DashboardPreset] = HashSet()) {
  def find(predicate: DashboardPreset => Boolean) = presets.find(predicate)
  def +(preset: DashboardPreset) = copy(presets = presets + preset)
  def ++(presets: DashboardPresets) = copy(presets = this.presets ++ presets.presets)
  def -(preset: DashboardPreset) = copy(presets = presets - preset)
  def removeFirst(predicate: DashboardPreset => Boolean) = {
    presets.find(predicate).map(preset => copy(presets = presets - preset))
  }
  def filter(predicate: DashboardPreset => Boolean) = copy(presets = presets.filter(predicate))
  def findByName(name: String) = find(_.name == name)
}

object DashboardPresets {
  import Event._
  import sp.domain.Logic._

  sealed trait Event
  case object MenuMounted extends Event
  case class LoadPreset(preset: DashboardPreset) extends Event
  case class SavePreset(preset: DashboardPreset) extends Event
  case class DeletePreset(preset: DashboardPreset) extends Event

  object Formats {
    def mapWrites[K, V](f: K => String)(implicit writes: JSWrites[V]): JSWrites[Map[K, V]] = xs => {
      JsObject(xs.map { case (k, v) => f(k) -> SPValue(v) })
    }

    def mapReads[K, V](keyParser: String => Option[K])(implicit reads: JSReads[V]): JSReads[Map[K, V]] = _.validate[Map[String, SPValue]].map(
      _.collect { case (k, v) =>
        for (key <- keyParser(k); value <- v.to[V].toOption) yield key -> value
      }.flatten.toMap
    )

    def mapFormat[K, V](deserializeKey: String => Option[K], serializeKey: K => String)(implicit f: JSFormat[V]) = {
      Format(mapReads[K, V](deserializeKey), mapWrites[K, V](serializeKey))
    }

    implicit lazy val fOpenWidgetMap = mapFormat[ID, OpenWidget](k => ID.makeID(k), k => k.toString)
    implicit lazy val fDashboardPresetMap = mapFormat[String, DashboardPreset](k => Some(k), k => k)

    implicit val fTheme: JSFormat[Theme] = Json.format[Theme]
    implicit val fSettings: JSFormat[Settings] = Json.format[Settings]
    implicit val fWidgetData: JSFormat[WidgetData] = Json.format[WidgetData]
    implicit val fWidgetLayout: JSFormat[WidgetLayout] = Json.format[WidgetLayout]
    implicit val fOpenWidget: JSFormat[OpenWidget] = Json.format[OpenWidget]
    implicit val fDropEvent: JSFormat[DropEventData] = Json.format[DropEventData]
    implicit val fDraggingState: JSFormat[DraggingState] = Json.format[DraggingState]

    implicit val fOpenWidgets: JSFormat[OpenWidgets] = Json.format[OpenWidgets]
    implicit val fDashboardPreset: JSFormat[DashboardPreset] = Json.format[DashboardPreset]
    implicit val fDashboardPresets: JSFormat[DashboardPresets] = Json.format[DashboardPresets]
    implicit val fGlobalState: JSFormat[GlobalState] = Json.format[GlobalState]
    implicit val fSPGUIModel: JSFormat[SPGUIModel] = Json.format[SPGUIModel]

    implicit lazy val modalStateWrites: JSWrites[ModalState] = _ => JsObject(Seq.empty)
    // we actually don't care about parsing modalState so we just instantiate a new one
    implicit lazy val modalStateReads: JSReads[ModalState] = _ => JsSuccess(ModalState())

    implicit lazy val fMenuMounted: JSFormat[MenuMounted.type] = deriveCaseObject[MenuMounted.type]
    implicit lazy val fLoadPreset: JSFormat[LoadPreset] = Json.format[LoadPreset]
    implicit lazy val fSavePreset: JSFormat[SavePreset] = Json.format[SavePreset]
    implicit lazy val fDeletePreset: JSFormat[DeletePreset] = Json.format[DeletePreset]

    def fEvent: JSFormat[Event] = Json.format[Event]
  }

  object Event {
    implicit lazy val fEvent: JSFormat[Event] = Formats.fEvent
  }
}

// case class DashboardPreset(name: String, widgets: OpenWidgets = OpenWidgets(), widgetData: WidgetData = WidgetData(Map()))
case class DashboardPreset(name: String, widgets: OpenWidgets, widgetData: WidgetData) {
  override def hashCode = name.hashCode
}

object DashboardPreset {
  def empty(name: String) = DashboardPreset(name, OpenWidgets(), WidgetData(Map()))
}

case class GlobalState(
  currentModel: Option[UUID] = None,
  selectedItems: List[UUID] = List(),
  userID: Option[UUID] = None,
  clientID: UUID = UUID.randomUUID(),
  attributes: Map[String, SPValue] = Map()
)
case class WidgetData(xs: Map[UUID, SPValue])

case class Settings(
  theme: Theme = Theme(),
  showHeaders: Boolean = true 
)

case class DropEventData(droppedId: UUID, targetId: UUID)
case class DraggingState(
  target: Option[UUID] = None,
  dragging: Boolean = false,
  renderStyle: String = "",
  data: String = "",
  latestDropEvent: Option[DropEventData] = None 
)

case class ModalState(
                       modalVisible: Boolean = false,
                       title: String = "",
                       component: Option[(ModalResult => Callback) => VdomElement] = None,
                       onComplete: Option[ModalResult => Callback] = None
                     )

// actions
case class AddWidget(widgetType: String, width: Int = 2, height: Int = 2, id: UUID = UUID.randomUUID()) extends Action
case class CloseWidget(id: UUID) extends Action
case class CollapseWidgetToggle(id: UUID) extends Action
case object CloseAllWidgets extends Action
case class RecallDashboardPreset(preset: DashboardPreset) extends Action
case class UpdateLayout(id: UUID, newLayout: WidgetLayout) extends Action
case class SetLayout(layout: Map[UUID, WidgetLayout]) extends Action

case class AddDashboardPreset(preset: DashboardPreset) extends Action
case class RemoveDashboardPreset(preset: DashboardPreset) extends Action
case class SetDashboardPresets(presets: DashboardPresets) extends Action

case class UpdateWidgetData(id: UUID, data: SPValue) extends Action

case class UpdateGlobalAttributes(key: String, value: SPValue) extends Action
case class UpdateGlobalState(state: GlobalState) extends Action

case class SetTheme(theme: Theme) extends Action
case object ToggleHeaders extends Action

case class SetDraggableRenderStyle(style:String) extends Action
case class SetDraggableData(data: String) extends Action
case class SetCurrentlyDragging(enabled: Boolean) extends Action 
case class SetDraggingTarget(id: UUID) extends Action
case object UnsetDraggingTarget extends Action
case class DropEvent(dropped: UUID, target: UUID) extends Action

case class OpenModal(title: String = "", component: (ModalResult => Callback) => VdomElement, onComplete: ModalResult => Callback) extends Action
case object CloseModal extends Action

// used when failing to retrieve a state from browser storage
object InitialState {
  def apply() = SPGUIModel()
}
