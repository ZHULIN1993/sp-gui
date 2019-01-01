package spgui.components

import java.util.UUID
import japgolly.scalajs.react.{BackendScope, Callback, ScalaComponent}
import japgolly.scalajs.react.vdom.html_<^._
import spgui.components.SPWidgetElementsCSS
import spgui.dragging.{DragDropData, Dragging, DropData}

object DragoverZoneWithChild {
  case class Props(id: UUID, cb: (DragDropData) => Unit, dropData: DropData, subComponent: VdomNode)
  case class State(hovering: Boolean, id: UUID)

  class Backend($: BackendScope[Props, State]) {
    def setHovering(hovering: Boolean) =
      $.modState(s => s.copy(hovering = hovering)).runNow()

    def render(p:Props, s:State) = {
      <.span(
        ^.className := SPWidgetElementsCSS.dropZoneUnzisedOuter.htmlClass,
        <.span(
          ^.id := p.id.toString,
          ^.className := SPWidgetElementsCSS.dropZone.htmlClass,
          ^.className := SPWidgetElementsCSS.fillParent.htmlClass,
          {if(s.hovering)
            ^.className := SPWidgetElementsCSS.blue.htmlClass
          else ""},
          ^.onMouseOver --> Callback({
            Dragging.setDraggingTarget(p.id)
          })
        ),
        p.subComponent
      )
    }
  }

  private val component = ScalaComponent.builder[Props]("SPDragZoneUnzised")
    .initialState(State(hovering = false, id = UUID.randomUUID()))
    .renderBackend[Backend]
    .componentDidUpdate(c => Callback{
      Dragging.unsubscribeToDropEvents(c.prevProps.id)
      Dragging.subscribeToDropEvents(c.currentProps.id, c.currentProps.cb, c.currentProps.dropData)
      Dragging.dropzoneResubscribe(c.currentProps.id, c.prevProps.id)
    })
    .componentDidMount(c => Callback{
      Dragging.dropzoneSubscribe(c.props.id, c.backend.setHovering)
      Dragging.subscribeToDropEvents(c.props.id, c.props.cb, c.props.dropData)
    })
    .componentWillUnmount(c => Callback({
      Dragging.dropzoneUnsubscribe(c.props.id)
      Dragging.unsubscribeToDropEvents(c.props.id)
    }))
    .build

  def apply(cb: (DragDropData) => Unit, dropData: DropData, subComponent: VdomNode) =
    component(Props(UUID.randomUUID(), cb, dropData, subComponent))
}