package spgui.utils

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import spgui.components.SPWidgetBase

/** List of all widgets and we can add new widgets to the list,
  * get the map with [String, (SPWidgetBase => VdomElement, Int, Int)]
  *
  * Tag: DocHelp.
  * If feels like we are not doing a straight way forward
  * from circuit to frontend, while we are tracking the current state of the
  * React-component through circuit and "globalState" with old circuit.
  * Maybe there are some good intentions that I can't figure out right now.

  * If we have all widgets in circuit, why do we need to track a different
  * singelton containing the same widgets in a list?
  *
  * Is there problems with adding widgets from other projects like sp-control?
  */
object WidgetList {

  /** A case class for a Widget
    *
    * @param name string
    * @param renderWidget Tag: DocHelp
    * @param width int representing the size in the grid
    * @param height int representing the size in the grid
    */
  case class Widget(name: String, renderWidget: SPWidgetBase => VdomElement, width: Int, height: Int)

  /** private var to add widgets from example sp-control */
  private var widgetList: List[Widget] = List()

  /** Add list of widgets to the widget-list
    *
    * @param widgets list of widgets
    */
  def addWidgets(widgets: List[Widget]): Unit = {
    widgetList ++= widgets
  }

  def list: List[Widget] = widgetList
  def map: Map[String, ((SPWidgetBase) => VdomElement, Int, Int)] =
    list.map(widget => widget.name -> (widget.renderWidget, widget.height, widget.width)).toMap
}
