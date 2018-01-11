package untyped.lambda

object Evaluation {
  object TermVarRefInEvalPhase extends RuntimeException
  object NoRuleApplied extends Exception

  def isVal(_ctx: Context, term: Term): Boolean = {
    term match {
      case TermAbs(_, _) => true
      case TermZero => true
      case TermSucc(t) => isVal(_ctx, t)
      case _ => false
    }
  }

  def termShift(d: Int, t: Term): Term = {
    def walk(c: Int, t: Term): Term = {
      t match {
        case TermVar(index, ctxLength) =>
          if (index >= c) TermVar(index + d, ctxLength + d)
          else TermVar(index, ctxLength + d)
        case TermAbs(name, expression) =>
          TermAbs(name, walk(c + 1, expression))
        case TermApp(t1, t2) =>
          TermApp(walk(c, t1), walk(c, t2))
        case TermZero => TermZero
        case TermSucc(t1) =>
          TermSucc(walk(c, t1))
        case TermVarRef(_) =>
          // TermVarRef be convert to TermVar in de brujin phase
          throw TermVarRefInEvalPhase
      }
    }
    walk(0, t)
  }

  // [j -> s]t
  def termSubst(j: Int, s: Term, t: Term): Term = {
    def walk(c: Int, t: Term): Term = {
      t match {
        case v @ TermVar(index, ctxLength) =>
          if (j + c == index) termShift(c, s)
          else v
        case TermAbs(name, expression) =>
          TermAbs(name, walk(c + 1, expression))
        case TermApp(t1, t2) =>
          TermApp(walk(c, t1), walk(c, t2))
        case TermZero => TermZero
        case TermSucc(t1) =>
          TermSucc(walk(c, t1))
        case TermVarRef(_) =>
          // TermVarRef be convert to TermVar in de brujin phase
          throw TermVarRefInEvalPhase
      }
    }
    walk(0, t)
  }

  def termSubstTop(s: Term, t: Term): Term = {
    termShift(-1, termSubst(0, termShift(1, s), t))
  }

  def eval1(ctx: Context, t: Term): Term = {
    t match {
      case TermApp(TermAbs(_name, t12), v2) if isVal(ctx, v2) =>
        termSubstTop(v2, t12)
      case TermApp(v1, t2) if isVal(ctx, v1) =>
        TermApp(v1, eval1(ctx, t2))
      case TermApp(t1, t2) =>
        TermApp(eval1(ctx, t1), t2)
      case TermSucc(t1) if isVal(ctx, t1) =>
        throw NoRuleApplied
      case TermSucc(t1) =>
        TermSucc(eval1(ctx, t1))
      case TermZero => TermZero
      case _ =>
        throw NoRuleApplied
    }
  }

  def eval(ctx: Context, t: Term): Term = {
    try {
      eval(ctx, eval1(ctx, t))
    } catch {
      case NoRuleApplied => t
    }
  }
}