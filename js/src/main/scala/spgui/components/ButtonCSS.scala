package spgui.components

import scalacss.DevDefaults._
import scalacss.ScalaCssReact._
object ButtonCSS extends StyleSheet.Inline {
  import dsl._
  val button = style(
    backgroundColor.white.important,
    color.darkgray.important,
    //border :=! "2px solid #555555",
    border :=! "2px solid",
    border.darkgray.important,

    textAlign.center,
    textDecoration := "none",

    fontSize(16.px),
    transitionDuration :=! "400ms",
    cursor.pointer,

    &.hover(
      backgroundColor.darkgray.important,
      color.white.important
    )

  )
  this.addToDocument()
}

