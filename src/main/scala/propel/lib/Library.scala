package propel.lib

import org.kiama.==>
import propel.core._
import propel.core.ExprTree._
import propel.core.TypeTree._
import propel.core.TypeContext

abstract class Library {

    def namespace: String

    def functions: Seq[NamedFunction]
    
    def typeInfo (ctx : TypeContext) : Id ==> TypeInfo = Function.unlift {qsym =>
        functions.find(_.qsym == qsym).map(_.typeInfo(ctx))
    }
    
    protected def contract(sym: String): Contract = NamedContract (s"${namespace}:${sym}")
    
    abstract class NamedFunction (val sym: String) {
        
        val qsym: Id = s"${namespace}:${sym}"
        
        def typeInfo (ctx : TypeContext) : TypeInfo
        
    }
    
    abstract class NullaryFunction (symbol: String) extends NamedFunction (symbol) {
        
        def apply () : Expr = {
            Lookup (qsym)
        }
        
        def unapply (expr : Expr) : Boolean = expr match {
            case Lookup (`qsym`) => true
            case _                          => false
        }
        
    }
    
    abstract class UnaryFunction (symbol: String) extends NamedFunction (symbol) {
        
        def apply (arg : Expr) : Expr = {
            Apply (Lookup (qsym), arg)
        }
        
        def unapply (expr : Expr) : Option[Expr] = expr match {
            case Apply (Lookup (`qsym`), arg)    => Some(arg)
            case _                                          => None
        }
        
    }
    
    abstract class BinaryFunction (symbol: String) extends NamedFunction (symbol) {
        
        def apply (arg1 : Expr, arg2 : Expr) : Expr = {
            Apply (Apply (Lookup (qsym), arg1), arg2)
        }
        
        def unapply (expr : Expr) : Option[(Expr, Expr)] = expr match {
            case Apply (Apply (Lookup (`qsym`), arg1), arg2) => Some((arg1, arg2))
            case _                                                      => None
        }
        
    }
    
    abstract class MultiaryFunction (symbol: String) extends NamedFunction (symbol) {
    
        def apply (exprs : Expr*) : Expr = {
            def applyRest (expr : Expr, rest : Seq[Expr]) : Expr = rest match {
                case Seq() => expr
                case seq => applyRest (Apply (expr, seq.head), seq.tail)
            }
            applyRest (Lookup (qsym), exprs)
        }
        
        def unapplySeq (expr : Expr) : Option[Seq[Expr]] = expr match {
            case Lookup (`qsym`) => Some(Seq())
            case Apply (fun, lastArg) => 
                unapplySeq (fun) match {
                    case Some (exprs)       => Some(exprs ++ Seq(lastArg))
                    case None               => None
                }
            case _                          => None
        }
        
    }
    
}