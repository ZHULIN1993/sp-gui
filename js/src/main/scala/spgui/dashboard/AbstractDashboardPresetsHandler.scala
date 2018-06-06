package spgui.dashboard

import sp.domain
import sp.domain._
import spgui.circuit.{DashboardPreset, SPGUICircuit, SetDashboardPresets}
import spgui.communication.BackendCommunication
import spgui.menu.SPMenu

import scala.concurrent.Future

/**
  * Created by alfredbjork on 2018-04-05.
  */
abstract class AbstractDashboardPresetsHandler {

  val presetConfig = DashboardPresetsMenu.Config
    .onMount(() => AbstractDashboardPresetsHandler.this.requestPresets())

  // Add the menu component to the menu and start subscribing preset messages
  SPMenu.addNavElem(connectedMenuComponent(p => DashboardPresetsMenu(p, presetConfig)).vdomElement)
  private val obs = BackendCommunication.getMessageObserver(handleMsg, dashboardPresetsTopic)

  //requestPresets() // Get initial state for presets

  private def connectedMenuComponent = {
    SPGUICircuit.connect(m => DashboardPresetsMenu.ProxyContents(m.presets.presets, m.openWidgets, m.widgetData))
  }

  protected final def updateGUIState(presets: Set[DashboardPreset]): Unit = {
    SPGUICircuit.dispatch(SetDashboardPresets(presets))
    println("GUI state updated to: " + presets)
  }

  protected final def fromJson(json: String): Option[DashboardPreset] = {
    import spgui.circuit.JsonifyUIState._
    domain.fromJsonAs[DashboardPreset](json).toOption
  }

  protected final def toJson(preset: DashboardPreset): String = {
    import spgui.circuit.JsonifyUIState._
    domain.toJson(preset)
  }

  protected final def sendMsg[S](from: String, to: String, payload: S)(implicit fjs: domain.JSWrites[S]): Future[String] =
    BackendCommunication.publish(SPMessage.make[SPHeader, S](SPHeader(from = from, to = to), payload), dashboardPresetsTopic)

  /**
    * Returns the topic to be used for communication between frontend and backend regarding dashboard presets
    * @return String
    */
  protected def dashboardPresetsTopic: String

  /**
    * Should process messages that comes on the dashboardPresetsTopic and update the
    * GUIModel according to incoming messages.
    * @param msg SPMessage
    */
  def handleMsg(msg: SPMessage): Unit

  /**
    * Should ask the backend for presets so that handleMsg can do its job.
    * This is called when the GUI is instantiated but can also be called at other times.
    */
  def requestPresets(): Unit

  /**
    * Should take the given preset and send it to the backend for persistent storage
    */
  def storePresetToPersistentStorage(name: String, preset: DashboardPreset): Unit

  /**
    * Should tell persistent storage to remove the preset corresponding to the given name
    * @param name
    */
  def removePresetFromPersistentStorage(name: String): Unit

}