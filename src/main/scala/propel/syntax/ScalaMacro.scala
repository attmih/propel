package propel.syntax

import language.experimental.macros
import reflect.macros.Context
import propel.core.{ExprTree => et}

object ScalaMacro {

  def propel (param: Any): et.Expr = macro propel_impl
 
  def propel_impl (c: Context) (param: c.Expr[Any]): c.Expr[et.Expr] = {
      val e = translate (c) (param.tree)
      //c.echo(c.enclosingPosition, e.toString)
      e
  }
  
  def translate (c: Context) (tree: c.Tree) : c.Expr[et.Expr] = {
      import c.universe._
      tree match {
          case Apply (fun, args)    => {
              val translatedArgs = args map (t => translate (c)(t)) 
              (translate (c) (fun) +: translatedArgs).reduceLeft ((a, b) => reify { et.Apply (a.splice, b.splice) })
          }
          case Select (This (any), name)  => {
              val n = c.Expr[String] (Literal (Constant (name.decoded)))
              reify { et.Lookup (n.splice) }
          }
          case Select (qual, name)  => {
              val n = c.Expr[String] (Literal (Constant (name.decoded)))
              reify { et.Apply (et.Lookup (n.splice), (translate (c) (qual)).splice) }
          }
          case Literal (const) => {
              val n = c.Expr[String] (Literal (Constant (const.value.toString)))
              reify { et.Lookup (n.splice) }
          }

      }
  }
    
}