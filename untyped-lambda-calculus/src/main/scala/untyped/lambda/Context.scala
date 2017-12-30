package untyped.lambda

import scala.collection.immutable
import scala.util.control.Breaks.{breakable, break}
import Context._

class Context(val ctx: immutable.ListMap[String, Binding]) extends {
  def length = ctx.size
  def add(name: String, bind: Binding) = Context(ctx + (name -> bind))
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

  def apply(ctx: immutable.ListMap[String, Binding]) = new Context(ctx)
  val EmptyContext = Context(immutable.ListMap.empty)
}
