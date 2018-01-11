package untyped.lambda

object DeBruijn {
  def deBruijn(term: Term, ctx: Context): Term = {
    import Context._
    term match {
      case TermVarRef(name) =>
        // we assume all variable are bounded
        val index = ctx.indexOf(name)
        TermVar(index, ctx.length)
      case v @ TermVar(_, _) => v
      case TermAbs(name, expression) =>
        TermAbs(name, deBruijn(expression, ctx.add(name, NameBind)))
      case TermApp(t1, t2) =>
        TermApp(deBruijn(t1, ctx), deBruijn(t2, ctx))
      case TermZero => TermZero
      case TermSucc(t) => TermSucc(deBruijn(t, ctx))
    }
  }
}
