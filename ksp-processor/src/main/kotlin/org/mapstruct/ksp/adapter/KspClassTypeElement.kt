package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSName
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ElementVisitor
import javax.lang.model.element.Modifier
import javax.lang.model.element.Name
import javax.lang.model.element.NestingKind
import javax.lang.model.element.TypeElement
import javax.lang.model.element.TypeParameterElement
import javax.lang.model.type.TypeMirror

class KspClassTypeElement(
    val declaration: KSClassDeclaration,
    private val resolver: Resolver,
    private val logger: KSPLogger
) : TypeElement {
    override fun asType(): TypeMirror {
        return KspTypeMirror(this, resolver, logger)
    }

    override fun getEnclosedElements(): List<Element?>? {
        TODO("Not yet implemented")
    }

    override fun getNestingKind(): NestingKind? {
        TODO("Not yet implemented")
    }

    override fun getQualifiedName(): Name? {
        val name: KSName = declaration.qualifiedName ?: return null
        return StringName(name.asString())
    }

    override fun getSimpleName(): Name {
        return StringName(declaration.simpleName.asString())
    }

    override fun getSuperclass(): TypeMirror? {
        TODO("Not yet implemented")
    }

    override fun getInterfaces(): List<TypeMirror?>? {
        TODO("Not yet implemented")
    }

    override fun getTypeParameters(): List<TypeParameterElement?>? {
        TODO("Not yet implemented")
    }

    override fun getEnclosingElement(): Element? {
        TODO("Not yet implemented")
    }

    override fun getKind(): ElementKind {
        return ElementKind.CLASS
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

