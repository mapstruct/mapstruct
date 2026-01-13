package org.mapstruct.ksp.adapter

import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.type.TypeVisitor
import javax.lang.model.type.WildcardType

class KspWildcardType(
    private val extendsBound: TypeMirror?,
    private val superBound: TypeMirror?
) : AbstractKspAnnotatedConstruct(), WildcardType {

    override fun getExtendsBound(): TypeMirror? = extendsBound

    override fun getSuperBound(): TypeMirror? = superBound

    override fun getKind(): TypeKind = TypeKind.WILDCARD

    override fun <R : Any?, P : Any?> accept(v: TypeVisitor<R?, P?>?, p: P?): R? {
        return v?.visitWildcard(this, p)
    }

    override fun toString(): String {
        return when {
            extendsBound != null -> "? extends $extendsBound"
            superBound != null -> "? super $superBound"
            else -> "?"
        }
    }
}
