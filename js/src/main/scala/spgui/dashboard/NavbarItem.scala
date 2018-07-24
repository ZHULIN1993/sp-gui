package spgui.dashboard

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object NavbarItem {
  case class Props(text: String)

  private class Backend($: BackendScope[Props, Unit]) {
    def render(p: Props) = {
      <.div(
        <.h1(p.text)
      )
    }
  }
  private val item = ScalaComponent.builder[Props]("NavbarItem")
    .renderBackend[Backend]
    .build

}

