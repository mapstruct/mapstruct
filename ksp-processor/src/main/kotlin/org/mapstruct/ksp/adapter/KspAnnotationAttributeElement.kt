// ABOUTME: Adapter for KSP annotation properties to javax.lang.model ExecutableElement
// ABOUTME: Converts annotation attributes (properties) to method-like ExecutableElements for Java annotation processing API
package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.Modifier as KspModifier
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ElementVisitor
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.Modifier
import javax.lang.model.element.Name
import javax.lang.model.element.TypeParameterElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeMirror

/**
 * Adapter that presents an annotation property (attribute) as an ExecutableElement (method).
 * This is necessary because in Java, annotation attributes are methods, but in Kotlin/KSP
 * they are properties.
 *
 * @param property The annotation property declaration
 * @param annotationInstance The KSAnnotation instance (if available) to get default values from
 */
class KspAnnotationAttributeElement(
    private val property: KSPropertyDeclaration,
    private val annotationInstance: KSAnnotation?,
    private val resolver: Resolver,
    private val logger: KSPLogger
) : ExecutableElement {

    override fun getTypeParameters(): List<TypeParameterElement> {
        return emptyList()
    }

    override fun getReturnType(): TypeMirror {
        val propType = property.type.resolve()
        val decl = propType.declaration

        return if (decl is com.google.devtools.ksp.symbol.KSClassDeclaration) {
            KspTypeMirror(
                KspClassTypeElement(decl, resolver, logger),
                resolver,
                logger
            )
        } else {
            KspNoType(javax.lang.model.type.TypeKind.NONE)
        }
    }

    override fun getParameters(): List<VariableElement> {
        return emptyList()
    }

    override fun getReceiverType(): TypeMirror? {
        return null
    }

    override fun isVarArgs(): Boolean {
        return false
    }

    override fun isDefault(): Boolean {
        return false
    }

    override fun getThrownTypes(): List<TypeMirror> {
        return emptyList()
    }

    override fun getDefaultValue(): AnnotationValue? {
        if (annotationInstance == null) {
            error("Cannot get default value for annotation attribute '${property.simpleName.asString()}' without KSAnnotation instance")
        }

        val propertyName = property.simpleName.asString()

        // Get the default value from KSAnnotation's default arguments
        // KSP provides defaults through the defaultArguments property
        val defaultArg = annotationInstance.defaultArguments.firstOrNull {
            it.name?.asString() == propertyName
        }

        if (defaultArg != null) {
            return KspAnnotationValue(defaultArg.value, resolver, logger)
        }

        // If no default argument found, this attribute must not have a default value
        return null
    }

    override fun getSimpleName(): Name {
        return StringName(property.simpleName.asString())
    }

    override fun getEnclosingElement(): Element {
        val parent = property.parentDeclaration
        return when (parent) {
            is com.google.devtools.ksp.symbol.KSClassDeclaration ->
                KspClassTypeElement(parent, resolver, logger)
            else -> error("Unexpected parent type for annotation property: ${parent!!::class.simpleName}")
        }
    }

    override fun getEnclosedElements(): List<Element> {
        return emptyList()
    }

    override fun getAnnotationMirrors(): List<AnnotationMirror> {
        return property.annotations.map { annotation ->
            KspAnnotationMirror(annotation, resolver, logger)
        }.toList()
    }

    override fun <A : Annotation?> getAnnotation(annotationType: Class<A?>?): A? {
        if (annotationType == null) return null

        val targetName = annotationType.name
        val annotation = property.annotations.firstOrNull { anno ->
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

    override fun asType(): TypeMirror {
        return getReturnType()
    }

    override fun getKind(): ElementKind {
        return ElementKind.METHOD
    }

    override fun getModifiers(): Set<Modifier> {
        val modifiers = mutableSetOf<Modifier>()
        modifiers.add(Modifier.PUBLIC)
        modifiers.add(Modifier.ABSTRACT)
        return modifiers
    }

    override fun <R : Any?, P : Any?> accept(v: ElementVisitor<R?, P?>?, p: P?): R? {
        return v?.visitExecutable(this, p)
    }

    override fun toString(): String = "KspAnnotationAttributeElement[${property.simpleName.asString()}]"
}
