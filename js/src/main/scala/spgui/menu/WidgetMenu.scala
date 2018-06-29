package spgui.menu

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.vdom.all.aria

import spgui.circuit.{AddWidget, SPGUICircuit}
import spgui.WidgetList
import spgui.WidgetList.Widget
import spgui.components.{ Icon, SPNavbarElements }

/** Navbar component for the widgets.
  * Can filter the different widgets from the widgetlist
  */
object WidgetMenu {
  /** The text that should be filtered */
  case class State(filterText: String = "")

  class Backend($: BackendScope[Unit, State]) {
    /** Add a new widget to the circuit */
    def addWidget(name: String, width: Int, height: Int): Callback =
      Callback(SPGUICircuit.dispatch(AddWidget(name, width, height)))

    def render(s: State) =
      SPNavbarElements.dropdown(
        "New widget",
        SPNavbarElements.TextBox(
          s.filterText,
          "Find widget...",
          (t: String) => { $.setState(State(filterText = t)) }
        ) :: WidgetList.list.collect{
          case w: Widget if w.name.toLowerCase.contains(s.filterText.toLowerCase)=>
            SPNavbarElements.dropdownElement(
              w.name,
              addWidget(w.name, w.height, w.width)
            )
        }
      )
  }

  private val component = ScalaComponent.builder[Unit]("WidgetMenu")
    .initialState(State())
    .renderBackend[Backend]
    .build

  def apply() = component()
}
