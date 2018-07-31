package spgui.components

import java.util.UUID

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

// Case class for casting widgetbase to VdomElement
case class SPWidgetBase(id: UUID)

/** The SPWidget, every widget in SP uses this React Component */
object SPWidget {
  case class Props(spwb: SPWidgetBase, renderWidget: SPWidgetBase => VdomElement)

  val Component = ScalaComponent.builder[Props]("SPWidget")
    .render_P(props => props.renderWidget(props.spwb))
    .build
}