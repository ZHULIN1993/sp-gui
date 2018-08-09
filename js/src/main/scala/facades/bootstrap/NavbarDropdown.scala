package facades.bootstrap

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object NavbarDropdown {

  case class Props(dropdownItems: Seq[VdomElement] = Seq())

  case class State(isExpanded: Boolean = false)

  class Backend($: BackendScope[Props, State]) {
    def render(p: Props, s: State)=
      <.li(
        ^.className := "nav-item dropdown",
        ^.onClick --> $.modState(s => s.copy(isExpanded = !s.isExpanded)),
        if(s.isExpanded)
          <.div(
            ^.className := "dropdow-menu",
            p.dropdownItems.map(i => <.a(^.className := "dropdown-item", i)).toTagMod
          )
        else
          <.div()
      )

  }

  val component = ScalaComponent.builder[Props]("NavbarDropdown")
    .initialState(State())
    .renderBackend[Backend]
    .build

  def apply() = component(Props())
  def apply(p: Props) = component(p)
  def apply(p: Props, s: State) = ???
}