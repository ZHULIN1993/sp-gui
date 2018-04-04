package spgui.dashboard

import java.util.UUID

import diode.Action
import diode.react.ModelProxy
import japgolly.scalajs.react.{BackendScope, Callback, CallbackTo, ScalaComponent}
import japgolly.scalajs.react.vdom.html_<^.{<, VdomElement, ^}
import spgui.circuit._
import spgui.components.{Icon, SPNavbarElements, SPNavbarElementsCSS}

import scala.util.Random

/**
  * Created by alfredbjork on 2018-03-26.
  */
object DashboardPresetsMenu {

  case class State(
                    textBoxValue: String
                  )

  case class Props(
                  proxy: ModelProxy[DashboardPresets]
                  )


  class Backend($: BackendScope[ModelProxy[DashboardPresets], State]) {

    private def saveCurrentLayout(name: String, dispatch: Action => Callback) = {
      val newPreset = DashboardPreset(SPGUICircuit.zoom(_.openWidgets).value)
      dispatch(AddDashboardPreset(name, newPreset)) >> $.setState(State(""))
    }

    private def recallLayout(p: DashboardPreset) = {
        Callback(SPGUICircuit.dispatch(RecallDashboardPreset(p))) //TODO: make component re-render after this
    }

    private def presetIsSelected(preset: DashboardPreset) = {
      val currentState = SPGUICircuit.zoom(_.openWidgets).value
      preset.widgets.equals(currentState)
    }

    def render(s: State, proxy: ModelProxy[DashboardPresets]) = {
      SPNavbarElements.dropdown(
        "Layout",
        Seq(
          SPNavbarElements.TextBox(
            s.textBoxValue,
            "Layout name",
            (t: String) => { $.modState( _s => _s.copy(textBoxValue = t)) }
          ),
          SPNavbarElements.dropdownElement(
            "Save layout",
            saveCurrentLayout(s.textBoxValue, proxy.dispatchCB)
          )
        ) ++
        proxy.modelReader.value.presets.map {
          case (name, preset) =>
            SPNavbarElements.dropdownElement(
              name, {
                if (presetIsSelected(preset)) Icon.dotCircleO else Icon.circle
              },
              recallLayout(preset)
            )
        }
      )
    }

  }

  private val component = ScalaComponent.builder[ModelProxy[DashboardPresets]]("DashboardPresetsMenu")
    .initialState(State(""))
    .renderBackend[Backend]
/*    .componentDidMount(_.backend.didMount)
    .componentWillUnmount(_.backend.willUnmount)*/
    .build


  def apply(proxy: ModelProxy[DashboardPresets]) = component(proxy)
}