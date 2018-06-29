package spgui.circuit

import diode._
import diode.react.ReactConnector
import org.scalajs.dom.ext.LocalStorage
import spgui.theming.Theming.Theme
import spgui.dashboard.{AbstractDashboardPresetsHandler, Dashboard}
import spgui.circuit.handlers._

/** SP-GUI CIRCUIT with information about the current frontend states Tag: DocHelp*/
object SPGUICircuit extends Circuit[SPGUIModel] with ReactConnector[SPGUIModel] {
  def initialModel = BrowserStorage.load.getOrElse(InitialState())
  val actionHandler = composeHandlers(
    new PresetsHandler(
      zoomRW(m => PresetsHandlerScope(m.presets, m.openWidgets, m.widgetData))
      ((m, phs) => m.copy(presets = phs.presets, openWidgets = phs.openWidgets, widgetData = phs.widgetData))
    ),
    new OpenWidgetsHandler(
      zoomRW(_.openWidgets)((m, openWidget) => m.copy(openWidgets = openWidget))
    ),
    new GlobalStateHandler(
      zoomRW(_.globalState)((m, state) => m.copy(globalState = state))
    ),
    new SettingsHandler(
      zoomRW(_.settings)((m, settings) => m.copy(settings = settings))
    ),
    new WidgetDataHandler(
      zoomRW(_.widgetData)((m, data) => m.copy(widgetData = data))
    ),
    new DraggingHandler(
      zoomRW(_.draggingState)((m, draggingState) => m.copy(draggingState = draggingState))
    ),
    new ModalHandler(
      zoomRW(_.modalState)((m, modalState) => m.copy(modalState = modalState))
    )
  )
  // store state upon any model change
  subscribe(zoomRW(myM => myM)((_, model) => model))(m => BrowserStorage.store(m.value))
  //subscribe(zoomRW(myM => myM)((m, model) => model))(m => BrowserStorage.store(m.value)) // If line above do not work

  /** Tag: DocHelp. Motivation of var? */
  var dashboardPresetHandler: Option[AbstractDashboardPresetsHandler] = None
}

case class PresetsHandlerScope(presets: DashboardPresets, openWidgets: OpenWidgets, widgetData: WidgetData)

object BrowserStorage {
  import sp.domain._
  import sp.domain.Logic._
  import JsonifyUIState._
  val namespace = "SPGUIState"

  def store(spGUIState: SPGUIModel) = LocalStorage(namespace) = SPValue(spGUIState).toJson
  def load: Option[SPGUIModel] =
    LocalStorage(namespace).flatMap(x => SPAttributes.fromJsonGetAs[SPGUIModel](x))
}

object JsonifyUIState {
  import sp.domain._
  import Logic._
  import play.api.libs.json._

  implicit val fTheme: JSFormat[Theme] = Json.format[Theme]
  implicit val fSettings: JSFormat[Settings] = Json.format[Settings]
  implicit val fWidgetData: JSFormat[WidgetData] = Json.format[WidgetData]
  implicit val fWidgetLayout: JSFormat[WidgetLayout] = Json.format[WidgetLayout]
  implicit val fOpenWidget: JSFormat[OpenWidget] = Json.format[OpenWidget]
  implicit val fDropEvent: JSFormat[DropEventData] = Json.format[DropEventData]
  implicit val fDraggingState: JSFormat[DraggingState] = Json.format[DraggingState]

  implicit lazy val modalStateWrites: JSWrites[ModalState] =
    new OWrites[ModalState] {
      override def writes(o: ModalState): SPAttributes =
        JsObject(Map("nothing" -> SPValue.empty)) // we don't care
    }

  implicit lazy val modalStateReads: JSReads[ModalState] =
    new JSReads[ModalState] {
      override def reads(json: SPValue): JsResult[ModalState] = {
        JsSuccess(ModalState()) // we actually don't care about parsing modalState so we just instantiate a new one
      }
    }

  implicit lazy val dashboardPresetsMapReads: JSReads[Map[String, DashboardPreset]] =
    new JSReads[Map[String, DashboardPreset]] {
      override def reads(json: JsValue): JsResult[Map[String, DashboardPreset]] = {
        json.validate[Map[String, SPValue]].map{xs =>
          def isCorrect(k: String, v: SPValue) = v.to[DashboardPreset].isSuccess
          xs.collect{case (k, v) if isCorrect(k, v) => k -> v.to[DashboardPreset].get}
        }
      }
    }
  implicit lazy val dashboardPresetsMapWrites: JSWrites[Map[String, DashboardPreset]] =
    new OWrites[Map[String, DashboardPreset]] {
      override def writes(xs: Map[String, DashboardPreset]): JsObject = {
        val toFixedMap = xs.map{case (k, v) => k -> SPValue(v)}
        JsObject(toFixedMap)
      }
    }

  implicit lazy val openWidgetMapReads: JSReads[Map[ID, OpenWidget]] = new JSReads[Map[ID, OpenWidget]] {
    override def reads(json: JsValue): JsResult[Map[ID, OpenWidget]] = {
      json.validate[Map[String, SPValue]].map{xs =>
        def isCorrect(k: String, v: SPValue) = ID.isID(k) && v.to[OpenWidget].isSuccess
        xs.collect{case (k, v) if isCorrect(k, v) => ID.makeID(k).get -> v.to[OpenWidget].get}}
    }
  }
  implicit lazy val openWidgetMapWrites: JSWrites[Map[ID, OpenWidget]] = new OWrites[Map[ID, OpenWidget]] {
    override def writes(xs: Map[ID, OpenWidget]): JsObject = {
      val toFixedMap = xs.map{case (k, v) => k.toString -> SPValue(v)}
      JsObject(toFixedMap)
    }
  }
  implicit val fOpenWidgets: JSFormat[OpenWidgets] = Json.format[OpenWidgets]
  implicit val fDashboardPreset: JSFormat[DashboardPreset] = Json.format[DashboardPreset]
  implicit val fDashboardPresets: JSFormat[DashboardPresets] = Json.format[DashboardPresets]
  implicit val fGlobalState: JSFormat[GlobalState] = Json.format[GlobalState]
  implicit val fSPGUIModel: JSFormat[SPGUIModel] = Json.format[SPGUIModel]
}