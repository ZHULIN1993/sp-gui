package spgui.dashboard

import diode.Action
import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import spgui.circuit._
import spgui.components.{Icon, SPNavbarElements, SPNavbarElementsCSS}
import spgui.dashboard
import spgui.modal.{ModalResult, SimpleModal}
import spgui.theming.Theming.SPStyleSheet

import scalacss.DevDefaults._

/**
  * Created by alfredbjork on 2018-03-26.
  */

object DashboardPresetsCSS extends SPStyleSheet {
  import dsl._
  import spgui.theming.Theming

  val closeIcon = style(
    marginLeft(6.px),
    opacity(0),
    float.right,
    color(_rgb(theme.value.navbarBackgroundColor)),
    &.hover(
      color(_rgb(theme.value.navbarButtonTextColor))
    )
  )

  val menuItem = style(
  )

  val menuUl = style(
    unsafeChild("." + menuItem.htmlClass + ":hover ." + closeIcon.htmlClass)(
      opacity(1)
    )
  )

  this.addToDocument()
}

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

    private def deleteLayout(name: String, dispatch: Action => Callback) = {
      dispatch(RemoveDashboardPreset(name))
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

    private def clickIsOnDeleteButton(e: ReactEventFromHtml): Boolean = {
      val deleteClassName = DashboardPresetsCSS.closeIcon.htmlClass
      e.target.classList.contains(deleteClassName) || e.target.parentElement.classList.contains(deleteClassName)
    }

    def didMount(p: Props) = {
      p.onDidMount()
    }

    def render(p: Props) = {

      SPNavbarElements.dropdown(
        "Layout",
        Seq(
          TagMod(^.className := DashboardPresetsCSS.menuUl.htmlClass),
          SPNavbarElements.dropdownElement(
            "Save layout",
            openSaveModal(p)
          )
        ) ++
        dashboardState(p).presets.map {
          case (name, preset) => {

            val icon = if (presetIsSelected(preset, dashboardState(p).openWidgets)) Icon.dotCircleO else Icon.circle

            SPNavbarElements.dropdownElement(
                Seq(
                  TagMod(^.className := DashboardPresetsCSS.menuItem.htmlClass),
                  TagMod(^.onClick ==> (
                    e => {
                      if (clickIsOnDeleteButton(e))
                        Callback(SimpleModal.open("Delete " + "\"" + name + "\"?", deleteLayout(name, p.proxy.dispatchCB)))
                      else
                        recallLayout(preset, p.proxy.dispatchCB)
                    }
                  )),
                  <.span(icon, ^.className := SPNavbarElementsCSS.textIconClearance.htmlClass),
                  <.span(name),
                  <.span(Icon.times, ^.className := DashboardPresetsCSS.closeIcon.htmlClass)
                ).toTagMod
            )
          }
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