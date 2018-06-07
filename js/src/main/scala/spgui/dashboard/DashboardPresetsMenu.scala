package spgui.dashboard

import diode.react.ModelProxy
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import spgui.circuit._
import spgui.components.{Icon, SPNavbarElements, SPNavbarElementsCSS}
import spgui.modal.{ModalResult, SimpleModal}
import spgui.theming.Theming.SPStyleSheet
import scalacss.DevDefaults._

/**
  * Created by alfredbjork on 2018-03-26.
  */

object DashboardPresetsCSS extends SPStyleSheet {
  import dsl._

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
  case class ProxyContents(presets: Set[DashboardPreset], widgets: OpenWidgets, widgetData: WidgetData)

  case class Props(proxy: ModelProxy[ProxyContents], config: Config = Config())


  class Backend($: BackendScope[Props, Unit]) {

    private def onSaveClicked(name: String)(implicit props: Props): Callback = {
      println("onSaveClicked()")
      val value = proxyValue(props)
      val newPreset = DashboardPreset(
        name,
        value.widgets,
        WidgetData(value.widgetData.xs.filterKeys(value.widgets.xs.keySet.contains))
      )

      props.config.onSave(newPreset)
      props.proxy.dispatchCB(savePreset(newPreset))
    }

    private def openSaveModal(implicit props: Props) = {
      props.proxy.dispatchCB(
        OpenModal(
          "Save preset",
          close => PresetNameModal(selectedPreset.map(_.name).getOrElse(""), close),
          onComplete = {
            case PresetNameModal.Return(name: String) => onSaveClicked(name)
            case x => Callback { println(x) }
          }
        )
      )
    }

    private def savePreset(preset: DashboardPreset) = {
      println("savePreset: returning AddDashboardPreset")
      AddDashboardPreset(preset)
    }
    private def deletePreset(preset: DashboardPreset) = RemoveDashboardPreset(preset)
    private def recallPreset(preset: DashboardPreset) = RecallDashboardPreset(preset)


    private def dashboardPresets(implicit props: Props): Set[DashboardPreset] = proxyValue(props).presets
    private def proxyValue(props: Props) = props.proxy.modelReader.value
    private def selectedPreset(implicit props: Props) = {
      val value = proxyValue(props)
      value.presets.find(_.widgets == value.widgets)
    }

    private def deleteClicked(e: ReactEventFromHtml): Boolean = {
      val deleteClassName = DashboardPresetsCSS.closeIcon.htmlClass
      e.target.classList.contains(deleteClassName) || e.target.parentElement.classList.contains(deleteClassName)
    }

    def dropDown(title: String)(contents: TagMod*) = SPNavbarElements.dropdown(title, contents)

    def renderPresets(implicit props: Props) = {
      def onPresetClick(e: ^.onClick.Event, preset: DashboardPreset): Callback = {
        if (deleteClicked(e))
          Callback {
            SimpleModal.open(
              title = s"Delete ${preset.name}?",
              onTrue = props.proxy.dispatchCB(deletePreset(preset))
            )
          }
        else
          props.proxy.dispatchCB(recallPreset(preset))
      }

      dashboardPresets(props).map { preset =>
        val icon = if (preset.isActive()) Icon.dotCircleO else Icon.circle

        SPNavbarElements.dropdownElement(Seq(
          ^.className := DashboardPresetsCSS.menuItem.htmlClass,
          ^.onClick ==> (e => onPresetClick(e, preset)),
          <.span(icon, ^.className := SPNavbarElementsCSS.textIconClearance.htmlClass),
          <.span(preset.name),
          <.span(Icon.times, ^.className := DashboardPresetsCSS.closeIcon.htmlClass)
        ).toTagMod
        )
      }.toTagMod
    }

    def render(props: Props) = {
      implicit val p: Props = props
      dropDown("Presets")(
        ^.className := DashboardPresetsCSS.menuUl.htmlClass,
        SPNavbarElements.dropdownElement(
          text = "Save preset",
          onClick = openSaveModal
        ),
        renderPresets
      )
    }

    implicit class PresetUtility(preset: DashboardPreset) {
      def isActive()(implicit props: Props) = {
        val value = proxyValue(props)
        selectedPreset.exists(_.widgets == value.widgets)
      }
    }
  }

  object Config {
    type Event = DashboardPreset => Unit
    val NoEvent: Event = _ => Unit
    case class State(onMount: () => Unit = () => Unit, onSave: Event = NoEvent, onLoad: Event = NoEvent, onDelete: Event = NoEvent)

    def onMount(onMount: () => Unit): Config = Config(onMount = onMount)
    def onSave(onSave: Event): Config = Config(onSave = onSave)
    def onLoad(onLoad: Event): Config = Config(onLoad = onLoad)
    def onDelete(onDelete: Event): Config = Config(onDelete = onDelete)
    def onUpdate(onUpdate: Props => Unit): Config = Config(onUpdate = onUpdate)
  }

  case class Config(
                     onMount: () => Unit = () => Unit,
                     onUpdate: Props => Unit = _ => Unit,
                     onSave: Config.Event = Config.NoEvent,
                     onLoad: Config.Event = Config.NoEvent,
                     onDelete: Config.Event = Config.NoEvent,
                   ) {
    import Config._

    def onMount(onMount: () => Unit): Config = copy(onMount = onMount)
    def onSave(onSave: Event): Config = copy(onSave = onSave)
    def onLoad(onLoad: Event): Config = copy(onLoad = onLoad)
    def onDelete(onDelete: Event): Config = copy(onDelete = onDelete)
    def onUpdate(onUpdate: Props => Unit): Config = copy(onUpdate = onUpdate)
  }

  private val component = ScalaComponent.builder[Props]("DashboardPresetsMenu")
    .renderBackend[Backend]
    .componentDidMount(ctx => Callback(ctx.props.config.onMount))
    .componentDidUpdate(ctx => Callback(ctx.currentProps.config.onUpdate(ctx.currentProps)))
    .build

  def apply(proxy: ModelProxy[ProxyContents], config: Config): VdomElement = {
    component(Props(proxy, config))
  }

}

object PresetNameModal {

  case class Props(close: Return => Callback, placeholderText: String = "")
  case class State(textBoxContent: String = "")
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