package propel.lib

import propel.core.TypeTree._
import propel.core.TypeContext

object DecimalLib extends Library {

    override val namespace = "propel.lib.decimal"
        
    override val functions = Seq (Zero, One, Minus, Add, Subtract, Multiply, Divide)

    abstract class NullaryDecimal (sym : String) extends NullaryFunction (sym) {
        override def typeInfo (ctx : TypeContext) = SimpleDecimal ()
    }
    object Zero     extends NullaryDecimal ("Zero")
    object One      extends NullaryDecimal ("One")
    
    object Minus    extends UnaryFunction   ("Minus") {
        override def typeInfo (ctx : TypeContext) = LambdaType ({case SimpleDecimal () => SimpleDecimal ()})
    }
    
    abstract class BinaryDecimal (sym : String) extends BinaryFunction (sym) {
        override def typeInfo (ctx : TypeContext) = LambdaType ({case SimpleDecimal () => LambdaType ({case SimpleDecimal () => SimpleDecimal ()})})
    }
    object Add      extends BinaryDecimal  ("Add")
    object Subtract extends BinaryDecimal  ("Subtract")
    object Multiply extends BinaryDecimal  ("Multiply")
    object Divide   extends BinaryDecimal  ("Divide")
        
    val DecimalContract = contract ("Decimal")
    
    object SimpleDecimal {
        def apply() = SimpleType (Layers (ExactlyOne (), DecimalContract))
        def unapply(typeInfo : TypeInfo) = typeInfo == SimpleType (Layers (ExactlyOne (), DecimalContract))
    }
    
}