package spgui.theming
/**
  * Created by alexa on 06/08/2018.
  */

import scalacss.DevDefaults._

object SPCSS extends StyleSheet.Inline {
  import dsl._

  val layout = style(
    font := "sans-serif",
    margin(0 px),
    color.rgb(0,0,0),
    backgroundColor.rgb(255, 255, 255)
  )

}

