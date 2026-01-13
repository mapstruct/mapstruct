// ABOUTME: Adapter for KSP annotations to javax.lang.model AnnotationMirror
// ABOUTME: Bridges KSP annotation representation to Java annotation processing API for MapStruct
package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.isDefault
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.ExecutableElement
import javax.lang.model.type.DeclaredType

class KspAnnotationMirror(
    val annotation: KSAnnotation,
    private val resolver: Resolver,
    private val logger: KSPLogger
) : AnnotationMirror {

    private val annotationTypeImpl: DeclaredType by lazy {
        logger.info("Creating annotation type for $annotation")
        val annoType = annotation.annotationType.resolve()
        val decl = annoType.declaration
        if (decl is KSClassDeclaration) {
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

        for (argument in annotation.arguments) {
            if (argument.isDefault()) continue

            val name = argument.name?.asString() ?: continue

            val annoTypeDecl = annotation.annotationType.resolve().declaration
            when (annoTypeDecl) {
                is KSClassDeclaration -> {
                    val method = annoTypeDecl.declarations.firstOrNull { func -> func.simpleName.asString() == name }
                    check(method != null) { "Could not find method $name in annotation type ${annoTypeDecl.qualifiedName?.asString()}" }

                    val executableElement = KspExecutableElement(method, resolver, logger)
                    val value = KspAnnotationValue(argument.value, resolver, logger)
                    result[executableElement] = value
                }
                else -> error("Annotation type is not a class declaration: ${annoTypeDecl::class.simpleName}")
            }
        }

        return result
    }

    override fun toString(): String {
        val annoType = annotation.annotationType.resolve()
        return "@${annoType.declaration.qualifiedName?.asString() ?: "Unknown"}"
    }
}
