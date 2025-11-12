// ABOUTME: Adapts KSP's KSPLogger to implement javax.annotation.processing.Messager interface.
// ABOUTME: Routes diagnostic messages between KSP logging and Java annotation processing messager API.
/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSNode
import org.mapstruct.ksp.util.toKSNode
import javax.annotation.processing.Messager
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.Element
import javax.tools.Diagnostic

/**
 * Adapts KSP's [KSPLogger] to implement [Messager] interface.
 * Routes diagnostic messages from Java annotation processing API to KSP logging.
 */
class KspMessagerAdapter(
    private val logger: KSPLogger
) : Messager {

    override fun printMessage(kind: Diagnostic.Kind, msg: CharSequence) {
        when (kind) {
            Diagnostic.Kind.ERROR -> logger.error(msg.toString())
            Diagnostic.Kind.WARNING, Diagnostic.Kind.MANDATORY_WARNING -> logger.warn(msg.toString())
            Diagnostic.Kind.NOTE, Diagnostic.Kind.OTHER -> logger.info(msg.toString())
        }
    }

    override fun printMessage(kind: Diagnostic.Kind, msg: CharSequence, e: Element) {
        val ksNode = e.toKSNode()
        when (kind) {
            Diagnostic.Kind.ERROR -> logger.error(msg.toString(), ksNode)
            Diagnostic.Kind.WARNING, Diagnostic.Kind.MANDATORY_WARNING -> logger.warn(msg.toString(), ksNode)
            Diagnostic.Kind.NOTE, Diagnostic.Kind.OTHER -> logger.info(msg.toString(), ksNode)
        }
    }

    override fun printMessage(kind: Diagnostic.Kind, msg: CharSequence, e: Element, a: AnnotationMirror) {
        // KSP doesn't have direct annotation mirror support, fall back to element
        printMessage(kind, msg, e)
    }

    override fun printMessage(
        kind: Diagnostic.Kind,
        msg: CharSequence,
        e: Element,
        a: AnnotationMirror,
        v: AnnotationValue
    ) {
        // KSP doesn't have direct annotation value support, fall back to element
        printMessage(kind, msg, e)
    }
}
