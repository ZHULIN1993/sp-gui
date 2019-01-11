package spgui.components

import japgolly.scalajs.react.component.Scala.BackendScope
import japgolly.scalajs.react.vdom.VdomElement
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{Callback, ScalaComponent}
import spgui.components.{TableCSS => css}

object Table {
  case class ColumnData(label: String, style: String = css.none.htmlClass)

  case class Props[A <: Product](columnData: Vector[ColumnData], rows: Iterable[A],
                                 onClick: Callback, smallTable: Boolean = false)

  private class Backend[A <: Product]($: BackendScope[Props[A], Option[Int]]) {

    def render(props: Props[A]) = {
      <.table(
        ^.className := css.table.htmlClass,
        ^.onClick --> props.onClick,
        <.thead(
          <.tr(
            ^.className := css.headerRow.htmlClass,
            props.columnData.map(d =>
              <.th(
                ^.className := {if(props.smallTable) css.smallHeaderCell.htmlClass else css.largeHeaderCell.htmlClass},
                ^.className := d.style,
                d.label
              )
            ).toTagMod
          )
        ),
        <.tbody(
          props.rows.map(renderRow(_, props.smallTable)).toTagMod
        )
      )
    }

    def renderRow(row: A, smallTable: Boolean): TagMod = {
      val values: List[TagMod] = row.productIterator.toList.map {
        case v: VdomElement => v
        case v => TagMod(v.toString)
      }

      val cells = values.map(v =>
        <.td(^.className :=
          {if(smallTable) css.smallDataCell.htmlClass else css.largeDataCell.htmlClass}, v)
      )

      <.tr(^.className := css.dataRow.htmlClass, cells.toTagMod)
    }

  }

  private def component[A <: Product] = ScalaComponent.builder[Props[A]]("Table")
    .initialState(None: Option[Int])
    .renderBackend[Backend[A]]
    .build

  def apply[A <: Product](headers: Vector[ColumnData], rows: Iterable[A],
                          onClick: Callback = Callback.empty, smallTable: Boolean = false): VdomElement = {
    component[A](Props(headers, rows, onClick, smallTable))
  }
}

