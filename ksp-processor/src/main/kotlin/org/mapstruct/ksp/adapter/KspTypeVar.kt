package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSTypeParameter
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.Element
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.type.TypeVariable
import javax.lang.model.type.TypeVisitor

class KspTypeVar(
    val param: KSTypeParameter,
    private val resolver: Resolver? = null,
    private val logger: KSPLogger? = null
) : TypeVariable {

    override fun asElement(): Element? {
        return if (resolver != null && logger != null) {
            KspTypeParameterElement(param, resolver, logger)
        } else {
            null
        }
    }

    override fun getUpperBound(): TypeMirror? {
        if (resolver == null || logger == null) return null

        val upperBound = param.bounds.firstOrNull() ?: return null
        val resolved = upperBound.resolve()
        val decl = resolved.declaration

        return if (decl is com.google.devtools.ksp.symbol.KSClassDeclaration) {
            KspTypeMirror(
                KspClassTypeElement(decl, resolver, logger),
                resolver,
                logger
            )
        } else {
            null
        }
    }

    override fun getLowerBound(): TypeMirror? {
        return null
    }

    override fun getKind(): TypeKind {
        return TypeKind.TYPEVAR
    }

    override fun getAnnotationMirrors(): List<AnnotationMirror> {
        if (resolver == null || logger == null) return emptyList()

        return toAnnotationMirrors(param.annotations.toList(), resolver, logger)
    }

    override fun <A : Annotation?> getAnnotation(annotationType: Class<A?>?): A? {
        if (annotationType == null || resolver == null || logger == null) return null

        val targetName = annotationType.name
        val annotation = param.annotations.firstOrNull { anno ->
            anno.annotationType.resolve().declaration.qualifiedName?.asString() == targetName
        } ?: return null

        @Suppress("UNCHECKED_CAST")
        return AnnotationBuilder.buildAnnotation(annotation, annotationType as Class<out Annotation>, resolver, logger) as? A
    }

    override fun <A : Annotation?> getAnnotationsByType(annotationType: Class<A?>?): Array<A?> {
        val annotation = getAnnotation(annotationType)
        @Suppress("UNCHECKED_CAST")
        return if (annotation != null) {
            arrayOf(annotation) as Array<A?>
        } else {
            arrayOfNulls<Annotation>(0) as Array<A?>
        }
    }

    override fun <R : Any?, P : Any?> accept(v: TypeVisitor<R?, P?>?, p: P?): R? {
        return v?.visitTypeVariable(this, p)
    }

    override fun toString(): String = "KspTypeVar[${param}]"
}