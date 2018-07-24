# Sequence Planner 2
Sequence Planner (SP) is a micro service architecture for modelling and analyzing automation systems. Initially, the focus was on supporting engineers in developing control code for programmable logical controllers (PLCs). 

During the first years, algorithms to handle product and automation system interaction, and to visualize complex operation sequences using multiple projections, was developed. 

Over the years, other use cases have been integrated, like formal verification and synthesis using Supremica, restart support, cycle time optimization, energy optimization and hybrid systems, online monitoring and control (the tweeting factory), as well as emergency department online planning.

## SP-GUI
This is a sub-project of SP with origin at https://github.com/kristoferB/SP

Now we have split up the SP source code and published each sub-project to Sonatype. To each new SP-project, let the project add our libraries needed as dependencies. GUI is using sp-domain and sp-comm.

Here we have widgets that can be widely used.

## Wiki
Watch our wiki for information about Sequence Planner and how to use.

# Guide #

## Prerequisites ##
You will need an sbt installation  (`scala-sbt.org/0.13/docs/`) 
You will also need an installation of node (`nodejs.org`) 

## Compiling the SP frontend ##
To install javascript dependencies, we now use [scalajs-bundler](https://github.com/scalacenter/scalajs-bundler/).

To compile the scalaJS code, run `sbt fastOptJS`. To compile the optimized version run `sbt fullOptJS` (slow process, not recommended in development).

## Running SP frontend only ##
For development purposes, the frontend can be run without backend and with live reloading using lihaoyi's workbench. To do this, run `sbt ~fastOptJS` and open `http://localhost:12345/js/target/scala-2.12/classes/index.html` in a browser.

## Making a widget ##
The simplest possible widget is created with `SPWidget(spwb => <.h1("Hello, World!"))`. So to get started making a widget, create a file in `widgets/` containing the following.
```scala
package spgui.widgets

import spgui.SPWidget

object MyWidget {
  def apply() = SPWidget(spwb => <.h1("Hello from MyWidget"))
}
```
(Defining `apply` is just the scala way of making your object callable with `MyWidget()`.) To make your new widget available in the SP-menu, open `WidgetList.scala` and add it like so: `("My new widget", spgui.MyWidget())`, next to the other widgets.

The argument to the function given as argument to `SPWidget`, above named `spwb`, provides the API to interact with SP. For example it contains access to a string of data stored in the browser storage, via the field `data: String` and the method `saveData(data: String)`. This is conveniently used together with a case class, `upickle` and `Try`, as in the example below (found in the code in `widgets/examples/`).
```scala
object WidgetWithData {
  // calling MyData() (with no arguments) will give MyData(someInt = -17)
  case class MyData(someInt: Int = -17)

  def apply() = SPWidget{spwb =>
    // upickle's read tries to turn the string in browser storage into a MyData-instance
    // if there is nothing there or casting fails, creates the standard instance instead
    val myData = Try(read[MyData](spwb.data)).getOrElse(MyData())
    val theInt = myData.someInt

    // upickle's write turns a new version of myData into a string that is saved in storage
    def increment = Callback(spwb.saveData(write(myData.copy(theInt + 1))))

    <.div(
      <.h3("count is " + theInt),
      <.button("increment", ^.onClick --> increment),
      <.p("this piece of data is stored in the browser")
    )
  }
}
```
Note that no explicit re-rendering is necessary after calling `saveData`. This is handled automatically.

The case class can contain anything data-ish, i.e. strings, doubles, ints, lists of them as well as nested data-ish case classes.

README-TODO: add more about what's inside spwb here.

The html-like scala-objects prefixed by `<` and `^` are provided by the scalajs-react library. The function given as argument to `SPWidget` need to return either an `<`-object or a scalajs-react component. Learn about scalajs-react [here] (https://github.com/japgolly/scalajs-react/blob/master/doc/USAGE.md).


## JavaScript dependencies ##
JS dependencies are handled by scalajs-bundler and made available through a bundle file generated with webpack. To add a JS dependency, go to `project/SPGuiSettings.scala`, add your version to the Version object as a String, then write your
