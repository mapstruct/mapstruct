// ABOUTME: Adapter for KSP function types to javax.lang.model ExecutableType
// ABOUTME: Represents method/function signatures with parameter and return types for MapStruct processor
package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.type.ExecutableType
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.type.TypeVariable
import javax.lang.model.type.TypeVisitor

class KspExecutableType(
    private val executableElement: KspExecutableElement,
    private val resolver: Resolver,
    private val logger: KSPLogger
) : ExecutableType {

    override fun getTypeVariables(): List<TypeVariable> {
        return executableElement.typeParameters.map { typeParam ->
            if (typeParam is KspTypeParameterElement) {
                KspTypeVar(typeParam.typeParameter, resolver, logger)
            } else {
                error("TypeParameterElement must be KspTypeParameterElement")
            }
        }
    }

    override fun getReturnType(): TypeMirror {
        return executableElement.returnType
    }

    override fun getParameterTypes(): List<TypeMirror> {
        return executableElement.parameters.map { param ->
            param.asType()
        }
    }

    override fun getReceiverType(): TypeMirror? {
        return executableElement.receiverType
    }

    override fun getThrownTypes(): List<TypeMirror> {
        return executableElement.thrownTypes
    }

    override fun getKind(): TypeKind {
        return TypeKind.EXECUTABLE
    }

    override fun <R : Any?, P : Any?> accept(v: TypeVisitor<R?, P?>?, p: P?): R? {
        return v?.visitExecutable(this, p)
    }

    override fun getAnnotationMirrors(): MutableList<out AnnotationMirror> {
        return executableElement.annotationMirrors.toMutableList()
    }

    override fun <A : Annotation?> getAnnotation(annotationType: Class<A>?): A? {
        @Suppress("UNCHECKED_CAST")
        return executableElement.getAnnotation(annotationType as Class<A?>?)
    }

    override fun <A : Annotation?> getAnnotationsByType(annotationType: Class<A>?): Array<A?> {
        @Suppress("UNCHECKED_CAST")
        return executableElement.getAnnotationsByType(annotationType as Class<A?>?)
    }

    override fun toString(): String = "KspExecutableType[${executableElement}]"
}
