package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import javax.annotation.processing.Messager
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.Element
import javax.tools.Diagnostic

class KspMessager(private val logger: KSPLogger) : Messager {
    override fun printMessage(kind: Diagnostic.Kind, msg: CharSequence) {
        printMessage(kind, msg.toString())
    }

    override fun printMessage(
        kind: Diagnostic.Kind,
        msg: CharSequence,
        e: Element
    ) {
        val message = buildString {
            append(msg)
            append(" [element: ")
            append(e.toString())
            append("]")
        }
        printMessage(kind, message)
    }

    override fun printMessage(
        kind: Diagnostic.Kind,
        msg: CharSequence,
        e: Element,
        a: AnnotationMirror
    ) {
        val message = buildString {
            append(msg)
            append(" [element: ")
            append(e.toString())
            append(", annotation: ")
            append(a.toString())
            append("]")
        }
        printMessage(kind, message)
    }

    override fun printMessage(
        kind: Diagnostic.Kind,
        msg: CharSequence,
        e: Element,
        a: AnnotationMirror,
        v: AnnotationValue?
    ) {
        val message = buildString {
            append(msg)
            append(" [element: ")
            append(e.toString())
            append(", annotation: ")
            append(a.toString())
            append(", value: ")
            append(v.toString())
            append("]")
        }
        printMessage(kind, message)
    }

    private fun printMessage(kind: Diagnostic.Kind, message: String) {
        when (kind) {
            Diagnostic.Kind.ERROR -> logger.error(message)
            Diagnostic.Kind.WARNING, Diagnostic.Kind.MANDATORY_WARNING -> logger.warn(message)
            Diagnostic.Kind.NOTE, Diagnostic.Kind.OTHER -> logger.info(message)
        }
    }

}
