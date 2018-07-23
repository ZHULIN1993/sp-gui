package TempMAIN.components

import japgolly.scalajs.react.vdom.html_<^._
/** List of all widgets and we can add new widgets to the list,
  * get the map with [String, (SPWidgetBase => VdomElement, Int, Int)]
  *
  */
object WidgetList {

  /** A case class for a Widget
    *
    * @param name string
    * @param spwb2Vdom Tag: DocHelp
    * @param width int representing the size in the grid
    * @param height int representing the size in the grid
    */
  case class Widget(name :String, spwb2Vdom: SPWidgetBase => VdomElement, width: Int, height: Int)

  /** Tag: DocHelp. Why private var? */
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
    list.map(t => t.name -> (t.spwb2Vdom, t.height, t.width)).toMap
}
