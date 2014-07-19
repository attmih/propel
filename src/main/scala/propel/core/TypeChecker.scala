package propel.core

import ExprTree._
import ExprAttributes._
import TypeTree._
import propel.lib.BooleanLib

object TypeChecker {
    
    def checkType (expr : Expr, ctx : TypeContext) : TypeInfo = expr match {
        case Lookup (qsym) => ctx.findType (qsym)
        case Apply (fun, arg) => checkType (fun, ctx) match {
            case LambdaType (typeLam) => 
                val argType = checkType (arg, ctx)
                if (typeLam isDefinedAt argType) typeLam apply argType
                else sys.error (s"${argType} cannot be applied to: ${fun}")
            case other => 
                sys.error (s"Expected lambda type but got: ${other}")
        }
        case e @ Lambda (fun) => LambdaType ({
            case varType => {
                val variable = "l" + (e->nodeId)
                val nestedCtx = new WrappingTypeContext (ctx) {
                    override def findType (symbol : Id) : TypeInfo = symbol match {
                        case `variable` => varType
                        case other => super.findType(other)
                    }
                }
                checkType (fun (Lookup (variable)), nestedCtx)
            }
        })
        case Let (decls, in) => {
            val nestedCtx = new WrappingTypeContext (ctx) {
                override def findType (symbol : Id) : TypeInfo = symbol match {
                    case sym if decls contains sym => checkType(decls(sym), this) // TODO : check for recursive declarations
                    case other => super.findType (other)
                }
            }
            checkType (in, nestedCtx)
        }
        case Case (ref, cases) => checkType (ref, ctx) match {
            case BooleanLib.SimpleBoolean () => cases map (c => checkType(c._2, ctx)) reduceLeft (ctx.lca)
        }
        case Build (fields) => ComplexType (fields map {case (id, e) => (id, checkType(e, ctx))}, Layers (ExactlyOne (), UnnamedContract ()))
    }
    
}