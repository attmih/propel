package propel.lib

import propel.core.TypeTree._
import propel.core.TypeContext

object CollectionLib extends Library {

    override val namespace = "propel.lib.collection"
        
    override val functions = Seq(Empty, Map, Filter/*, GroupBy*/)
    
    object Empty   extends NullaryFunction ("Empty") {
        override def typeInfo (ctx : TypeContext) = SimpleType (Layers (Multi (0, Some(0)), NothingContract ()))
    }
    
    object Map     extends BinaryFunction ("Map") {
        override def typeInfo (ctx : TypeContext) = LambdaType ({
            case info1 : ConcreteTypeInfo => 
                val itemType = info1 withLayers (_.copy(multi = ExactlyOne ()))
                LambdaType ({
                    case LambdaType (typeLam) if typeLam isDefinedAt itemType => 
                        typeLam apply itemType match {
                            case newItemType : ConcreteTypeInfo => newItemType withLayers (_.copy(multi = info1.layers.multi))
                        }
                })
        })
    }
    
    object Filter  extends BinaryFunction ("Filter") {
        override def typeInfo (ctx : TypeContext) = LambdaType ({
            case info1 : ConcreteTypeInfo => {
                val itemType = info1 withLayers (_.copy(multi = ExactlyOne ()))
                LambdaType ({
                    case LambdaType (typeLam) if typeLam isDefinedAt itemType => typeLam apply itemType match {
                        case BooleanLib.SimpleBoolean () => info1 withLayers (layers => layers.copy(multi = layers.multi.copy(lowerBound = 0)))
                    }
                })
            }
        })
    }
    
    //object GroupBy extends BinaryFunction ("GroupBy")
    
}