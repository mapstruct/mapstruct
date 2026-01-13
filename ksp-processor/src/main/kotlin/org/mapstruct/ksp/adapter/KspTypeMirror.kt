package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSTypeParameter
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.Element
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.type.TypeVisitor

class KspTypeMirror(
    val element: KspClassTypeElement,
    private val resolver: Resolver,
    private val logger: KSPLogger
) : DeclaredType {

    override fun getKind(): TypeKind {
        // KspTypeMirror represents DeclaredType (class/interface types), not primitives
        // Even for Kotlin's Boolean/Int/etc., when accessed as java.lang.Boolean/java.lang.Integer,
        // they should be treated as DECLARED types (wrapper types), not primitives
        return TypeKind.DECLARED
    }

    override fun asElement(): Element = element

    override fun getEnclosingType(): TypeMirror? {
        TODO("Not yet implemented")
    }

    override fun getTypeArguments(): List<TypeMirror> {
        return element.declaration.typeParameters.map { typeParameter: KSTypeParameter ->
            KspTypeVar(typeParameter, resolver, logger)
        }
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

    override fun <R, P> accept(v: TypeVisitor<R, P>, p: P): R {
        return v.visitDeclared(this, p)
    }

    override fun equals(other: Any?): Boolean {
        // Use element.equals() which compares by qualified name, not reference equality
        return other is KspTypeMirror && element == other.element
    }

    override fun hashCode(): Int = element.hashCode()

    override fun toString(): String = "KspTypeMirror[${element.declaration}]"
}

