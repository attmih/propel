package propel.lib

import propel.core.ExprTree._

abstract class Library {

    def namespace: String
    
    abstract class NamedFunction (val symbol: String) {
        
        val qualifiedSymbol: Id = s"${namespace}:${symbol}"
        
    }
    
    abstract class NullaryFunction (symbol: String) extends NamedFunction (symbol) {
        
        def apply () : Expr = {
            Lookup (qualifiedSymbol)
        }
        
        def unapply (expr : Expr) : Boolean = expr match {
            case Lookup (`qualifiedSymbol`) => true
            case _                          => false
        }
        
    }
    
    abstract class UnaryFunction (symbol: String) extends NamedFunction (symbol) {
        
        def apply (arg : Expr) : Expr = {
            Apply (Lookup (qualifiedSymbol), arg)
        }
        
        def unapply (expr : Expr) : Option[Expr] = expr match {
            case Apply (Lookup (`qualifiedSymbol`), arg)    => Some(arg)
            case _                                          => None
        }
        
    }
    
    abstract class BinaryFunction (symbol: String) extends NamedFunction (symbol) {
        
        def apply (arg1 : Expr, arg2 : Expr) : Expr = {
            Apply (Apply (Lookup (qualifiedSymbol), arg1), arg2)
        }
        
        def unapply (expr : Expr) : Option[(Expr, Expr)] = expr match {
            case Apply (Apply (Lookup (`qualifiedSymbol`), arg1), arg2) => Some((arg1, arg2))
            case _                                                      => None
        }
        
    }
    
    abstract class MultiaryFunction (symbol: String) extends NamedFunction (symbol) {
    
        def apply (exprs : Expr*) : Expr = {
            def applyRest (expr : Expr, rest : Seq[Expr]) : Expr = rest match {
                case Seq() => expr
                case seq => applyRest (Apply (expr, seq.head), seq.tail)
            }
            applyRest (Lookup (qualifiedSymbol), exprs)
        }
        
        def unapplySeq (expr : Expr) : Option[Seq[Expr]] = expr match {
            case Lookup (`qualifiedSymbol`) => Some(Seq())
            case Apply (fun, lastArg) => 
                unapplySeq (fun) match {
                    case Some (exprs)       => Some(exprs ++ Seq(lastArg))
                    case None               => None
                }
            case _                          => None
        }
        
    }
    
}