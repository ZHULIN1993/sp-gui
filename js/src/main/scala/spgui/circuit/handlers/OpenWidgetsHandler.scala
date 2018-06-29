package spgui.circuit.handlers

import diode.{ActionHandler, ModelRW}
import spgui.circuit._
import spgui.dashboard.Dashboard

class OpenWidgetsHandler[M](modelRW: ModelRW[M, OpenWidgets]) extends ActionHandler(modelRW) {
  override def handle = {
    case AddWidget(widgetType, width, height, id) =>
      val occupiedGrids = value.widgetMap.values.flatMap(w =>
        for{x <- w.layout.x until w.layout.x + w.layout.w} yield {
          for{y <- w.layout.y until w.layout.y + w.layout.h} yield {
            (x, y)
          }
        }
      ).flatten
      val bestPosition:Int = Stream.from(0).find(i => {
        val x = i % Dashboard.cols
        val y = i / Dashboard.cols

        val requiredGrids = (for{reqX <- x until x + width} yield {
          for{reqY <- y until y + height} yield {
            (reqX, reqY)
          }
        }).toSeq.flatten
        requiredGrids.forall(req =>
          occupiedGrids.forall(occ =>
            !(occ._1 == req._1 && occ._2 == req._2 || req._1 >= Dashboard.cols)
          )
        )
      }).get
      val x:Int = bestPosition % Dashboard.cols
      val y:Int = bestPosition / Dashboard.cols
      val newWidget = OpenWidget(
        id,
        WidgetLayout(x, y, width, height),
        widgetType
      )
      updated(OpenWidgets(value.widgetMap + (id -> newWidget)))
    case CloseWidget(id) =>
      updated(OpenWidgets(value.widgetMap - id))
    case CollapseWidgetToggle(id) =>
      val targetWidget = value.widgetMap.get(id).get
      val modifiedWidget = targetWidget.layout.h match {
        case 1 => targetWidget.copy(
          layout = targetWidget.layout.copy(
            collapsedHeight = 1,
            h = targetWidget.layout.collapsedHeight match {
              // this deals with the fact that panels can already have a height of 1
              // it would be strange to "restore" the height to the current height
              case 1 => 4
              case _ => targetWidget.layout.collapsedHeight
            }
          )
        )
        case _ => targetWidget.copy(
          layout = targetWidget.layout.copy(
            collapsedHeight = targetWidget.layout.h ,
            h = 1
          )
        )
      }
      updated(OpenWidgets((value.widgetMap - id ) + (id -> modifiedWidget)))
    case CloseAllWidgets => updated(OpenWidgets())
    case UpdateLayout(id, newLayout) => {
      val updW = value.widgetMap.get(id)
        .map(_.copy(layout = newLayout))
        .map(x => value.widgetMap + (x.id -> x))
        .getOrElse(value.widgetMap)
      updated(OpenWidgets(updW))
    }
    case SetLayout(newLayout) => {
      val updW = OpenWidgets(value.widgetMap.map(pair =>
        (
          pair._1,
          pair._2.copy(
            layout = newLayout(pair._1)
          )
        )
      ))
      updated(updW)
    }
  }
}