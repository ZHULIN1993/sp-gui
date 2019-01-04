package spgui.components

import scalacss.DevDefaults._
import scalacss.ScalaCssReact._
import scalacss.internal.ValueT
object ButtonCSS extends StyleSheet.Inline {
  import dsl._
  val spButtonStyle = style(
    backgroundColor.white,
    color :=! "#df691a",
    border :=! "2px solid #df691a",
    textAlign.center,
    textDecoration := "none",
    fontSize(16.px),
    //transitionDuration :=! "400ms",
    transitionDuration :=! inherit,
    cursor.pointer,
    padding :=! "5px 10px",
    borderRadius :=! "4px",
    marginBottom :=! "0px",
    whiteSpace :=! "nowrap",

    &.hover( // on hover
      backgroundColor :=! "#df691a",
      color :=! "#FFF"
    ),
    &.focus( // while focused, removes blue border
      outline :=! "0"
    ),
    &.active( // while mouse held down
      backgroundColor :=! "#d15b0c"
    )
  )

  val spDropdownButton = style(
    backgroundColor.white,
    color :=! "#df691a",
    textAlign.center,
    textDecoration := "none",
    fontSize(16.px),
    //transitionDuration :=! "400ms",
    transitionDuration :=! inherit,
    cursor.pointer,
    padding :=! "5px 10px",
    marginBottom :=! "0px",
    whiteSpace :=! "nowrap",
    &.hover( // on hover
      backgroundColor :=! "#df691a",
      color :=! "#FFF"
    ),
    &.focus( // while focused, removes blue border
      outline :=! "0"
    ),
    &.active( // while mouse held down
      backgroundColor :=! "#d15b0c"
    )
  )

  val textBeforeIcon = style(
    paddingRight :=! "6px",
  )

  val icon = style(
    transitionDuration :=! inherit,
    color :=! inherit,
    backgroundColor :=! inherit
  )

  val spDropdownStyle = style(

  )

  this.addToDocument()
}

