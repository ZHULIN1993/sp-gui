package spgui.widgets

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import spgui.SPWidget
import spgui.components.Button._
import spgui.components.Icon

object TestComponents {
  private class Backend($: BackendScope[Unit, Unit]) {
    def render() = {
      // Create a Button
      SPButton("Create Model", Callback.empty)
      // Create a Button with Callback
      SPButton("Create Model",
        Callback.log("SPButton clicked in TestComponents"))
      // Create a Button with Font Awesome Icon
      SPButton("Create Model With Arrow",
        Callback.empty, Icon.arrowDown)
      // Create a Button with an empty Dropdown-menu
      SPDropdown("Create Model With Dropdown", Callback.empty, Icon.arrowDown, Seq())
      // Create a Button with an Dropdown-menu
      SPDropdown("Create Model With Dropdown", Callback.empty, Icon.arrowDown,
        Seq(
          SPDropdownItem("Sub Item 1", Callback.log("Sub Item 1 - Pressed")),
          SPDropdownItem("Sub Item 2", Callback.log("Sub Item 2 - Pressed"))
        )
      )
    }
  }

  private val component = ScalaComponent.builder[Unit]("VDTracker")
    .renderBackend[Backend]
    .build

  def apply() = SPWidget(_ => component())
}

