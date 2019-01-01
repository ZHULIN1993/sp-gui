import java.util.UUID

import japgolly.scalajs.react.{BackendScope, Callback, ScalaComponent}
import japgolly.scalajs.react.vdom.html_<^._
import spgui.components.SPWidgetElementsCSS
import spgui.dragging.{DragDropData, Dragging, DropData}

import scala.scalajs.js

object DragoverZoneRect {
  trait Rectangle extends js.Object {
    var left: Float = js.native
    var top: Float = js.native
    var width: Float = js.native
    var height: Float = js.native
  }

  case class Props(id: UUID, cb: (DragDropData) => Unit, dropData: DropData, x: Float, y: Float, w: Float, h: Float)
  case class State(hovering: Boolean, id: UUID)

  class Backend($: BackendScope[Props, State]) {

    def setHovering(hovering: Boolean) =
      $.modState(s => s.copy(hovering = hovering)).runNow()

    def render(p:Props, s:State) = {
      <.span(
        <.span(
          ^.id := p.id.toString,
          ^.style := {
            var rect =  (js.Object()).asInstanceOf[Rectangle]
            rect.left = p.x
            rect.top = p.y
            rect.height = p.h
            rect.width = p.w
            rect
          },
          ^.className := SPWidgetElementsCSS.dropZone.htmlClass,
          {if(s.hovering)
            ^.className := SPWidgetElementsCSS.blue.htmlClass
          else ""},
          ^.onMouseOver --> Callback({
            Dragging.setDraggingTarget(p.id)
          })
        )
      )
    }
  }

  private val component = ScalaComponent.builder[Props]("SPDragZone")
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

  def apply(cb: (DragDropData) => Unit, dropData: DropData, x: Float, y: Float, w: Float, h: Float) =
    component(Props(UUID.randomUUID(), cb, dropData, x, y, w, h))
}

