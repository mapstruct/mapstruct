package org.mapstruct.ksp.adapter

import javax.lang.model.type.ArrayType
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.type.TypeVisitor

class KspArrayType(private val componentType: TypeMirror) : AbstractKspAnnotatedConstruct(), ArrayType {

    override fun getComponentType(): TypeMirror = componentType

    override fun getKind(): TypeKind = TypeKind.ARRAY

    override fun <R : Any?, P : Any?> accept(v: TypeVisitor<R?, P?>?, p: P?): R? {
        return v?.visitArray(this, p)
    }

    override fun toString(): String = "KspArrayType[$componentType[]]"
}
