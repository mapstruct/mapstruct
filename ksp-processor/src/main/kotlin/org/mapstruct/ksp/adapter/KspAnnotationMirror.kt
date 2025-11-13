// ABOUTME: Adapter for KSP annotations to javax.lang.model AnnotationMirror
// ABOUTME: Bridges KSP annotation representation to Java annotation processing API for MapStruct
package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotation
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.ExecutableElement
import javax.lang.model.type.DeclaredType

class KspAnnotationMirror(
    private val annotation: KSAnnotation,
    private val resolver: Resolver,
    private val logger: KSPLogger
) : AnnotationMirror {

    private val annotationTypeImpl: DeclaredType by lazy {
        logger.info("Creating annotation type for $annotation")
        val annoType = annotation.annotationType.resolve()
        val decl = annoType.declaration
        if (decl is com.google.devtools.ksp.symbol.KSClassDeclaration) {
            val typeElement = KspClassTypeElement(decl, resolver, logger, annotation)
            KspTypeMirror(typeElement, resolver, logger)
        } else {
            error("Annotation type is not a class declaration: ${decl::class.simpleName}")
        }
    }

    override fun getAnnotationType(): DeclaredType {
        return annotationTypeImpl
    }

    override fun getElementValues(): Map<ExecutableElement, AnnotationValue> {
        val result = mutableMapOf<ExecutableElement, AnnotationValue>()

        annotation.arguments.forEach { argument ->
            val name = argument.name?.asString() ?: return@forEach
            val annoTypeDecl = annotation.annotationType.resolve().declaration
            if (annoTypeDecl is com.google.devtools.ksp.symbol.KSClassDeclaration) {
                val method = annoTypeDecl.getAllFunctions().firstOrNull { func ->
                    func.simpleName.asString() == name
                }
                if (method != null) {
                    val executableElement = KspExecutableElement(method, resolver, logger)
                    val value = KspAnnotationValue(argument.value, resolver, logger)
                    result[executableElement] = value
                }
            }
        }

        return result
    }

    override fun toString(): String {
        val annoType = annotation.annotationType.resolve()
        return "@${annoType.declaration.qualifiedName?.asString() ?: "Unknown"}"
    }
}
