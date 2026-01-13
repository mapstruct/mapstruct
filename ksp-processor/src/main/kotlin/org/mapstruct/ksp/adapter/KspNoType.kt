package org.mapstruct.ksp.adapter

import javax.lang.model.type.NoType
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeVisitor

class KspNoType(private val noTypeKind: TypeKind) : AbstractKspAnnotatedConstruct(), NoType {

    override fun getKind(): TypeKind = noTypeKind

    override fun <R : Any?, P : Any?> accept(v: TypeVisitor<R?, P?>?, p: P?): R? {
        return v?.visitNoType(this, p)
    }

    override fun toString(): String = "KspNoType[$noTypeKind]"
}
