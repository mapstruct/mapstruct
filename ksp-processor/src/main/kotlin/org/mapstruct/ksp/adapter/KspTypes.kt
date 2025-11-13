package org.mapstruct.ksp.adapter

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.type.ArrayType
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.ExecutableType
import javax.lang.model.type.NoType
import javax.lang.model.type.NullType
import javax.lang.model.type.PrimitiveType
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.type.WildcardType
import javax.lang.model.util.Types

class KspTypes(
    environment: SymbolProcessorEnvironment,
    private val resolver: Resolver,
    private val logger: KSPLogger
) : Types {
    override fun asElement(t: TypeMirror): Element {
        TODO("Not yet implemented")
    }

    override fun isSameType(
        t1: TypeMirror,
        t2: TypeMirror
    ): Boolean = when {
        else -> TODO()
    }

    override fun isSubtype(
        t1: TypeMirror,
        t2: TypeMirror
    ): Boolean = when {
        t1 is KspTypeMirror && t2 is KspTypeMirror -> {
            t2.element.declaration.asStarProjectedType().isAssignableFrom(t1.element.declaration.asStarProjectedType())
        }
        t1 is KspTypeVar && t2 is KspTypeVar -> {
            TODO("TypeVar comparison not yet implemented")
        }
        t1 is KspTypeVar && t2 is KspTypeMirror -> {
            TODO()
        }
        else -> {
            error("Unsupported type subtype: ${t1::class.simpleName}:$t1 vs ${t2::class.simpleName}:$t2")
        }
    }

    override fun isAssignable(
        t1: TypeMirror,
        t2: TypeMirror
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun contains(
        t1: TypeMirror,
        t2: TypeMirror
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun isSubsignature(
        m1: ExecutableType,
        m2: ExecutableType
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun directSupertypes(t: TypeMirror): List<TypeMirror> {
        TODO("Not yet implemented")
    }

    override fun erasure(t: TypeMirror): TypeMirror {
        return when (t) {
            is KspTypeMirror -> t
            is KspTypeVar -> t
            else -> error("TypeMirror is not a KspTypeMirror: $t")
        }
    }

    override fun boxedClass(p: PrimitiveType): TypeElement {
        TODO("Not yet implemented")
    }

    override fun unboxedType(t: TypeMirror): PrimitiveType {
        TODO("Not yet implemented")
    }

    override fun capture(t: TypeMirror): TypeMirror {
        TODO("Not yet implemented")
    }

    override fun getPrimitiveType(kind: TypeKind): PrimitiveType {
        TODO("Not yet implemented")
    }

    override fun getNullType(): NullType {
        TODO("Not yet implemented")
    }

    override fun getNoType(kind: TypeKind): NoType {
        TODO("Not yet implemented")
    }

    override fun getArrayType(componentType: TypeMirror): ArrayType {
        TODO("Not yet implemented")
    }

    override fun getWildcardType(
        extendsBound: TypeMirror,
        superBound: TypeMirror
    ): WildcardType {
        TODO("Not yet implemented")
    }

    override fun getDeclaredType(
        typeElem: TypeElement,
        vararg typeArgs: TypeMirror
    ): DeclaredType {
        TODO("Not yet implemented")
    }

    override fun getDeclaredType(
        containing: DeclaredType,
        typeElem: TypeElement,
        vararg typeArgs: TypeMirror
    ): DeclaredType {
        TODO("Not yet implemented")
    }

    override fun asMemberOf(
        containing: DeclaredType,
        element: Element
    ): TypeMirror {
        TODO("Not yet implemented")
    }

}
