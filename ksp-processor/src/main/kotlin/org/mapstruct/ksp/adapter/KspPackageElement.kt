package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ElementVisitor
import javax.lang.model.element.Modifier
import javax.lang.model.element.Name
import javax.lang.model.element.PackageElement
import javax.lang.model.type.TypeMirror

class KspPackageElement(
    private val name: String,
    resolver: Resolver,
    logger: KSPLogger
) : PackageElement {
    override fun asType(): TypeMirror? {
        TODO("Not yet implemented")
    }

    override fun getQualifiedName(): Name? {
        return StringName(name)
    }

    override fun getSimpleName(): Name? {
        TODO("Not yet implemented")
    }

    override fun getEnclosedElements(): List<Element?>? {
        TODO("Not yet implemented")
    }

    override fun isUnnamed(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getEnclosingElement(): Element? {
        TODO("Not yet implemented")
    }

    override fun getKind(): ElementKind? {
        TODO("Not yet implemented")
    }

    override fun getModifiers(): Set<Modifier?>? {
        TODO("Not yet implemented")
    }

    override fun getAnnotationMirrors(): List<AnnotationMirror?>? {
        TODO("Not yet implemented")
    }

    override fun <A : Annotation?> getAnnotation(annotationType: Class<A?>?): A? {
        TODO("Not yet implemented")
    }

    override fun <A : Annotation?> getAnnotationsByType(annotationType: Class<A?>?): Array<out A?>? {
        TODO("Not yet implemented")
    }

    override fun <R : Any?, P : Any?> accept(
        v: ElementVisitor<R?, P?>?,
        p: P?
    ): R? {
        TODO("Not yet implemented")
    }

}
