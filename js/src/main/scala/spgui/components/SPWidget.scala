package spgui.components

import java.util.UUID

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

/** case class for the bare minimal of a SPWidget.
  * SPWidgetBase is connected to circuit where
  * it's current state is tracked
  *
  * TODO: Add state from circuit and implement events
  */
case class SPWidgetBase(id: UUID, width: Int, height: Int)

/** The SPWidget, every widget in SP uses this React Component
  *
  * When creating a new widget
  * object ExampleWidget {
  *
  *   val component = ScalaComponent.builder[Unit]("ExampleWidget")
  *     .render(
  *       <.div(
  *         <.h1("Hello World!")
  *       )
  *     )
  *     .build
  *
  *     def apply() = spgui.components.SPWidget(spwb => component())
  * }
  *
  * */
object SPWidget {
  case class Props(name: String, spwb: SPWidgetBase, renderWidget: SPWidgetBase => VdomElement)

  val component = ScalaComponent.builder[Props]("SPWidget")
    .render_P(props => props.renderWidget(props.spwb))
    .build

  def apply(name: String, width: Int, height: Int,
            renderWidget: SPWidgetBase => VdomElement): SPWidgetBase => VdomElement =
    spwb => component(Props(name, spwb, renderWidget))
}