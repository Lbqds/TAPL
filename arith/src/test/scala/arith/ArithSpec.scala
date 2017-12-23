package arith

import arith.Arith._
import org.scalatest.WordSpec

// Test just for well-formed
class ArithSpec extends WordSpec {
  "if t1 then t2 else t3" when {
    "t1 = true, t2 = Succ(Succ(0)), t3 = Succ(Succ(0))" should {
      "be Succ(Succ(0))" in {
        assert(eval(TermIf(TermTrue, TermSucc(TermZero), TermSucc(TermSucc(TermSucc(TermZero))))) == TermSucc(TermZero))
      }
    }
  }

  "if t1 then t2 else t3" when {
    "t1 = false, t2 = Succ(Succ(0)), t3 = Succ(Succ(Succ(0)))" should {
      "be Succ(Succ(Succ(0)))" in {
        assert(eval(TermIf(TermFalse, TermSucc(TermSucc(TermZero)), TermSucc(TermSucc(TermSucc(TermZero))))) == TermSucc(TermSucc(TermSucc(TermZero))))
      }
    }
  }

  "if t1 then t2 else t3" when {
    "t1 = if TermIsZero(TermZero) then TermTrue else TermFalse, t2 = Succ(Succ(0)), t3 = TermFalse" should {
      "be Succ(Succ(0))" in {
        assert(eval(
          TermIf(
            TermIf(TermIsZero(TermZero), TermTrue, TermFalse),
            TermSucc(TermSucc(TermZero)),
            TermFalse
          )) == TermSucc(TermSucc(TermZero)))
      }
    }
  }

  "Succ of t1" when {
    "t1 = Succ(Succ(0))" should {
      "be Succ(Succ(Succ(0)))" in {
        assert(eval(TermSucc(TermSucc(TermSucc(TermZero)))) == TermSucc(TermSucc(TermSucc(TermZero))))
      }
    }
  }

  "Pred of t1" when {
    "t1 = Succ(Succ(Succ(0)))" should {
      "be Succ(Succ(0))" in {
        assert(eval(TermPred(TermSucc(TermSucc(TermSucc(TermZero))))) == TermSucc(TermSucc(TermZero)))
      }
    }
  }

  "TermIsZero apply to t1" when {
    "t1 = Pred(Pred(Succ(Succ(0)))" should {
      "be TermTrue" in {
        assert(eval(TermIsZero(TermPred(TermPred(TermSucc(TermSucc(TermZero)))))) == TermTrue)
      }
    }
  }

  "TermIsZero apply to t1" when {
    "t1 = Pred(Succ(Succ(0))" should {
      "be TermFalse" in {
        assert(eval(TermIsZero(TermPred(TermSucc(TermSucc(TermZero))))) == TermFalse)
      }
    }
  }
}