package untyped.lambda.ext

import org.scalatest.WordSpec
import untyped.lambda._

/// We use call-by-value strategy, in which only outermost redex are reduced
/// and where a redex is reduced only when its right-hand side has already been
/// reduced to a value. So the inner \n. \s. \z -> n s z will not computed, we
/// need pass extra arguments to trigger this. Therefore, we add TermSucc and
/// TermZero.
class NumericExtSpec extends WordSpec {
  "succ n" when {
    "n = 0" should {
      "be 1" in {
        val first = TermSucc(TermZero)
        val result = NumericExt.succ(NumericExt.Zero)
        assert(NumericExt.toSuccExpr(result).equalWith(first))
      }
    }
  }

  "plus m n" when {
    "m = 1, n = 2" should {
      "be 3" in {
        val m = NumericExt.succ(NumericExt.Zero)
        val n = NumericExt.succ(m)
        val sum = NumericExt.plus(m, n)
        val three = TermSucc(TermSucc(TermSucc(TermZero)))
        assert(NumericExt.toSuccExpr(sum).equalWith(three))
      }
    }
  }

  "time m n" when {
    "m = 2, n = 2" should {
      "be 4" in {
        val one = NumericExt.succ(NumericExt.Zero)
        val m = NumericExt.succ(one)
        val four = TermSucc(TermSucc(TermSucc(TermSucc(TermZero))))
        val product = NumericExt.time(m, m)
        assert(NumericExt.toSuccExpr(product).equalWith(four))
      }
    }
  }
}
