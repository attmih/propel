package propel.core

import ExprTree._
import TypeTree._
import propel.lib._
import propel.lib.CollectionLib._
import propel.lib.DecimalLib._

object TypeCheckerTest extends App {

    import propel.lib.SampleExprs._
    
    val ctx = new TypeContext {
        
        val libs: Seq[Library] = Seq (BooleanLib, CompareLib, DecimalLib, CollectionLib, SampleLib)
        
        def lca (a : TypeInfo, b : TypeInfo) : TypeInfo = 
            a
        
        def isAssignableFrom (parent : TypeInfo, child : TypeInfo) : Boolean = 
            parent == child
    
        def isOrdered (info : TypeInfo) : Boolean =
            true
    
        def findType (symbol : Id) : TypeInfo =
            libs.find(l => l.typeInfo(this).isDefinedAt(symbol)).get.typeInfo(this)(symbol)
            
    }
    
    println (TypeChecker.checkType (trueOrFalse, ctx))
    println (TypeChecker.checkType (oneEqualsZero, ctx))
    println (TypeChecker.checkType (oneMinusOne, ctx))
    println (TypeChecker.checkType (oneMinusOneIsZero, ctx))
    println (TypeChecker.checkType (oneMinusOneIsZeroWithLet, ctx))
    println (TypeChecker.checkType (zeroIsLessThanOne, ctx))
    
    println (TypeChecker.checkType (Map(Empty (), Lambda(x => Build (Seq ("_1" -> oneMinusOne, "_2" -> oneMinusOneIsZero)))), ctx))
    
    println (TypeChecker.checkType (Map(One (), Lambda(x => Add (x, One ()))), ctx))
    
    println (TypeChecker.checkType (peopleOver1, ctx))
    
}