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
    "n = zero" should {
      "be first" in {
        val first = TermSucc(TermZero)
        val result = NumericExt.succ(NumericExt.Zero)
        assert(NumericExt.toSuccExpr(result).equalWith(first))
      }
    }
  }
}
