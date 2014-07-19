package propel.core

import TypeTree._

trait TypeContext {

    def lca (a : TypeInfo, b : TypeInfo) : TypeInfo
    
    def isAssignableFrom (parent : TypeInfo, child : TypeInfo) : Boolean
    
    def isOrdered (info : TypeInfo) : Boolean
    
    def findType (symbol : Id) : TypeInfo
    
}

abstract class WrappingTypeContext (val innerCtx : TypeContext) extends TypeContext {
    
    override def lca (a : TypeInfo, b : TypeInfo) : TypeInfo =
        innerCtx.lca(a, b)
    
    override def isAssignableFrom (parent : TypeInfo, child : TypeInfo) : Boolean =
        innerCtx.isAssignableFrom(parent, child)
    
    override def isOrdered (info : TypeInfo) : Boolean =
        innerCtx.isOrdered(info)
    
    override def findType (symbol : Id) : TypeInfo = 
        innerCtx.findType(symbol)
    
}