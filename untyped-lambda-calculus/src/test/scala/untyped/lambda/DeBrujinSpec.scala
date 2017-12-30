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
        assert(DeBruijn.deBruign(t, Context.EmptyContext) == TermAbs("s", TermAbs("z", TermApp(TermVar(1, 2), TermVar(0, 2)))))
      }
    }
  }

  // plus = λm. λn. λs. λz. m s (n z s)
  "de brujin TermAbs(name, expression)" when {
    "name = m, expression = TermAbs(n, TermAbs(s, TermAbs(z, TermApp(TermApp(TermVarRef(m), TermVarRef(s)), TermApp(TermApp(TermVarRef(n), TermVarRef(z)), TermVarRef(s)))))))" should {
      "be TermAbs(m, TermAbs(n, TermAbs(s, TermAbs(z, TermApp(TermApp(TermVar(3, 4), TermVar(1, 4)), TermApp(TermApp(TermVar(2, 4), TermVar(0, 4)), TermVarRef(1, 4))))))" in {
        val expression = TermAbs("n", TermAbs("s", TermAbs("z", TermApp(TermApp(TermVarRef("m"), TermVarRef("s")), TermApp(TermApp(TermVarRef("n"), TermVarRef("z")), TermVarRef("s"))))))
        val plus = TermAbs("m", expression)
        val deBruginTerm = DeBruijn.deBruign(plus, Context.EmptyContext)
        assert(deBruginTerm == TermAbs("m", TermAbs("n", TermAbs("s", TermAbs("z", TermApp(TermApp(TermVar(3, 4), TermVar(1, 4)), TermApp(TermApp(TermVar(2, 4), TermVar(0, 4)), TermVar(1, 4))))))))
      }
    }
  }

  // fix = λf. (λx. f (λy. (x x) y)) (λx. f (λy. (x x) y))
  "de brujin TermAbs(name, expression)" when {
    "name = f, expression = TermApp(g, g) where g = TermAbs(x, TermApp(f, TermAbs(y, TermApp(TermApp(x, x), y))))" should {
      "be TermAbs(f, TermApp(g, g)) where y = TermAbs(x, TermApp(TermVar(1, 2), TermAbs(y, TermApp(TermApp(TermVar(1, 3), TermVar(1, 3)), TermVar(0, 3)))))" in {
        val g = TermAbs("x", TermApp(TermVarRef("f"), TermAbs("y", TermApp(TermApp(TermVarRef("x"), TermVarRef("x")), TermVarRef("y")))))
        val fix = TermAbs("f", TermApp(g, g))
        val deBruijnTerm = DeBruijn.deBruign(fix, Context.EmptyContext)
        val deBruijnG = TermAbs("x", TermApp(TermVar(1, 2), TermAbs("y", TermApp(TermApp(TermVar(1, 3), TermVar(1, 3)), TermVar(0, 3)))))
        assert(deBruijnTerm == TermAbs("f", TermApp(deBruijnG, deBruijnG)))
      }
    }
  }
}
