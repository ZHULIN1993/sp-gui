package spgui.components

import scalacss.DevDefaults._
import scalacss.ScalaCssReact._

/** Define the css-classes for the DriverWidget with ScalaCSS */
object TableCSS extends StyleSheet.Inline {
  import dsl._

  private val space = 10.px
  private val headerColor = c"#7B8383"
  private val valueColor = c"#606879"
  private val borderColor = c"#D7D0D0"
  private val headerBgColor = c"#F2F6F6"
  private val cellHoverColor = c"#E1E4EA"
  private val bgColor = c"#FFFFFC"

  private val font = "'Roboto', sans-serif"

  val none = style("none")()

  val table = style(
    width :=! "calc(100% - 15px)",
    marginLeft(5.px),
    tableLayout.auto,
    fontFamily :=! font
  )

  val cell = style(
    margin.`0`,
    whiteSpace.nowrap,
    overflow.hidden,
    textOverflow := "ellipsis",
    borderLeft :=! s"2px solid ${borderColor.value}",
    borderBottom :=! s"2px solid ${borderColor.value}"
  )

  val smallCell = style(
    cell,
    maxWidth(150.px),
    paddingRight(6.px),
    paddingLeft(6.px),
    paddingTop(3.px),
    paddingBottom(3.px),
    fontSize(16.px),
    minWidth(70.px)
  )

  val largeCell = style(
    cell,
    maxWidth(200.px),
    padding(10.px),
    fontSize(20.px),
    minWidth(80.px)
  )

  val headerCell = style(
    backgroundColor(headerBgColor),
    color(headerColor),
    borderTop :=! s"2px solid ${borderColor.value}"
  )

  val smallHeaderCell = style(
    smallCell,
    headerCell
  )

  val largeHeaderCell = style(
    largeCell,
    headerCell
  )

  val dataCell = style(
    color(valueColor),
    backgroundColor.inherit,
    textOverflow := "ellipsis",
    whiteSpace.nowrap,
    overflow.hidden
  )

  val smallDataCell = style(
    smallCell,
    dataCell
  )

  val largeDataCell = style(
    largeCell,
    dataCell
  )

  val row = style(
    unsafeChild("td")(
      &.lastChild(
        borderRight :=! s"2px solid ${borderColor.value}"
      )
    ),
    unsafeChild("th")(
      &.lastChild(
        borderRight :=! s"2px solid ${borderColor.value}"
      )
    )
  )

  val headerRow = style(
    row
  )

  val dataRow = style(
    row,
    backgroundColor(bgColor),
    &.hover(
      backgroundColor(cellHoverColor)
    )
  )

  this.addToDocument()
}

