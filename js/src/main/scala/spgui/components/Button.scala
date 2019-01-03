package spgui.components

import japgolly.scalajs.react.vdom.html_<^._

object Button {
  def button(text: String) =
    <.button(
      ^.className := "btn btn-sm",
      ^.className := ButtonCSS.button.htmlClass,
      text
    )
}
