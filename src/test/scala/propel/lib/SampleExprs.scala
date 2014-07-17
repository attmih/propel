package propel.lib

import BooleanLib._
import CompareLib._
import DecimalLib._

object SampleExprs {

    val trueOrFalse = Or (True (), False ())
    
    val oneEqualsZero = Equal (One (), Zero ())
    
    val oneMinusOneIsZero = Equal (Subtract (One (), One()), Zero ())
    
    val zeroIsLessThanOne = LessThan (Zero (), One())
    
}