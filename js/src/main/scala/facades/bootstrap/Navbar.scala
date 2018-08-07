package facades.bootstrap

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

/**
  * Created by alexa on 06/08/2018.
  */

object Navbar {
  case class Props(brand: Option[VdomElement] = None, navItems: Seq[VdomElement] = Seq())
  case class State()

  class Backend($: BackendScope[Props, State]) {
    def render(p: Props, s: State) = {
      <.nav(  ^.className := "navbar navbar-expand-lg navbar-light bg-light",
        <.a(^.className := "navbar-brand", ^.onClick --> brandClicked(p, s), p.brand.getOrElse(TagMod())),
        <.div(^.className := "collapse navbar-collapse",
          <.ul(^.className := "navbar-nav mr-auto",
            p.navItems.map{ item =>
                <.span(item)
            }.toTagMod
          )
        )
      )
    }

    private def brandClicked(p: Props, s: State) = Callback(println("Brand Clicked"))

  }

  val component = ScalaComponent.builder[Props]("Navbar")
    .initialState(State())
    .renderBackend[Backend]
    .build

  def apply() = component(Props())

  def apply(props: Props) = component(props)
}

