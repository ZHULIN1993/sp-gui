package facades.bootstrap

/**
  * Created by alexa on 06/08/2018.
  */

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object NavbarDropdownItem {

  case class Props(text: String = "", onClick: Callback = Callback(println("Empty Callback in dropdownItem")))

  case class State()

  class Backend($: BackendScope[Props, State]) {
    def render(p: Props, s: State) = {
      <.span(^.onClick --> p.onClick, p.text)
    }
  }

  val component = ScalaComponent.builder[Props]("NavbarDropdownItem")
    .initialState(State())
    .renderBackend[Backend]
    .build

  def apply() = component(Props())
  def apply(p: Props) = component(p)
  def apply(p: Props, s: State) = ???
}