package untyped.lambda.ext

import untyped.lambda.{TermAbs, TermVarRef}

object BooleanExt {
  val TermTrue = TermAbs("t", TermAbs("f", TermVarRef("t")))
  val TermFalse = TermAbs("t", TermAbs("f", TermVarRef("f")))
}
