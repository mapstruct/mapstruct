// ABOUTME: Adapter for KSP annotation argument values to javax.lang.model AnnotationValue
// ABOUTME: Wraps annotation attribute values for Java annotation processing API compatibility
package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSType
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.AnnotationValueVisitor

class KspAnnotationValue(
    private val value: Any?,
    private val resolver: Resolver? = null,
    private val logger: KSPLogger? = null
) : AnnotationValue {

    override fun getValue(): Any? = value

    override fun <R : Any?, P : Any?> accept(v: AnnotationValueVisitor<R?, P?>?, p: P?): R? = when (value) {
        is Boolean -> v?.visitBoolean(value, p)
        is Byte -> v?.visitByte(value, p)
        is Char -> v?.visitChar(value, p)
        is Double -> v?.visitDouble(value, p)
        is Float -> v?.visitFloat(value, p)
        is Int -> v?.visitInt(value, p)
        is Long -> v?.visitLong(value, p)
        is Short -> v?.visitShort(value, p)
        is String -> v?.visitString(value, p)
        is List<*> -> {
            // Convert list items to AnnotationValue objects
            val annotationValues = value.map { item ->
                when (item) {
                    is AnnotationValue -> item
                    else -> KspAnnotationValue(item, resolver, logger)
                }
            }
            v?.visitArray(annotationValues, p)
        }
        // KSP represents enum entries as KSClassDeclaration
        is KSClassDeclaration if value.classKind == com.google.devtools.ksp.symbol.ClassKind.ENUM_ENTRY -> {
            if (resolver != null && logger != null) {
                val enumConstantElement = KspEnumConstantElement(value, resolver, logger)
                v?.visitEnumConstant(enumConstantElement, p)
            } else {
                error("Cannot visit enum constant without resolver and logger")
            }
        }
        // KSType representing enum values
        is KSType if value.declaration is KSClassDeclaration &&
                (value.declaration as KSClassDeclaration).classKind == com.google.devtools.ksp.symbol.ClassKind.ENUM_ENTRY -> {
            if (resolver != null && logger != null) {
                val decl = value.declaration as KSClassDeclaration
                val enumConstantElement = KspEnumConstantElement(decl, resolver, logger)
                v?.visitEnumConstant(enumConstantElement, p)
            } else {
                error("Cannot visit enum constant without resolver and logger")
            }
        }
        // KSAnnotation representing nested annotation values (e.g., @Builder in builder() default)
        is KspAnnotationMirror -> {
            v?.visitAnnotation(value, p)
        }
        is KSAnnotation -> {
            if (resolver != null && logger != null) {
                val annotationMirror = KspAnnotationMirror(value, resolver, logger)
                v?.visitAnnotation(annotationMirror, p)
            } else {
                error("Cannot visit annotation without resolver and logger")
            }
        }
        // KSType representing class values (e.g., MappingControl.class)
        is KSType -> {
            if (resolver != null && logger != null) {
                val decl = value.declaration
                if (decl is KSClassDeclaration) {
                    val typeMirror = KspTypeMirror(KspClassTypeElement(decl, resolver, logger), resolver, logger)
                    v?.visitType(typeMirror, p)
                } else {
                    error("KSType declaration is not a KSClassDeclaration: ${decl::class.simpleName}")
                }
            } else {
                error("Cannot visit class type without resolver and logger")
            }
        }
        // KSClassDeclaration representing class values (non-enum)
        is KSClassDeclaration -> {
            if (resolver != null && logger != null) {
                val typeMirror = KspTypeMirror(KspClassTypeElement(value, resolver, logger), resolver, logger)
                v?.visitType(typeMirror, p)
            } else {
                error("Cannot visit class type without resolver and logger")
            }
        }

        else -> error("Unknown annotation value type: ${value?.javaClass?.name}")
    }

    override fun toString(): String = value?.toString() ?: "null"
}
