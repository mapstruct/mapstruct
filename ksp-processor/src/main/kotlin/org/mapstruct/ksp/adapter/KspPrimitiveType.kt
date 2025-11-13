package org.mapstruct.ksp.adapter

import javax.lang.model.element.AnnotationMirror
import javax.lang.model.type.PrimitiveType
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeVisitor

class KspPrimitiveType(private val primitiveKind: TypeKind) : PrimitiveType {

    override fun getKind(): TypeKind = primitiveKind

    override fun getAnnotationMirrors(): List<AnnotationMirror> = emptyList()

    override fun <A : Annotation?> getAnnotation(annotationType: Class<A?>?): A? = null

    override fun <A : Annotation?> getAnnotationsByType(annotationType: Class<A?>?): Array<A?> {
        @Suppress("UNCHECKED_CAST")
        return arrayOfNulls<Annotation>(0) as Array<A?>
    }

    override fun <R : Any?, P : Any?> accept(v: TypeVisitor<R?, P?>?, p: P?): R? {
        return v?.visitPrimitive(this, p)
    }

    override fun toString(): String = "KspPrimitiveType[$primitiveKind]"
}
