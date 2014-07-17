package propel.lib

object BooleanLib extends Library {

    override val namespace = "propel.lib.boolean"
    
    object True     extends NullaryFunction ("True")
    object False    extends NullaryFunction ("False")
    
    object Not      extends UnaryFunction   ("Not")

    object And      extends BinaryFunction  ("And")
    object Or       extends BinaryFunction  ("Or")
    
}