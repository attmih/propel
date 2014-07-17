package propel.lib

object DecimalLib extends Library {

    override val namespace = "propel.lib.decimal"

    object Zero     extends NullaryFunction ("Zero")
    object One      extends NullaryFunction ("One")
    
    object Minus    extends UnaryFunction   ("Minus")
    
    object Add      extends BinaryFunction  ("Add")
    object Subtract extends BinaryFunction  ("Subtract")
    object Multiply extends BinaryFunction  ("Multiply")
    object Divide   extends BinaryFunction  ("Divide")
        
}