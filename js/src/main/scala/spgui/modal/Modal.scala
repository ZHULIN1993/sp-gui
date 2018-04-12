package spgui.modal

import japgolly.scalajs.react._
import vdom.all._
import extra._
import diode.react.ModelProxy
import japgolly.scalajs.react.{BackendScope, ScalaComponent}
import japgolly.scalajs.react.vdom.html_<^.{<, EmptyVdom, ^}
import spgui.circuit.{CloseModal, ModalState}
import spgui.components.Icon

import scala.scalajs.js

/**
  * Created by alfredbjork on 2018-04-10.
  */

trait ModalResult

object Modal {
  case class Props(
                    proxy: ModelProxy[ModalState]

                  )

  case class State()

  private val noComponent : VdomElement = div("No content")

  private def noOnComplete : Callback = Callback(println("No onComplete function provided"))


  class Backend($: BackendScope[Props, State]) {

    def render(props: Props) = {

      val state = props.proxy.modelReader.value

      val close = props.proxy.dispatchCB(CloseModal)

      def handleInnerClose(returnItem: ModalResult): Callback =
        state.onComplete.getOrElse((_: ModalResult) => noOnComplete)(returnItem) >> close


      val modalStyle =
        if (state.modalVisible) TagMod(display.block)
        else TagMod(display.none)

      val heading: Option[VdomElement] = if (state.title.nonEmpty) Some(<.h1(state.title)) else None

      div(className := "modal", /*id := props.modalId,*/ modalStyle)(
        div(className := ModalCSS.fadeScreen.htmlClass,
          onClick ==> (e => e.stopPropagationCB >> close))(
          div(cls := ModalCSS.window.htmlClass,
            /*props.modalCss,*/
            onClick ==> (e => e.stopPropagationCB))(
            div(cls := ModalCSS.header.htmlClass)(
              div(cls := ModalCSS.close.htmlClass,
                onClick ==> (e => e.stopPropagationCB >> close),
                role := "button",
                title := "Close window",
                Icon.timesCircle),
              h2(cls := ModalCSS.title.htmlClass, state.title).when(state.title.nonEmpty)
            ),
            div(cls := ModalCSS.inner.htmlClass)(
              state.component.getOrElse((_: ModalResult => Callback)=> noComponent)(handleInnerClose)
            )
          )
        )
      )
    }
  }

  private val component = ScalaComponent.builder[Props]("Modal")
    .initialState(State())
    .renderBackend[Backend]
    .build

  def apply(proxy: ModelProxy[ModalState]) = component(Props(proxy))
}

object DummyModal {
  case class Props(
                    close: ModalResult => Callback
                  )

  case class State()

  case object Return extends ModalResult

  class Backend($: BackendScope[Props, State]) {

    def render(props: Props) = {

      div(cls := "button",
        onClick ==> (e => e.stopPropagationCB >> props.close(Return)),
        role := "button",
        title := "Close window",
        Icon.circle)
    }
  }

  private val component = ScalaComponent.builder[Props]("DummyModal")
    .initialState(State())
    .renderBackend[Backend]
    .build

  def apply(close: ModalResult => Callback): VdomElement = component(Props(close))
}
