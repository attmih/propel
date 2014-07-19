package propel.lib

import propel.core.TypeTree._
import propel.core.TypeContext

object CompareLib extends Library {

    override val namespace = "propel.lib.compare"
    
    override val functions = Seq(Equal, LessThan)
        
    object Equal extends BinaryFunction ("Equal") {
        override def typeInfo (ctx : TypeContext) = LambdaType ({
            case info1 => LambdaType ({
                case info2 if ctx.isAssignableFrom(info1, info2) || ctx.isAssignableFrom(info2, info1) => BooleanLib.SimpleBoolean ()
            })
        })
    }
    
    object LessThan extends BinaryFunction ("LessThan") {
        override def typeInfo (ctx : TypeContext) = LambdaType ({
            case info1 => LambdaType ({
                case info2 if (ctx.isAssignableFrom(info1, info2) && ctx.isOrdered(info1)) || (ctx.isAssignableFrom(info1, info2) && ctx.isOrdered(info2)) => BooleanLib.SimpleBoolean ()
            })
        })
    }
    
}