package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import javax.annotation.processing.Messager
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.Element
import javax.tools.Diagnostic

class KspMessager(logger: KSPLogger) : Messager {
    override fun printMessage(kind: Diagnostic.Kind, msg: CharSequence) {
        TODO("Not yet implemented")
    }

    override fun printMessage(
        kind: Diagnostic.Kind,
        msg: CharSequence,
        e: Element
    ) {
        TODO("Not yet implemented")
    }

    override fun printMessage(
        kind: Diagnostic.Kind,
        msg: CharSequence,
        e: Element,
        a: AnnotationMirror
    ) {
        TODO("Not yet implemented")
    }

    override fun printMessage(
        kind: Diagnostic.Kind,
        msg: CharSequence,
        e: Element,
        a: AnnotationMirror,
        v: AnnotationValue
    ) {
        TODO("Not yet implemented")
    }

}
