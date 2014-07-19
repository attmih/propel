package propel.core

import org.kiama.==>

object TypeTree {

    sealed abstract class TypeInfo
    case class LambdaType (fun : TypeInfo ==> TypeInfo) extends TypeInfo
    
    sealed abstract class ConcreteTypeInfo extends TypeInfo {
        def layers: Layers
        def withLayers (fun : Layers => Layers) : ConcreteTypeInfo
    }
    case class SimpleType (layers : Layers) extends ConcreteTypeInfo {
        override def withLayers (fun : Layers => Layers) = copy(layers = fun (layers))
    }
    case class ComplexType (fields : Seq[(Id, TypeInfo)], layers : Layers) extends ConcreteTypeInfo {
        override def withLayers (fun : Layers => Layers) = copy(layers = fun (layers))
    }
    
    case class Layers (multi : Multi, contract : Contract)
    
    case class Multi (lowerBound : Int, upperBound : Option[Int])
    
    protected abstract class MultiExtract (val lower : Int, val upper : Option[Int]) {
        def apply() = Multi (lower, upper)
        def unapply(multi : Multi) : Boolean = multi == Multi (lower, upper)
    }
    object ExactlyZero  extends MultiExtract (0, Some(0))
    object ExactlyOne   extends MultiExtract (1, Some(1))
    object ZeroOrOne    extends MultiExtract (0, Some(1))
    object ZeroOrMore   extends MultiExtract (0, None)
    object OneOrMore    extends MultiExtract (1, None)
    
    sealed abstract class Contract
    case class AnythingContract () extends Contract
    case class NothingContract () extends Contract
    case class NamedContract (symbol : Id) extends Contract
    case class UnnamedContract () extends Contract
    
}