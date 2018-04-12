package spgui.dashboard

import java.util.UUID

import diode.Action
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import spgui.circuit._
import spgui.components.{Icon, SPNavbarElements, SPNavbarElementsCSS}
import spgui.modal.ModalResult

import scala.util.Random

/**
  * Created by alfredbjork on 2018-03-26.
  */
object DashboardPresetsMenu {

  type ProxyType = ModelProxy[(DashboardState, ModalState)]

  case class Props(
                  proxy: ProxyType,
                  onDidMount: () => Unit = () => Unit
                  )


  class Backend($: BackendScope[Props, Unit]) {

    private def openSaveModal(p: Props) = {
      p.proxy.dispatchCB(
        OpenModal(
          "Save preset",
          close => PresetNameModal(getSelectedPreset(dashboardState(p)).getOrElse(""), close),
          { case PresetNameModal.Return(name: String) => saveCurrentLayout(name, p.proxy.dispatchCB) }
        )
      )
    }

    private def saveCurrentLayout(name: String, dispatch: Action => Callback) = {
      dispatch(AddDashboardPreset(name))
    }

    private def recallLayout(p: DashboardPreset, dispatch: Action => Callback) = {
      dispatch(RecallDashboardPreset(p))
    }

    private def dashboardState(p: Props): DashboardState = p.proxy.modelReader.value._1

    private def presetIsSelected(preset: DashboardPreset, openWidgets: OpenWidgets) = preset.widgets.equals(openWidgets)

    private def getSelectedPreset(dashboardState: DashboardState): Option[String] =
      getSelectedPreset(dashboardState.presets, dashboardState.openWidgets)

    private def getSelectedPreset(presets: Map[String, DashboardPreset], openWidgets: OpenWidgets): Option[String] =
      presets.flatMap(
        {case (name, preset) => if (openWidgets.equals(preset.widgets)) Some(name) else None}
      ).headOption


    def didMount(p: Props) = {
      p.onDidMount()
    }

    def render(p: Props) = {
      SPNavbarElements.dropdown(
        "Layout",
        Seq(
          SPNavbarElements.dropdownElement(
            "Save layout",
            openSaveModal(p)
          )
        ) ++
        dashboardState(p).presets.map {
          case (name, preset) =>
            SPNavbarElements.dropdownElement(
              name, {
                if (presetIsSelected(preset, dashboardState(p).openWidgets)) Icon.dotCircleO else Icon.circle
              },
              recallLayout(preset, p.proxy.dispatchCB)
            )
        }
      )
    }

  }

  private val component = ScalaComponent.builder[Props]("DashboardPresetsMenu")
    //.initialState(State(""))
    .renderBackend[Backend]
    .componentDidMount(ctx => Callback(ctx.backend.didMount(ctx.props)))
    //.componentWillUnmount(_.backend.willUnmount())
    .build

  def apply(proxy: ProxyType, onDidMount: () => Unit = () => Unit): VdomElement =
    component(Props(proxy, onDidMount))
}

object PresetNameModal {
  case class Props(
                    close: Return => Callback,
                    placeholderText: String = ""
                  )

  case class State(
                  textBoxContent: String = ""
                  )

  case class Return(name: String) extends ModalResult

  class Backend($: BackendScope[Props, State]) {

    def render(props: Props, state: State) = {
      <.div(
        SPNavbarElements.TextBox(
          state.textBoxContent,
          "Preset name",
          i => $.modState(s => s.copy(textBoxContent = i))
        ),
        <.button(
          ^.onClick ==> (_ => props.close(Return(state.textBoxContent))),
          "Save"
        )
      )
    }
  }

  private val component = ScalaComponent.builder[Props]("PresetNameModal")
    .initialStateFromProps(p => State(p.placeholderText))
    .renderBackend[Backend]
    .build

  def apply(placeholderText: String = "", close: Return => Callback): VdomElement = component(Props(close, placeholderText))
}