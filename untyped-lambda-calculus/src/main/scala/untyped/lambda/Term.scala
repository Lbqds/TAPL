package untyped.lambda

sealed trait Term
case class TermVar(index: Int, ctxLength: Int) extends Term
case class TermAbs(arg: String, expression: Term) extends Term
case class TermApp(t1: Term, t2: Term) extends Term
// will be convert to TermVar in de bruijn phase
case class TermVarRef(name: String) extends Term
