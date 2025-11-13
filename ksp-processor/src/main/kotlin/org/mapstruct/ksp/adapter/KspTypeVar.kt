package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.symbol.KSTypeParameter
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.Element
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.type.TypeVariable
import javax.lang.model.type.TypeVisitor

class KspTypeVar(val param: KSTypeParameter) : TypeVariable {

    override fun asElement(): Element? {
        TODO("Not yet implemented")
    }

    override fun getUpperBound(): TypeMirror? {
    }

    override fun getLowerBound(): TypeMirror? {
        TODO("Not yet implemented")
    }

    override fun getKind(): TypeKind {
        return TypeKind.TYPEVAR
    }

    override fun getAnnotationMirrors(): List<AnnotationMirror?>? {
        TODO("Not yet implemented")
    }

    override fun <A : Annotation?> getAnnotation(annotationType: Class<A?>?): A? {
        TODO("Not yet implemented")
    }

    override fun <A : Annotation?> getAnnotationsByType(annotationType: Class<A?>?): Array<out A?>? {
        TODO("Not yet implemented")
    }

    override fun <R : Any?, P : Any?> accept(v: TypeVisitor<R?, P?>?, p: P?): R? {
        TODO("Not yet implemented")
    }

    override fun toString(): String = "KspTypeVar[${param}]"
}