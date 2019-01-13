package spgui.widgets.services

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import spgui.communication._
import sp.domain._
import sp.service.{APIServiceHandler => api}
import spgui.components.{SPNavbarElements, SPTextBox, Table}

object ServiceListWidget {
  case class State(services: List[APISP.StatusResponse], filterText: String = null)

  private class Backend($: BackendScope[Unit, State]) {

    val wsObs = BackendCommunication.getWebSocketStatusObserver(connected => {
      if (connected) sendToHandler(api.GetServices)
    }, api.topicResponse)

    def handelMess(mess: SPMessage): Unit = {
      for {
        h <- mess.getHeaderAs[SPHeader]
        b <- mess.getBodyAs[api.Response]
      } yield {
        val res = b match {
          case api.Services(xs) => $.setState(State(xs))
          case api.ServiceAdded(s) => $.modState(state => State(s :: state.services))
          case api.ServiceRemoved(s) => $.modState(state => State(state.services.filter(x => x.instanceID != s.instanceID)))
        }
        res.runNow()
      }
    }

    val answerHandler = BackendCommunication.getMessageObserver(handelMess, api.topicResponse)

    def render(state: State) = {
      val services = state.services.map{resp => (resp.service, resp.instanceName, "" + resp.instanceID.getOrElse(""), resp.tags.mkString(" "), resp.version + "")}
      val toDisplay = if(state.filterText != null || state.filterText == "")
        services.filter(s => s._1 == state.filterText) else services
      <.div(
        <.div(
          ^.marginLeft.:=(5.px),
          ^.marginBottom.:=(5.px),
          SPNavbarElements.TextBox(
            state.filterText,
            "Filter Services...",
            (t: String) => { $.setState(State(state.services, filterText = t)) }
          )
        ),
        <.div(
          renderServices(toDisplay)
          // TODO: Add search and sorting. Add possibility to send messaged based on api
        )
      )
    }

    val tableHeaders = Vector(
      Table.ColumnData("Service"),
      Table.ColumnData("Name"),
      Table.ColumnData("Id"),
      Table.ColumnData("Tags"),
      Table.ColumnData("Version")
    )

    def renderServices(services: Seq[(String, String, String, String, String)]) = {
      Table(tableHeaders, services, Callback.empty, true)
    }

    def onUnmount() = {
      answerHandler.kill()
      wsObs.kill()
      Callback.empty
    }

    def onMount() = {
      sendToHandler(api.GetServices)
    }

    def sendToHandler(mess: api.Request): Callback = {
      val h = SPHeader(from = "ServiceListWidget", to = api.service, reply = SPValue("ServiceListWidget"))
      val json = SPMessage.make[SPHeader, api.Request](h, api.GetServices)
      BackendCommunication.publish(json, api.topicRequest)
      Callback.empty
    }
  }

  private val component = ScalaComponent.builder[Unit]("AbilityHandlerWidget")
    .initialState(State(services = List()))
    .renderBackend[Backend]
    .componentDidMount(_.backend.onMount())
    .componentWillUnmount(_.backend.onUnmount())
    .build

  def apply() = spgui.SPWidget(spwb => component())
}
