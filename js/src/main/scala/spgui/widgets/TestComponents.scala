package spgui.widgets

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import spgui.SPWidget
import spgui.components.Button._
import spgui.components.Icon
import spgui.components._

object TestComponents {
  private class Backend($: BackendScope[Unit, Unit]) {
    def render() = {
      <.div(
        <.div(
          // Create a Button
          SPButton("Create Model", Callback.empty),
          // Create a Button with Callback
          SPButton("Create Model",
            Callback.log("SPButton clicked in TestComponents")),
          // Create a Button with Font Awesome Icon
          SPButton("Create Model With Arrow",
            Callback.empty, Icon.arrowDown)
        ),
        <.div(
          // Create a Button with an empty Dropdown-menu
          SPDropdown("Create Model With Dropdown", Callback.empty, Icon.arrowDown, Seq()),
          // Create a Button with an Dropdown-menu
          SPDropdown("Create Model With Dropdown", Callback.empty, Icon.arrowDown,
            Seq(
              SPDropdownItem("Sub Item 1", Callback.log("Sub Item 1 - Pressed")),
              SPDropdownItem("Sub Item 2", Callback.log("Sub Item 2 - Pressed"))
            )
          )
        ),
        <.div(
          <.h1("Table"),
          Table(tableHeaders, seq, Callback.empty, true)
        )
      )
    }

    val test1: (String, String, String) = ("Hi", "Hi", "Hi")
    val test2: (String, String, String) = ("Hi", "Hi", "Hi")
    val test3: (String, String, String) = ("Hi", "Hi", "hipoisfsäfljjjölskjfjjflkjsafjfjfjHi")
    val seq = Seq(test1,test2,test3)

    val tableHeaders = Vector(
      Table.ColumnData("Name"),
      Table.ColumnData("Id"),
      Table.ColumnData("Value")
    )
  }

  private val component = ScalaComponent.builder[Unit]("VDTracker")
    .renderBackend[Backend]
    .build

  def apply() = SPWidget(_ => component())
}

