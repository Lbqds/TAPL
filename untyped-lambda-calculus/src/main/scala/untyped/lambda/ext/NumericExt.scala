package untyped.lambda.ext

import untyped.lambda._

object NumericExt {
  val Zero = TermAbs("s", TermAbs("z", TermVarRef("z")))
  private val _succ = TermAbs("n", TermAbs("s", TermAbs("z",
    TermApp(TermVarRef("s"), TermApp(TermApp(TermVarRef("n"), TermVarRef("s")), TermVarRef("z"))))))

  private val _plus = TermAbs("m", TermAbs("n", TermAbs("s",
    TermAbs("z", TermApp(TermApp(TermVarRef("m"), TermVarRef("s")),
      TermApp(TermApp(TermVarRef("n"), TermVarRef("s")), TermVarRef("z"))))
  )))

  private val _time = TermAbs("m", TermAbs("n", TermAbs("s",
    TermAbs("z", TermApp(TermApp(TermVarRef("m"), TermApp(TermVarRef("n"), TermVarRef("s"))),
      TermVarRef("z")))
  )))

  def time(n: Term, m: Term): Term = {
    val t = TermApp(TermApp(_plus, m), n)
    val deBruijnTerm = DeBruijn.deBruijn(t, Context.EmptyContext)
    Evaluation.eval(Context.EmptyContext, deBruijnTerm)
  }

  def plus(n: Term, m: Term): Term = {
    val t = TermApp(TermApp(_plus, m), n)
    val deBruijnTerm = DeBruijn.deBruijn(t, Context.EmptyContext)
    Evaluation.eval(Context.EmptyContext, deBruijnTerm)
  }

  def succ(n: Term): Term = {
    val t = TermApp(_succ, n)
    val deBruijnTerm = DeBruijn.deBruijn(t, Context.EmptyContext)
    Evaluation.eval(Context.EmptyContext, deBruijnTerm)
  }

  def toSuccExpr(n: Term): Term = {
    val succ = TermAbs("z", TermSucc(TermVarRef("z")))
    val term = TermApp(TermApp(n, succ), TermZero)
    val deBruijnTerm = DeBruijn.deBruijn(term, Context.EmptyContext)
    Evaluation.eval(Context.EmptyContext, deBruijnTerm)
  }
}
