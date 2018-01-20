package untyped.lambda.ext

import org.scalatest.WordSpec

class BooleanExtSpec extends WordSpec {
  "not a" when {
    "a = true" should {
      "be false" in {
        assert(BooleanExt.not(BooleanExt.TermTrue).equalWith(BooleanExt.TermFalse))
      }
    }
  }

  "a and b" when {
    "a = true, b = false" should {
      "be false" in {
        assert(BooleanExt.and(BooleanExt.TermTrue, BooleanExt.TermFalse).equalWith(BooleanExt.TermFalse))
      }
    }
  }

  "a or b" when {
    "a = true, b = false" should {
      "be true" in {
        assert(BooleanExt.or(BooleanExt.TermTrue, BooleanExt.TermFalse).equalWith(BooleanExt.TermTrue))
      }
    }
  }

  "if p a else b" when {
    "p = true, a = true, b = false" should {
      "be true" in {
        assert(BooleanExt.ifElse(BooleanExt.TermTrue, BooleanExt.TermTrue, BooleanExt.TermFalse).equalWith(BooleanExt.TermTrue))
      }
    }
  }
}
