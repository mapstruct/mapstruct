// ABOUTME: Adapter for KSP enum entry declarations to javax.lang.model VariableElement
// ABOUTME: Bridges enum constant representation for Java annotation processing API compatibility
package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ElementVisitor
import javax.lang.model.element.Modifier
import javax.lang.model.element.Name
import javax.lang.model.element.VariableElement
import javax.lang.model.type.TypeMirror

/**
 * Adapter that presents an enum constant (KSP ENUM_ENTRY) as a VariableElement.
 * This is required for proper annotation value visiting when enum constants are used in annotations.
 */
class KspEnumConstantElement(
    private val enumEntry: KSClassDeclaration,
    private val resolver: Resolver,
    private val logger: KSPLogger
) : VariableElement {

    init {
        require(enumEntry.classKind == com.google.devtools.ksp.symbol.ClassKind.ENUM_ENTRY) {
            "KspEnumConstantElement requires an ENUM_ENTRY but got ${enumEntry.classKind}"
        }
    }

    override fun getConstantValue(): Any? {
        // Enum constants don't have compile-time constant values in the traditional sense
        return null
    }

    override fun getSimpleName(): Name {
        return StringName(enumEntry.simpleName.asString())
    }

    override fun getEnclosingElement(): Element {
        val parent = enumEntry.parentDeclaration
        return when (parent) {
            is KSClassDeclaration -> KspClassTypeElement(parent, resolver, logger)
            else -> error("Unexpected parent type for enum constant: ${parent!!::class.simpleName}")
        }
    }

    override fun getEnclosedElements(): List<Element> {
        return emptyList()
    }

    override fun asType(): TypeMirror {
        // Return the enum type itself
        val parent = enumEntry.parentDeclaration as? KSClassDeclaration
        return if (parent != null) {
            KspTypeMirror(KspClassTypeElement(parent, resolver, logger), resolver, logger)
        } else {
            KspNoType(javax.lang.model.type.TypeKind.NONE)
        }
    }

    override fun getKind(): ElementKind {
        return ElementKind.ENUM_CONSTANT
    }

    override fun getModifiers(): Set<Modifier> {
        return setOf(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
    }

    override fun getAnnotationMirrors(): List<AnnotationMirror> {
        return enumEntry.annotations.map { annotation ->
            KspAnnotationMirror(annotation, resolver, logger)
        }.toList()
    }

    override fun <A : Annotation?> getAnnotation(annotationType: Class<A?>?): A? {
        if (annotationType == null) return null

        val targetName = annotationType.name
        val annotation = enumEntry.annotations.firstOrNull { anno ->
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

    override fun toString(): String {
        return "KspEnumConstantElement[${enumEntry.qualifiedName?.asString() ?: enumEntry.simpleName.asString()}]"
    }
}
