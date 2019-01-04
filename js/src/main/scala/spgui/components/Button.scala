package spgui.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import spgui.components.Icon.Icon

object Button {
  def SPButton(text: String, onClick: Callback, icon: Icon = null) =
    <.button(
      ^.onClick --> onClick,
      ^.className := ButtonCSS.spButtonStyle.htmlClass,
      if(icon == null) text
      else
        <.span(<.span(^.className := ButtonCSS.textBeforeIcon.htmlClass, text), icon)
    )

  def SPDropdown(text: String, onClick: Callback, icon: VdomNode, subItems: Seq[VdomNode]) =
    <.button(
      ^.onClick --> onClick,
      ^.className := ButtonCSS.spDropdownStyle.htmlClass,
      <.span(^.className := ButtonCSS.textBeforeIcon.htmlClass, text),
      icon,
      <.ul(
        ^.className := "", // add css for a list
        subItems.map(node =>
          <.li(
            ^.className := "", // add css foreach list item
            node
          )
        ).toTagMod
      ).when(subItems.nonEmpty)
    )

  def SPDropdownItem(text: String, onClick: Callback) =
    <.button(
      ^.onClick --> onClick,
      ^.className := ButtonCSS.spDropdownButton.htmlClass,
      text
    )

}
