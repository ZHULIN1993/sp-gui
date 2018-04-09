package spgui.dashboard

import java.util.UUID

import diode.Action
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
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
                  proxy: ModelProxy[DashboardState],
                  onDidMount: () => Unit = () => Unit
                  )


  class Backend($: BackendScope[Props, State]) {

    private def saveCurrentLayout(name: String, dispatch: Action => Callback) = {
      dispatch(AddDashboardPreset(name)) >> $.setState(State(""))
    }

    private def recallLayout(p: DashboardPreset) = {
        Callback(SPGUICircuit.dispatch(RecallDashboardPreset(p)))
    }

    private def presetIsSelected(preset: DashboardPreset) = {
      val currentState = SPGUICircuit.zoom(_.dashboard.openWidgets).value
      preset.widgets.equals(currentState)
    }

    def didMount(p: Props) = {
      p.onDidMount()
    }

    def render(s: State, p: Props) = {
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
            saveCurrentLayout(s.textBoxValue, p.proxy.dispatchCB)
          )
        ) ++
        p.proxy.modelReader.value.presets.map {
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

  private val component = ScalaComponent.builder[Props]("DashboardPresetsMenu")
    .initialState(State(""))
    .renderBackend[Backend]
    .componentDidMount(ctx => Callback(ctx.backend.didMount(ctx.props)))
    //.componentWillUnmount(_.backend.willUnmount())
    .build

  def apply(proxy: ModelProxy[DashboardState], onDidMount: () => Unit = () => Unit): VdomElement =
    component(Props(proxy, onDidMount))
}