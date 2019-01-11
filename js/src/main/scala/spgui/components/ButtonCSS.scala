package spgui.components

import scalacss.DevDefaults._
import scalacss.ScalaCssReact._

// TODO: Make CSS typesafe with Attr
object ButtonCSS extends StyleSheet.Inline {
  import dsl._

  private val spOrange = c"#df691a"
  private val spOrangeDark = c"#d15b0c"

  val spButtonStyle = style("spButton")(
    backgroundColor.white,
    color(spOrange),
    border :=! s"2px solid ${spOrange.value}",
    textAlign.center,
    textDecoration := "none",
    fontSize(16.px),
    //transitionDuration :=! "400ms",
    transitionDuration.inherit,
    cursor.pointer,
    padding(5.px, 10.px),
    borderRadius(4.px),
    marginTop(0.px),
    marginRight(4.px),
    marginBottom(0.px),
    whiteSpace.nowrap,

    &.hover( // on hover
      backgroundColor(spOrange),
      color.white,
      boxShadow := "0 4px 8px 0 rgba(0, 0, 0, 0.3), 0 6px 20px 0 rgba(0, 0, 0, 0.28)"
    ),
    &.focus( // while focused, removes blue border
      outline.`0`
    ),
    &.active( // while mouse held down
      backgroundColor(spOrangeDark),
      border :=! s"2px solid ${spOrangeDark.value}"
    )
  )

  val spDropdownButtonStyle = style("spDropdownButton")(
    backgroundColor.white,
    color(spOrange),
    textAlign.center,
    textDecoration := "none",
    fontSize(16.px),
    //transitionDuration :=! "400ms",
    transitionDuration.inherit,
    cursor.pointer,
    position.relative,
    display.inlineBlock,
    padding(5.px, 10.px),
    margin(5.px, 10.px, 5.px, 0.px),
    whiteSpace.nowrap,
    borderRadius(4.px),
    border.none,

    unsafeChild("ul")(
      visibility.hidden,
      display.none,
      right :=! "0",
      opacity :=! "0",
      marginTop(6.px),
      position.absolute,
      /*
            unsafeChild("spButtonStyle")(
              clear.both,
              width(100.%%)
            ),*/
      &.hover(
        visibility.visible,
        opacity :=! "1",
        display.block
      )
    ),
    &.hover( // on hover
      backgroundColor(spOrange),
      boxShadow := "0 4px 8px 0 rgba(0, 0, 0, 0.3), 0 6px 20px 0 rgba(0, 0, 0, 0.28)",
      color.white,
      unsafeChild("ul")(
        visibility.visible,
        opacity :=! "1",
        display.block
      )
    ),
    &.focus( // while focused, removes blue border
      outline :=! "0"
    )
  )

  val textBeforeIcon = style(
    paddingRight(6.px),
  )

  val icon = style(
    transitionDuration.inherit,
    color.inherit,
    backgroundColor.inherit
  )

  val spDropdownItem = style("dd-item")(
    backgroundColor.white,
    color(spOrange),
    border :=! s"2px solid ${spOrange.value}",
    textAlign.center,
    textDecoration := "none",
    fontSize(14.px),
    //transitionDuration :=! "400ms",
    transitionDuration :=! inherit,
    cursor.pointer,
    width :=! "100%",
    padding(5.px, 10.px),
    borderRadius(4.px),
    whiteSpace.nowrap,

    &.hover( // on hover
      backgroundColor(spOrange),
      boxShadow := "0 4px 8px 0 rgba(0, 0, 0, 0.3), 0 6px 20px 0 rgba(0, 0, 0, 0.28)",
      color.white
    ),
    &.focus( // while focused, removes blue border
      outline :=! "0"
    ),
    &.active( // while mouse held down
      backgroundColor(spOrangeDark),
      border :=! s"2px solid ${spOrangeDark.value}",
    )
  )
  /*val customSPButtonCSSInSPDropdown = style(
    unsafeRoot(".spDropdownButton ul li .spButton")(
      backgroundColor.gray
    )
  )*/

  val unsortedList = style("custom_ul")(
    listStyle := "none",
    paddingTop(0.px),
    paddingBottom(0.px),
    paddingRight(6.px),
    paddingLeft(6.px),
    backgroundColor :=! "#d15b0c50",
    borderRadius(2.px)
  )

  val listItem = style("custom_li")(
    /*display.flex,
    justifyContent.flexEnd,*/
    marginBottom(1.px),
    width :=! "100%"

  )

  this.addToDocument()
}

