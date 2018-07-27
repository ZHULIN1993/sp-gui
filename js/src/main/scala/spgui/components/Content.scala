package spgui.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.component.Scala
import japgolly.scalajs.react.internal.Box
import japgolly.scalajs.react.vdom.html_<^._

/** Top-Level trait to add to Layout
  * Every component can extend Content to add
  * application-flexibility
  */
object Content {

  //  private val component = ScalaComponent.builder[Props...]("Content")
  // In JS <.div( {props.children},
  // SEE scalajs-spa-tutorial for old scalajs-react types
  // in Panel with renderPC(_, props, children)
  // ALSO checkout the docs for Children.Varargs / Children.None in scalajs-react by japgolly
  // React.createClass in JS seems to be the old way to go, but sounds like it soon will be deprecated?!?
  private val component = ScalaComponent.builder[Unit]("Content")
    .renderC((state, children) => <.div(children))
    .build

  def apply() = component

  type SPContent = Scala.Component[_, _, _, CtorType.Summoner.Aux[Box[Unit], Children.Varargs, CtorType.Children]#CT]
}


