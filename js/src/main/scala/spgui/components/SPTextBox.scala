package spgui.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.vdom.all.aria

object SPTextBox {
  case class Props(contentText: String, placeholderText: String, onChange: String => Callback )
  case class State(text: String = "")

  class Backend($: BackendScope[Props, State]) {
    def render(p:Props, s: State) =
      <.input(
        ^.className := SPTextBoxCSS.inputForm.htmlClass,
        ^.placeholder := p.placeholderText,
        ^.`type` := "text",
        ^.value := p.contentText,
        ^.onChange ==> onFilterTextChange(p)
      )

    def onFilterTextChange(p:Props)(e: ReactEventFromInput): Callback =
      e.extract(_.target.value)(v => (p.onChange(v)))
  }

  private val component = ScalaComponent.builder[Props]("SPTextBox")
    .initialState(State())
    .renderBackend[Backend]
    .build

  // atm broken; gives funny behaviour where you can just enter one letter that you cannot see
  //def apply(placeholderText: String, onChange: String => Callback): VdomElement =
  //  component(Props("", placeholderText, onChange))

  def apply(contentText: String, placeholderText: String, onChange: String => Callback): VdomElement =
    component(Props(contentText, placeholderText, onChange))
}