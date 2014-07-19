package propel.core

import org.kiama._
import org.kiama.attribution.Attribution._
import ExprTree._

object ExprAttributes {

    val nodeId : Expr => Long =
        attr {
            case e if e.isRoot  => 0
            case e if e.isFirst => e.parent[Expr] -> nodeId + 1
            case e              => e.prev[Expr] -> nodeId + 1
        }

    val undefinedSymbols : Expr => Set[Id] =
        attr {
            case Apply(e1, e2) => (e1 -> undefinedSymbols) ++ (e2 -> undefinedSymbols)
            case Lookup(id)    => Set(id)
            case Let(defs, in) => ((in -> undefinedSymbols) ++ (defs.values flatMap undefinedSymbols)) -- defs.keySet
            case e @ Lambda(fun) => {
                val id = "_"+(e -> nodeId)
                fun (Lookup (id)) -> undefinedSymbols - id
            }
            case Case(ref, cases) => (ref -> undefinedSymbols) ++ (cases flatMap (p => Seq(p._1, p._2)) flatMap undefinedSymbols)
            case Build(fields)  => (fields map (_._2)).toSet flatMap undefinedSymbols
        }

}