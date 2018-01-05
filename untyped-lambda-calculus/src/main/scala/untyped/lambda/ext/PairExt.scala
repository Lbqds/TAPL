package untyped.lambda.ext

import untyped.lambda.{Term, TermAbs, TermApp, TermVarRef}
import untyped.lambda.Evaluation.eval
import untyped.lambda.Context
import untyped.lambda.DeBruijn

object PairExt {
  def pair(first: Term, second: Term): Term = {
    applyTo(TermApp(_pair, first), second)
  }

  def first(pair: Term): Term = {
    applyTo(pair, BooleanExt.TermTrue)
  }

  def second(pair: Term): Term = {
    applyTo(pair, BooleanExt.TermFalse)
  }

  private def applyTo(pair: Term, f: Term) = {
    val t = TermApp(pair, f)
    val deBruignTerm = DeBruijn.deBruijn(t, Context.EmptyContext)
    eval(Context.EmptyContext, deBruignTerm)
  }

  private val _pair = TermAbs("f", TermAbs("s", TermAbs("b", TermApp(TermApp(TermVarRef("b"), TermVarRef("f")), TermVarRef("s")))))
}
