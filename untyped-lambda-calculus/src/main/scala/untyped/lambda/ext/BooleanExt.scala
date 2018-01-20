package untyped.lambda.ext

import untyped.lambda._

object BooleanExt {
  val TermTrue = TermAbs("t", TermAbs("f", TermVarRef("t")))
  val TermFalse = TermAbs("t", TermAbs("f", TermVarRef("f")))
  private val _not = TermAbs("a", TermApp(TermApp(TermVarRef("a"), TermFalse), TermTrue))
  private val _and = TermAbs("a", TermAbs("b", TermApp(TermApp(TermVarRef("a"), TermVarRef("b")), TermFalse)))
  private val _or = TermAbs("a", TermAbs("b", TermApp(TermApp(TermVarRef("a"), TermTrue), TermVarRef("b"))))
  private val _ifElse = TermAbs("p", TermAbs("a", TermAbs("b", TermApp(TermApp(TermVarRef("p"), TermVarRef("a")), TermVarRef("b")))))

  def not(t: Term) = applyTo(_not, t)
  def and(t1: Term, t2: Term) = applyTo(TermApp(_and, t1), t2)
  def or(t1: Term, t2: Term) = applyTo(TermApp(_or, t1), t2)
  def ifElse(p: Term, t1: Term, t2: Term) = applyTo(TermApp(TermApp(_ifElse, p), t1), t2)

  private def applyTo(t1: Term, t2: Term): Term = {
    val t = TermApp(t1, t2)
    val deBruijnTerm = DeBruijn.deBruijn(t, Context.EmptyContext)
    Evaluation.eval(Context.EmptyContext, deBruijnTerm)
  }
}
