package arith

// untyped arith expression, ill-formed is accepted, it can't distinguish that
object Arith {
  sealed trait Term
  object TermTrue extends Term
  object TermFalse extends Term
  object TermZero extends Term
  case class TermIf(t1: Term, t2: Term, t3: Term) extends Term
  case class TermSucc(t: Term) extends Term
  case class TermPred(t: Term) extends Term
  case class TermIsZero(t: Term) extends Term

  object PredZero extends RuntimeException("Pred can't apply to zero")
  object NoRuleApplied extends RuntimeException("No rule applied")

  def isNumericVal(t: Term): Boolean = {
    t match {
      case TermZero => true
      case TermSucc(_) => true
      case _ => false
    }
  }

  def isVal(t: Term): Boolean = {
    t match {
      case TermFalse|TermTrue => true
      case t1 if isNumericVal(t1) => true
      case _ => false
    }
  }

  // Single-step evaluation, if result is normal form, throw NoRuleApplied
  def eval1(t: Term): Term = {
    t match {
      case TermIf(TermTrue, t2, t3) => t2
      case TermIf(TermFalse, t2, t3) => t3
      case TermIf(t1, t2, t3) => TermIf(eval1(t1), t2, t3)
      case TermSucc(t1) => TermSucc(eval1(t1))
      case TermPred(TermZero) => throw PredZero
      case TermPred(TermSucc(nvl)) if isNumericVal(nvl) => nvl
      case TermPred(t1) => TermPred(eval1(t1))
      case TermIsZero(TermZero) => TermTrue
      case TermIsZero(TermSucc(nvl)) if isNumericVal(nvl) => TermFalse
      case TermIsZero(t1) => TermIsZero(eval1(t1))
      case _ => throw NoRuleApplied
    }
  }

  def eval(t: Term): Term = {
    try {
      eval1(t) match {
        case nextStep => eval(nextStep)
      }
    } catch {
      case NoRuleApplied => t
    }
  }
}
