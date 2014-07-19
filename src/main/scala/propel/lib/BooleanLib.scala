package propel.lib

import propel.core.TypeTree._
import propel.core.TypeContext
    
object BooleanLib extends Library {

    override val namespace = "propel.lib.boolean"
        
    override val functions = Seq(True, False, Not, And, Or)
    
    abstract class NullaryBoolean (sym : String) extends NullaryFunction (sym) {
        override def typeInfo (ctx : TypeContext) = SimpleBoolean ()
    }
    object True extends NullaryBoolean ("True")
    object False extends NullaryBoolean ("False")
    
    object Not extends UnaryFunction ("Not") {
        override def typeInfo (ctx : TypeContext) = LambdaType ({case SimpleBoolean () => SimpleBoolean ()})
    }

    abstract class BinaryBoolean (sym : String) extends BinaryFunction (sym) {
        override def typeInfo (ctx : TypeContext) = LambdaType ({case SimpleBoolean () => LambdaType ({case SimpleBoolean () => SimpleBoolean ()})})
    }
    object And      extends BinaryBoolean ("And")
    object Or       extends BinaryBoolean ("Or")
    
    val BooleanContract = contract ("Boolean")
    
    object SimpleBoolean {
        def apply() = SimpleType (Layers (ExactlyOne (), BooleanContract))
        def unapply(typeInfo : TypeInfo) = typeInfo == SimpleType (Layers (ExactlyOne (), BooleanContract))
    }
    
}