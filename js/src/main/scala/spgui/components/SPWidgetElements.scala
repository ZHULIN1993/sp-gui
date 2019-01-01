package spgui.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.vdom.all.aria


object SPWidgetElements {
  def button(text: String, onClick: Callback): VdomNode =
    <.span(
      text,
      ^.onClick --> onClick,
      ^.className := "btn",
      ^.className := SPWidgetElementsCSS.defaultMargin.htmlClass,
      ^.className := SPWidgetElementsCSS.clickable.htmlClass,
      ^.className := SPWidgetElementsCSS.button.htmlClass
    )

  def button(text:String, icon:VdomNode, onClick: Callback): VdomNode =
    <.span(
      <.span(text, ^.className:= SPWidgetElementsCSS.textIconClearance.htmlClass),
      icon,
      ^.onClick --> onClick,
      ^.className := "btn",
      ^.className := SPWidgetElementsCSS.defaultMargin.htmlClass,
      ^.className := SPWidgetElementsCSS.clickable.htmlClass,
      ^.className := SPWidgetElementsCSS.button.htmlClass
    )

  def button(icon: VdomNode, onClick: Callback): VdomNode =
    <.span(icon,
      ^.onClick --> onClick,
      ^.className := "btn",
      ^.className := SPWidgetElementsCSS.defaultMargin.htmlClass,
      ^.className := SPWidgetElementsCSS.clickable.htmlClass,
      ^.className := SPWidgetElementsCSS.button.htmlClass
    )

  def dropdown(text: String, contents: Seq[TagMod]): VdomElement =
    <.span(
      ^.className:= SPWidgetElementsCSS.dropdownRoot.htmlClass,
      <.span(
        ^.className:= SPWidgetElementsCSS.dropdownOuter.htmlClass,
        ^.className := SPWidgetElementsCSS.defaultMargin.htmlClass,
        ^.className:= "dropdown",
        <.span(
          <.span(text, ^.className:= SPWidgetElementsCSS.textIconClearance.htmlClass),
          Icon.caretDown,
          VdomAttr("data-toggle") := "dropdown",
          ^.id:="something",
          ^.className := "nav-link dropdown-toggle",
          aria.hasPopup := "true",
          aria.expanded := "false",
          ^.className := "btn",
          ^.className := SPWidgetElementsCSS.button.htmlClass,
          ^.className := SPWidgetElementsCSS.clickable.htmlClass
        ),
        <.ul(
          contents.collect{
            case e => <.div(
              ^.className := SPWidgetElementsCSS.dropdownElement.htmlClass,
              e
            )
          }.toTagMod,
          ^.className := SPWidgetElementsCSS.dropDownList.htmlClass,
          ^.className := "dropdown-menu",
          aria.labelledBy := "something"
        )
      )
    )

  def dropdown(button: TagMod, contents: Seq[TagMod]): VdomElement =
    <.span(
      ^.className:= SPWidgetElementsCSS.dropdownRoot.htmlClass,
      <.span(
        ^.className:= SPWidgetElementsCSS.dropdownOuter.htmlClass,
        ^.className := SPWidgetElementsCSS.defaultMargin.htmlClass,
        ^.className:= "dropdown",
        <.span(
          button,
          VdomAttr("data-toggle") := "dropdown",
          ^.id:="something",
          ^.className := "nav-link dropdown-toggle",
          aria.hasPopup := "true",
          aria.expanded := "false",
          ^.className := "btn",
          ^.className := SPWidgetElementsCSS.button.htmlClass,
          ^.className := SPWidgetElementsCSS.clickable.htmlClass
        ),
        <.ul(
          contents.collect{
            case e => <.div(
              ^.className := SPWidgetElementsCSS.dropdownElement.htmlClass,
              e
            )
          }.toTagMod,
          ^.className := SPWidgetElementsCSS.dropDownList.htmlClass,
          ^.className := "dropdown-menu",
          aria.labelledBy := "something"
        )
      )
    )

  def dropdownElement(text: String, icon: VdomNode, onClick: Callback): VdomNode =
    <.li(
      ^.className := SPWidgetElementsCSS.dropdownElement.htmlClass,
      <.span(icon, ^.className := SPWidgetElementsCSS.textIconClearance.htmlClass),
      text,
      ^.onClick --> onClick
    )

  def dropdownElement(text: String, onClick: Callback): VdomNode =
    <.li(
      ^.className := SPWidgetElementsCSS.dropdownElement.htmlClass,
      text,
      ^.onClick --> onClick
    )

  def buttonGroup(contents: Seq[TagMod]): VdomElement =
    <.div(
      ^.className:= "form-inline",
      contents.toTagMod
    )

  import spgui.dragging._


  def draggable(label:String, data: DragData, typ: String, onDrop: (DragDropData => Unit)): TagMod = {
    Seq(
      (^.onTouchStart ==> handleTouchDragStart(label, data, typ, onDrop)),
      (^.onTouchMoveCapture ==> {
        (e: ReactTouchEvent) => Callback ({
          val x = e.touches.item(0).pageX.toFloat
          val y = e.touches.item(0).pageY.toFloat
          Dragging.onDragMove(x, y)
        })
      }),
      (^.onTouchEnd ==> {
        (e: ReactTouchEvent) => Callback (Dragging.onDragStop())
      }),
      (^.onMouseDown ==> handleDragStart(label, data, typ, onDrop))
    ).toTagMod
  }

  /*
   This is used to generate mouse events when dragging on a touch screen, which will trigger
   the ^.onMouseOver on any element targeted by the touch event. Mobile browsers do not support
   mouse-hover related events (and why should they) so this is a way to deal with that.
   */
  def handleTouchDrag(e: ReactTouchEvent) = Callback {
    spgui.dragging.Dragging.onDragMove(
      e.touches.item(0).pageX.toFloat,
      e.touches.item(0).pageY.toFloat
    )
  }

  def handleTouchDragStart(label: String, data: DragData, typ: String, onDrop: (DragDropData => Unit))(e: ReactTouchEvent): Callback = {
    Callback(
      Dragging.onDragStart(
        label = label,
        typ = typ,
        data = data,
        x = e.touches.item(0).pageX.toFloat,
        y = e.touches.item(0).pageY.toFloat,
        onDrop = onDrop
      )
    )
  }

  def handleDragStart(label: String, data: DragData, typ: String, onDrop: (DragDropData => Unit))(e: ReactMouseEvent): Callback = {
    Callback(
      Dragging.onDragStart(
        label = label,
        typ = typ,
        data = data,
        x = e.pageX.toFloat,
        y = e.pageY.toFloat,
        onDrop = onDrop
      )
    )
  }
}
