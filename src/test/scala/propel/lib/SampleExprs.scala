package propel.lib

import propel.core.ExprTree._
import BooleanLib._
import CompareLib._
import CollectionLib.Filter
import DecimalLib._
import SampleLib._

object SampleExprs {

    val trueOrFalse = Or (True (), False ())
    
    val oneEqualsZero = Equal (One (), Zero ())
    
    val oneMinusOne = Subtract (One (), One())
    
    val oneMinusOneIsZero = Equal (oneMinusOne, Zero ())
    
    val oneMinusOneIsZeroWithLet = 
        Let (
           decls = Map ("oneMinusOne" -> oneMinusOne), 
           in = Equal (Lookup("oneMinusOne"), Zero ()))
    
    val recursiveLet = 
        Let (
           decls = Map (
               "a" -> Add (Lookup ("b"), One ()),
               "b" -> Subtract (Lookup ("a"), One())), 
           in = Equal (Lookup("a"), Zero ()))
           
    val zeroIsLessThanOne = LessThan (Zero (), One())
    
    val allThePeople = People ()
    val peopleOver1 = Filter (People (), Lambda (p => LessThan (One (), Age (p))))
    
}