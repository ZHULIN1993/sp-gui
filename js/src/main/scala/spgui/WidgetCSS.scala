package spgui

import scalacss.DevDefaults._

/** CSS-Style for the Widget-Headers and fonts */
object WidgetCSS extends StyleSheet.Inline {
  import dsl._

  // generates @font-face for alteHaas
  val alteHaas = fontFace("alte_haas_groteskregular")(
    _.src("url(fonts/alteHaas/altehaasgroteskregular-webfont.woff2), url(fonts/alteHaas/altehaasgroteskregular-webfont.woff), url(fonts/alteHaas/AlteHaasGroteskRegular.ttf)")
  )

  // generates @font-face for freeSans
  val freeSansFF = fontFace("freeSans")(
    _.src("url(fonts/freeSans/FreeSans.woff), url(fonts/freeSans/freesans.woff2), url(fonts/freeSans/freesans.ttf)")
  )

  // generates @font-face for freeSansBold
  val freeSansBoldFF = fontFace("freeSansBold")(
    _.src("url(fonts/freeSans/FreeSansBold.woff), url(fonts/freeSans/freesansbold.woff2), url(fonts/freeSans/freesansbold.ttf)")
    .fontWeight.bold
  )

  // generates @font-face for freeSansOblique
  val freeSansObliqueFF = fontFace("freeSansOblique")(
    _.src("url(fonts/freeSans/FreeSansOblique.woff), url(fonts/freeSans/freesansoblique.woff2), url(fonts/freeSans/freesansoblique.ttf)")
    .fontStyle.italic
    .fontStyle.oblique
  )

  val hideScrollBar = style(
    overflow.hidden
  )

  val clock = style(
    hideScrollBar,
    textAlign.center,
    fontSize(60.px),
    fontFamily(freeSansBoldFF)
  )

  val widgetHeader = style(
    fontSize(23.px),
    paddingBottom(8.px),
    fontFamily(freeSansBoldFF)
  )

  val widgetSubHeader = style(
    fontSize(23.px),
    paddingBottom(8.px),
    fontFamily(freeSansFF)
  )

  val widgetText = style(
    hideScrollBar,
    fontSize(22.px),
    paddingBottom(8.px),
    fontFamily(freeSansFF)
  )

  val widgetTextBold = style(
    hideScrollBar,
    fontSize(22.px),
    paddingBottom(8.px),
    fontFamily(freeSansBoldFF)
  )

  val freeSans = style(
    hideScrollBar,
    fontFamily(freeSansFF)
  )

  val freeSansBold = style(
    hideScrollBar,
    fontFamily(freeSansBoldFF)
  )

  val helveticaZ = style(
    hideScrollBar,
    padding(2.px),
    fontFamily(freeSansFF) //:=! "Helvetica Neue, Helvetica, Liberation Sans, Arial, sans-serif"
  )

  this.addToDocument()
}
