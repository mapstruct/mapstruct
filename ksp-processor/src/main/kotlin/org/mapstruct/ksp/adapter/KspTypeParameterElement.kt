// ABOUTME: Adapter for KSP type parameters to javax.lang.model TypeParameterElement
// ABOUTME: Bridges KSP generic type parameter representation to Java annotation processing API
package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSTypeParameter
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ElementVisitor
import javax.lang.model.element.Modifier
import javax.lang.model.element.Name
import javax.lang.model.element.TypeParameterElement
import javax.lang.model.type.TypeMirror

class KspTypeParameterElement(
    val typeParameter: KSTypeParameter,
    private val resolver: Resolver,
    private val logger: KSPLogger
) : TypeParameterElement {

    override fun getGenericElement(): Element {
        val parent = typeParameter.parent
        return when (parent) {
            is com.google.devtools.ksp.symbol.KSClassDeclaration ->
                KspClassTypeElement(parent, resolver, logger)
            is com.google.devtools.ksp.symbol.KSFunctionDeclaration ->
                KspExecutableElement(parent, resolver, logger)
            else -> error("Unexpected parent for type parameter: ${parent!!::class.simpleName}")
        }
    }

    override fun getBounds(): List<TypeMirror> {
        return typeParameter.bounds.map { boundRef ->
            val bound = boundRef.resolve()
            val decl = bound.declaration
            if (decl is com.google.devtools.ksp.symbol.KSClassDeclaration) {
                KspTypeMirror(
                    KspClassTypeElement(decl, resolver, logger),
                    resolver,
                    logger
                )
            } else {
                KspNoType(javax.lang.model.type.TypeKind.NONE)
            }
        }.toList()
    }

    override fun asType(): TypeMirror {
        return KspTypeVar(typeParameter, resolver, logger)
    }

    override fun getKind(): ElementKind {
        return ElementKind.TYPE_PARAMETER
    }

    override fun getModifiers(): Set<Modifier> {
        return emptySet()
    }

    override fun getSimpleName(): Name {
        return StringName(typeParameter.name.asString())
    }

    override fun getEnclosingElement(): Element {
        return getGenericElement()
    }

    override fun getEnclosedElements(): List<Element> {
        return emptyList()
    }

    override fun getAnnotationMirrors(): List<AnnotationMirror> {
        return typeParameter.annotations.map { annotation ->
            KspAnnotationMirror(annotation, resolver, logger)
        }.toList()
    }

    override fun <A : Annotation?> getAnnotation(annotationType: Class<A?>?): A? {
        if (annotationType == null) return null

        val targetName = annotationType.name
        val annotation = typeParameter.annotations.firstOrNull { anno ->
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

    override fun <R : Any?, P : Any?> accept(v: ElementVisitor<R?, P?>?, p: P?): R? {
        return v?.visitTypeParameter(this, p)
    }

    override fun toString(): String = "KspTypeParameterElement[${typeParameter.name.asString()}]"
}
