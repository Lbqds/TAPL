package untyped.lambda

object DeBruijn {
  def deBruign(term: Term, ctx: Context): Term = {
    import Context._
    term match {
      case TermVarRef(name) =>
        // we assume all variable are bounded
        val index = ctx.indexOf(name)
        TermVar(index, ctx.length)
      case v @ TermVar(_, _) => v
      case TermAbs(name, expression) =>
        TermAbs(name, deBruign(expression, ctx.add(name, NameBind)))
      case TermApp(t1, t2) =>
        TermApp(deBruign(t1, ctx), deBruign(t2, ctx))
    }
  }
}
