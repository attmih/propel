package propel.typesystem

import propel.core.ExprTree.Id

object StructureTree {

    sealed abstract class Structure
    
    case class SimpleStructure () extends Structure
    case class ComplexStructure (fields : Seq[(Id, Structure)]) extends Structure
    
}