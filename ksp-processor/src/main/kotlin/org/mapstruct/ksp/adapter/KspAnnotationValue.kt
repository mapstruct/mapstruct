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

    override fun <R : Any?, P : Any?> accept(v: AnnotationValueVisitor<R?, P?>?, p: P?): R? {
        return when {
            value is Boolean -> v?.visitBoolean(value, p)
            value is Byte -> v?.visitByte(value, p)
            value is Char -> v?.visitChar(value, p)
            value is Double -> v?.visitDouble(value, p)
            value is Float -> v?.visitFloat(value, p)
            value is Int -> v?.visitInt(value, p)
            value is Long -> v?.visitLong(value, p)
            value is Short -> v?.visitShort(value, p)
            value is String -> v?.visitString(value, p)
            value is List<*> -> v?.visitArray(value as List<AnnotationValue>, p)
            // KSP represents enum entries as KSClassDeclaration
            value is KSClassDeclaration && value.classKind == com.google.devtools.ksp.symbol.ClassKind.ENUM_ENTRY -> {
                if (resolver != null && logger != null) {
                    val enumConstantElement = KspEnumConstantElement(value, resolver, logger)
                    v?.visitEnumConstant(enumConstantElement, p)
                } else {
                    error("Cannot visit enum constant without resolver and logger")
                }
            }
            // KSType representing enum values
            value is KSType && value.declaration is KSClassDeclaration &&
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
            value is KSAnnotation -> {
                if (resolver != null && logger != null) {
                    val annotationMirror = KspAnnotationMirror(value, resolver, logger)
                    v?.visitAnnotation(annotationMirror, p)
                } else {
                    error("Cannot visit annotation without resolver and logger")
                }
            }
            // KSType representing class values (e.g., MappingControl.class)
            value is KSType -> {
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
            value is KSClassDeclaration -> {
                if (resolver != null && logger != null) {
                    val typeMirror = KspTypeMirror(KspClassTypeElement(value, resolver, logger), resolver, logger)
                    v?.visitType(typeMirror, p)
                } else {
                    error("Cannot visit class type without resolver and logger")
                }
            }
            else -> error("Unknown annotation value type: ${value?.javaClass?.name}")
        }
    }

    override fun toString(): String = value?.toString() ?: "null"
}
