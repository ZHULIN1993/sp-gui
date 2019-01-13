package spgui.components

import scalacss.DevDefaults._
import scalacss.ScalaCssReact._

object SPTextBoxCSS extends StyleSheet.Inline {
  import dsl._

  private val spOrange = c"#df691a"
  private val spOrangeDark = c"#d15b0c"

  val inputForm = style("input-form")(
    border :=! "2px solid #000",
    borderRadius(4.px)
  )

  this.addToDocument()
}


