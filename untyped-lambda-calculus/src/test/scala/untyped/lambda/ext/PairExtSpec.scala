package untyped.lambda.ext

import org.scalatest.WordSpec
import untyped.lambda.Constant

class PairExtSpec extends WordSpec {
  "p = Pair(t1, t2)" when {
    "t1 = true, t2 = false" should {
      "be first(p) = true, second(p) = false" in {
        val p = PairExt.pair(BooleanExt.TermTrue, BooleanExt.TermFalse)
        val first = PairExt.first(p)
        val second = PairExt.second(p)
        assert(first.equalWith(BooleanExt.TermTrue))
        assert(second.equalWith(BooleanExt.TermFalse))
      }
    }
  }

  "p = Pair(t1, t2)" when {
    "t1 = false, t2 = true" should {
      "be first(p) = false, second(p) = true" in {
        val p = PairExt.pair(BooleanExt.TermFalse, BooleanExt.TermTrue)
        val first = PairExt.first(p)
        val second = PairExt.second(p)
        assert(first.equalWith(BooleanExt.TermFalse))
        assert(second.equalWith(BooleanExt.TermTrue))
      }
    }
  }

  "p = Pair(t1, t2)" when {
    "t1 = id, t2 = id" should {
      "be first(p) = id, second(p) = id" in {
        val p = PairExt.pair(Constant.id, Constant.id)
        val first = PairExt.first(p)
        val second = PairExt.second(p)
        assert(first.equalWith(Constant.id))
        assert(second.equalWith(Constant.id))
      }
    }
  }
}
