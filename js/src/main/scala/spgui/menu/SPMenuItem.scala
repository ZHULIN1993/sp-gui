package spgui.menu

import java.util.UUID

import japgolly.scalajs.react.ScalaComponent
import japgolly.scalajs.react.vdom.html_<^.VdomElement
import sp.domain.SPValue
import spgui.circuit._

/**
  * Created by alfredbjork on 2018-03-21.
  */

trait SPMenuItem {
  case class Props()

  /*private val component = ScalaComponent.builder[Props]("SpMenuItemComp")
    .render_P(p => p.renderMenuItem)
    .build*/

  def apply() : VdomElement
}
