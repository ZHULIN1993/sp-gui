package spgui.components

import scalacss.DevDefaults._
import scalacss.ScalaCssReact._

// TODO: Make CSS typesafe with Attr
object ButtonCSS extends StyleSheet.Inline {
  import dsl._

  val spButtonStyle = style("spButton")(
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
    marginTop :=! "0px",
    marginRight :=! "4px",
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

  val spDropdownButtonStyle = style("spDropdownButton")(
    backgroundColor.white,
    color :=! "#df691a",
    textAlign.center,
    textDecoration := "none",
    fontSize(16.px),
    //transitionDuration :=! "400ms",
    transitionDuration :=! inherit,
    cursor.pointer,
    padding :=! "5px 10px",
    margin(5.px, 10.px, 5.px, 0.px),
    whiteSpace :=! "nowrap",
    borderRadius(4.px),
    border :=! "none",

    unsafeChild("ul")(
      visibility.hidden,
      display :=! "none",
      transition := "all 0.5s ease",
      left :=! "0",
      opacity :=! "0",
      minWidth(5.rem),
      marginTop(1.rem),
      position.absolute,

      unsafeChild("spButtonStyle")(
        clear.both,
        width(100.%%)
      )

    ),

    &.hover( // on hover
      backgroundColor :=! "#df691a",
      color :=! "#FFF",
      unsafeChild("ul")(
        visibility.visible,
        opacity :=! "1",
        display :=! "block"
      )
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
    transitionDuration.inherit,
    color.inherit,
    backgroundColor.inherit
  )

  val customSPButtonCSSInSPDropdown = style(
    unsafeRoot(".spDropdownButton ul li .spButton")(
      backgroundColor.gray
    )
  )

  val unsortedList = style("custom_ul")(
    margin(0.px),
    border(0.px),
    listStyle := "none"
  )

  this.addToDocument()
}

