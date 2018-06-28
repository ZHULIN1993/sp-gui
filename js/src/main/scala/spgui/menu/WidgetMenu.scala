package spgui.menu

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.vdom.all.aria

import spgui.circuit.{AddWidget, SPGUICircuit}
import spgui.WidgetList
import spgui.WidgetList.Widget
import spgui.components.{ Icon, SPNavbarElements }

/**
  *
  */
object WidgetMenu {
  case class State(filterText: String = "")

  class Backend($: BackendScope[Unit, State]) {
    def addW(name: String, w: Int, h: Int): Callback =
      Callback(SPGUICircuit.dispatch(AddWidget(name, w, h)))

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
              addW(w.name, w.height, w.width)
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
