package spgui.dashboard

import java.util.UUID

import japgolly.scalajs.react.{BackendScope, Callback, CallbackTo, ScalaComponent}
import japgolly.scalajs.react.vdom.html_<^.{<, VdomElement, ^}
import spgui.circuit._
import spgui.components.{Icon, SPNavbarElements, SPNavbarElementsCSS}

import scala.util.Random

/**
  * Created by alfredbjork on 2018-03-26.
  */
object DashboardPresetsMenu {

  case class DashboardPreset(
                              name: String,
                              widgets: Map[UUID, OpenWidget]
                            )

  case class State(
                    selectedPreset: DashboardPreset,
                    availablePresets: Seq[DashboardPreset],
                    textBoxValue: String
                  )


  class Backend($: BackendScope[Unit, State]) {

    def saveCurrentLayout(s: State) = {
      val newPreset = DashboardPreset(s.textBoxValue, SPGUICircuit.zoom(_.openWidgets).value.xs)

      $.modState(
        _s =>
          _s.copy(
            selectedPreset = newPreset,
            availablePresets = _s.availablePresets :+ newPreset,
            textBoxValue = ""
          )
      )
    }

    def recallLayout(p: DashboardPreset) = {
        /*Callback(SPGUICircuit.dispatch(CloseAllWidgets)) >>
        Callback(p.widgets.values.foreach(
          w => {
            SPGUICircuit.dispatch(AddWidget(
              w.widgetType,
              w.layout.w,
              w.layout.h,
              UUID.randomUUID()
            ))
          }
        )) >> */
        Callback(SPGUICircuit.dispatch(RecallDashboardPreset(OpenWidgets(p.widgets)))) >>
        $.modState(s => s.copy(selectedPreset = p))
    }

    def render(s: State) = {
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
            saveCurrentLayout(s)
          )
        ) ++
        s.availablePresets.map(x =>
          SPNavbarElements.dropdownElement(
            x.name, {
              if (s.selectedPreset.name == x.name) Icon.dotCircleO else Icon.circle
            },
            recallLayout(x)
          )
        )
      )
    }

  }

  private val component = ScalaComponent.builder[Unit]("DashboardPresetsMenu")
    .initialState(State(DashboardPreset("", Map()), Seq(), ""))
    .renderBackend[Backend]
/*    .componentDidMount(_.backend.didMount)
    .componentWillUnmount(_.backend.willUnmount)*/
    .build


  def apply() = component()

}