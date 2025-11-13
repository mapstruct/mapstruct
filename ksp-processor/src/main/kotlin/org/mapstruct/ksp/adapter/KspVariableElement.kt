// ABOUTME: Adapter for KSP parameters and properties to javax.lang.model VariableElement
// ABOUTME: Bridges KSP variable representation to Java annotation processing API for MapStruct processor
package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter
import com.google.devtools.ksp.symbol.Modifier as KspModifier
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ElementVisitor
import javax.lang.model.element.Modifier
import javax.lang.model.element.Name
import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeMirror

class KspVariableElement : VariableElement {
    private val annotated: KSAnnotated
    private val resolver: Resolver
    private val logger: KSPLogger
    private val name: String
    private val typeMirror: TypeMirror

    constructor(parameter: KSValueParameter, resolver: Resolver, logger: KSPLogger) {
        this.annotated = parameter
        this.resolver = resolver
        this.logger = logger
        this.name = parameter.name?.asString() ?: ""

        val paramType = parameter.type.resolve()
        val decl = paramType.declaration
        this.typeMirror = if (decl is com.google.devtools.ksp.symbol.KSClassDeclaration) {
            KspTypeMirror(
                KspClassTypeElement(decl, resolver, logger),
                resolver,
                logger
            )
        } else {
            KspNoType(javax.lang.model.type.TypeKind.NONE)
        }
    }

    constructor(property: KSPropertyDeclaration, resolver: Resolver, logger: KSPLogger) {
        this.annotated = property
        this.resolver = resolver
        this.logger = logger
        this.name = property.simpleName.asString()

        val propType = property.type.resolve()
        val decl = propType.declaration
        this.typeMirror = if (decl is com.google.devtools.ksp.symbol.KSClassDeclaration) {
            KspTypeMirror(
                KspClassTypeElement(decl, resolver, logger),
                resolver,
                logger
            )
        } else {
            KspNoType(javax.lang.model.type.TypeKind.NONE)
        }
    }

    override fun getConstantValue(): Any? = null

    override fun getSimpleName(): Name = StringName(name)

    override fun getEnclosingElement(): Element? {
        return when (val item = annotated) {
            is KSValueParameter -> {
                val funcDecl = item.parent as? com.google.devtools.ksp.symbol.KSFunctionDeclaration
                funcDecl?.let { KspExecutableElement(it, resolver, logger) }
            }
            is KSPropertyDeclaration -> {
                val parent = item.parentDeclaration
                when (parent) {
                    is com.google.devtools.ksp.symbol.KSClassDeclaration ->
                        KspClassTypeElement(parent, resolver, logger)
                    else -> null
                }
            }
            else -> null
        }
    }

    override fun getEnclosedElements(): List<Element> = emptyList()

    override fun asType(): TypeMirror = typeMirror

    override fun getKind(): ElementKind {
        return when (annotated) {
            is KSValueParameter -> ElementKind.PARAMETER
            is KSPropertyDeclaration -> ElementKind.FIELD
            else -> ElementKind.OTHER
        }
    }

    override fun getModifiers(): Set<Modifier> {
        val modifiers = mutableSetOf<Modifier>()

        when (val item = annotated) {
            is KSPropertyDeclaration -> {
                if (KspModifier.PUBLIC in item.modifiers) modifiers.add(Modifier.PUBLIC)
                if (KspModifier.PRIVATE in item.modifiers) modifiers.add(Modifier.PRIVATE)
                if (KspModifier.PROTECTED in item.modifiers) modifiers.add(Modifier.PROTECTED)
                if (KspModifier.FINAL in item.modifiers) modifiers.add(Modifier.FINAL)
            }
        }

        return modifiers
    }

    override fun getAnnotationMirrors(): List<AnnotationMirror> {
        return annotated.annotations.map { annotation ->
            KspAnnotationMirror(annotation, resolver, logger)
        }.toList()
    }

    override fun <A : Annotation?> getAnnotation(annotationType: Class<A?>?): A? {
        if (annotationType == null) return null

        val targetName = annotationType.name
        val annotation = annotated.annotations.firstOrNull { anno ->
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
        return v?.visitVariable(this, p)
    }

    override fun toString(): String = "KspVariableElement[$name]"
}
