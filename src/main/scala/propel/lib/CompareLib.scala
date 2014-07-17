package propel.lib

object CompareLib extends Library {

    override val namespace = "propel.lib.compare"
    
    object Equal    extends BinaryFunction ("Equal")
    object LessThan extends BinaryFunction ("LessThan")
    
}