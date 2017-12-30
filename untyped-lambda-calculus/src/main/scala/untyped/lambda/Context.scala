package untyped.lambda

import scala.collection.immutable
import scala.util.control.Breaks.{breakable, break}
import Context._

class Context(val ctx: immutable.List[(String, Binding)]) extends {
  def length = ctx.size
  def add(name: String, bind: Binding) = Context((name -> bind) +: ctx)
  // get de bruijn index
  def indexOf(name: String): Int = {
    var index = 0
    val iterator = ctx.iterator
    breakable {
      while (iterator.hasNext) {
        if (iterator.next._1 == name)
          break()
        index += 1
      }
    }
    index
  }

  def isBound(name: String): Boolean = ctx.contains(name)
}

object Context {
  trait Binding
  object NameBind extends Binding

  def apply(ctx: immutable.List[(String, Binding)]) = new Context(ctx)
  val EmptyContext = Context(immutable.List.empty)
}
