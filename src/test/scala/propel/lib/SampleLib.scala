package propel.lib

import propel.core.TypeTree._
import propel.core.TypeContext

object SampleLib extends Library {

    override val namespace = "propel.lib.sample"
        
    override val functions = Seq (People, Age)
    
    val PersonContract = contract ("Person")
    
    object People extends NullaryFunction ("People") {
        override def typeInfo (ctx : TypeContext) = SimpleType (Layers (ZeroOrMore (), PersonContract))
    }
        
    object Age extends UnaryFunction ("Age") {
        override def typeInfo (ctx : TypeContext) = LambdaType ({
            case SimpleType (Layers (ExactlyOne (), PersonContract)) => SimpleType (Layers (ExactlyOne (), DecimalLib.DecimalContract))
        })
    }
    
}