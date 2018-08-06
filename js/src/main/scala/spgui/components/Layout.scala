package spgui.components

import java.util.UUID

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import spgui.theming.SPCSS

object Layout {

  case class LayoutProps(id: UUID, content: Option[VdomElement])

  class Backend($: BackendScope[LayoutProps, Unit]) {
    def render(p: LayoutProps) = {
      <.div(
        p.content.whenDefined(content => <.div(^.className := SPCSS.layout.htmlClass, content))
      )
    }
  }

  private val component = ScalaComponent.builder[LayoutProps]("Layout")
    .renderBackend[Backend]
    .build

  def apply(id: UUID, content: Option[VdomElement]) = component(LayoutProps(id, content))
  def apply(content: Option[VdomElement]) = component(LayoutProps(UUID.randomUUID(), content))
  def apply(id: UUID) = component(LayoutProps(id, None))
  def apply() = component(LayoutProps(UUID.randomUUID(), None))
}

