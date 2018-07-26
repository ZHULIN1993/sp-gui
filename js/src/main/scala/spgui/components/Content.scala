package spgui.components

import java.util.UUID

/** Top-Level trait to add to Layout
  * Every component can extend Content to add
  * application-flexibility
  */
case class Content(name: String, children: Option[Content] = None, parent: Option[Content] = None)
