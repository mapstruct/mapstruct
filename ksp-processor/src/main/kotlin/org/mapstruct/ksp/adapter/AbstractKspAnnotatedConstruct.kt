// ABOUTME: Abstract base class providing default implementation for AnnotatedConstruct methods
// ABOUTME: Eliminates duplication of annotation-related methods across KSP adapter classes
package org.mapstruct.ksp.adapter

import javax.lang.model.AnnotatedConstruct
import javax.lang.model.element.AnnotationMirror

/**
 * Abstract base class for KSP adapters that implement [AnnotatedConstruct].
 * Provides default "no annotations" implementation that can be overridden by subclasses.
 */
abstract class AbstractKspAnnotatedConstruct : AnnotatedConstruct {

    override fun getAnnotationMirrors(): List<AnnotationMirror> = emptyList()

    override fun <A : Annotation?> getAnnotation(annotationType: Class<A?>?): A? = null

    override fun <A : Annotation?> getAnnotationsByType(annotationType: Class<A?>?): Array<A?> {
        @Suppress("UNCHECKED_CAST")
        return arrayOfNulls<Annotation>(0) as Array<A?>
    }
}
