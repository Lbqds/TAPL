package untyped.lambda

import org.scalatest.WordSpec

class DeBrujinSpec extends WordSpec {
  "de brujin TermAbs(name, expression)" when {
    "name = x, expression = TermVarRef(x)" should {
      "be TermAbs(name, TermVar(0, 1))" in {
        val t = TermAbs("x", TermVarRef("x"))
        assert(DeBruijn.deBruign(t, Context.EmptyContext) == TermAbs("x", TermVar(0, 1)))
      }
    }
  }

  "de brujin TermAbs(name, expression)" when {
    "name = s, expression = TermAbs(z, TermApp(TermVarRef(s), TermVarRef(z)))" should {
      "be TermAbs(s, TermAbs(z, TermApp(TermVar(1, 1), TermVar(0, 2)))" in {
        val t = TermAbs("s", TermAbs("z", TermApp(TermVarRef("s"), TermVarRef("z"))))
        assert(DeBruijn.deBruign(t, Context.EmptyContext) == TermAbs("s", TermAbs("z", TermApp(TermVar(0, 2), TermVar(1, 2)))))
      }
    }
  }
}
