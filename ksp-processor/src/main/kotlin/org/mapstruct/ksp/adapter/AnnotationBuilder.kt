// ABOUTME: Helper class to build Java annotation proxy instances from KSP annotations
// ABOUTME: Uses dynamic proxies to create annotation instances that MapStruct processor can read
package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSType
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

object AnnotationBuilder {

    fun <A : Annotation> buildAnnotation(
        annotation: KSAnnotation,
        annotationType: Class<A>,
        resolver: Resolver,
        logger: KSPLogger
    ): A? {
        val annotationHandler = AnnotationInvocationHandler(annotation, annotationType, resolver, logger)

        @Suppress("UNCHECKED_CAST")
        return Proxy.newProxyInstance(
            annotationType.classLoader,
            arrayOf(annotationType),
            annotationHandler
        ) as? A
    }

    private class AnnotationInvocationHandler<A : Annotation>(
        private val annotation: KSAnnotation,
        private val annotationType: Class<A>,
        private val resolver: Resolver,
        private val logger: KSPLogger
    ) : InvocationHandler {

        private val argumentCache = mutableMapOf<String, Any?>()

        init {
            annotation.arguments.forEach { argument ->
                val name = argument.name?.asString()
                if (name != null) {
                    argumentCache[name] = convertValue(argument.value, null)
                }
            }
        }

        override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any? {
            return when (method.name) {
                "annotationType" -> annotationType
                "toString" -> "@${annotationType.name}${argumentCache}"
                "hashCode" -> argumentCache.hashCode()
                "equals" -> proxy === args?.get(0)
                else -> {
                    val value = argumentCache[method.name]
                    value ?: method.defaultValue
                }
            }
        }

        private fun convertValue(value: Any?, expectedType: Class<*>?): Any? {
            return when (value) {
                is KSType -> {
                    try {
                        val qualifiedName = value.declaration.qualifiedName?.asString()
                        if (qualifiedName != null) {
                            Class.forName(qualifiedName)
                        } else {
                            null
                        }
                    } catch (e: ClassNotFoundException) {
                        logger.warn("Could not load class for type: ${value.declaration.qualifiedName?.asString()}")
                        null
                    }
                }
                is List<*> -> {
                    if (expectedType?.isArray == true) {
                        val componentType = expectedType.componentType
                        val converted = value.map { convertValue(it, componentType) }
                        convertListToArray(converted, componentType)
                    } else {
                        value.map { convertValue(it, null) }
                    }
                }
                is com.google.devtools.ksp.symbol.KSAnnotation -> {
                    null
                }
                is Enum<*> -> value
                is String, is Boolean, is Byte, is Short, is Int, is Long, is Char, is Float, is Double -> value
                else -> value
            }
        }

        private fun convertListToArray(list: List<Any?>, componentType: Class<*>): Any {
            val array = java.lang.reflect.Array.newInstance(componentType, list.size)
            list.forEachIndexed { index, value ->
                java.lang.reflect.Array.set(array, index, value)
            }
            return array
        }
    }
}
