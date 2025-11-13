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
    private val environment: SymbolProcessorEnvironment,
    private val resolver: Resolver,
) : Types {
    private val logger: KSPLogger = environment.logger
    override fun asElement(t: TypeMirror): Element {
        return when (t) {
            is KspTypeMirror -> t.element
            is KspTypeVar -> throw IllegalArgumentException("Cannot get element from type variable")
            else -> error("Unsupported TypeMirror type: ${t::class.simpleName}")
        }
    }

    override fun isSameType(
        t1: TypeMirror,
        t2: TypeMirror
    ): Boolean = when {
        t1 === t2 -> true
        t1 is KspTypeMirror && t2 is KspTypeMirror -> {
            val qn1 = t1.element.qualifiedName?.toString()
            val qn2 = t2.element.qualifiedName?.toString()
            qn1 != null && qn2 != null && qn1 == qn2
        }
        t1 is KspTypeVar && t2 is KspTypeVar -> {
            t1.param.name.asString() == t2.param.name.asString()
        }
        else -> false
    }

    override fun isSubtype(
        t1: TypeMirror,
        t2: TypeMirror
    ): Boolean = when {
        isSameType(t1, t2) -> true
        t1 is KspPrimitiveType || t2 is KspPrimitiveType -> {
            // Primitive types are only subtypes of themselves (handled by isSameType above)
            false
        }
        t1 is KspTypeMirror && t2 is KspTypeMirror -> {
            t2.element.declaration.asStarProjectedType().isAssignableFrom(t1.element.declaration.asStarProjectedType())
        }
        t1 is KspTypeVar && t2 is KspTypeVar -> {
            isSameType(t1, t2)
        }
        t1 is KspTypeVar && t2 is KspTypeMirror -> {
            val upperBound = t1.param.bounds.firstOrNull()
            when {
                upperBound != null -> {
                    val qualifiedName = upperBound.resolve().declaration.qualifiedName?.asString()
                    when {
                        qualifiedName != null -> {
                            val upperBoundElement = resolver.getClassDeclarationByName(resolver.getKSNameFromString(qualifiedName))
                            when {
                                upperBoundElement != null -> {
                                    val upperBoundMirror = KspTypeMirror(KspClassTypeElement(upperBoundElement, resolver, logger), resolver, logger)
                                    isSubtype(upperBoundMirror, t2)
                                }
                                else -> false
                            }
                        }
                        else -> false
                    }
                }
                else -> false
            }
        }
        else -> {
            error("Unsupported type subtype: ${t1::class.simpleName}:$t1 vs ${t2::class.simpleName}:$t2")
        }
    }

    override fun isAssignable(
        t1: TypeMirror,
        t2: TypeMirror
    ): Boolean {
        return when {
            isSameType(t1, t2) -> true
            t1 is KspTypeMirror && t2 is KspTypeMirror -> {
                t2.element.declaration.asStarProjectedType().isAssignableFrom(t1.element.declaration.asStarProjectedType())
            }
            else -> isSubtype(t1, t2)
        }
    }

    override fun contains(
        t1: TypeMirror,
        t2: TypeMirror
    ): Boolean {
        return isSameType(t1, t2) || isSubtype(t2, t1)
    }

    override fun isSubsignature(
        m1: ExecutableType,
        m2: ExecutableType
    ): Boolean {
        return false
    }

    override fun directSupertypes(t: TypeMirror): List<TypeMirror> {
        return when (t) {
            is KspTypeMirror -> {
                val supertypes = mutableListOf<TypeMirror>()
                val declaration = t.element.declaration

                declaration.superTypes.forEach { superTypeRef ->
                    val superType = superTypeRef.resolve()
                    val superDeclaration = superType.declaration
                    if (superDeclaration is com.google.devtools.ksp.symbol.KSClassDeclaration) {
                        supertypes.add(
                            KspTypeMirror(
                                KspClassTypeElement(superDeclaration, resolver, logger),
                                resolver,
                                logger
                            )
                        )
                    }
                }
                supertypes
            }
            else -> emptyList()
        }
    }

    override fun erasure(t: TypeMirror): TypeMirror {
        return when (t) {
            is KspTypeMirror -> t
            is KspTypeVar -> t
            is KspPrimitiveType -> t
            is KspArrayType -> t
            is KspNullType -> t
            is KspNoType -> t
            is KspWildcardType -> t
            else -> error("TypeMirror is not a KspTypeMirror: $t")
        }
    }

    override fun boxedClass(p: PrimitiveType): TypeElement {
        val boxedClassName = when (p.kind) {
            TypeKind.BOOLEAN -> "java.lang.Boolean"
            TypeKind.BYTE -> "java.lang.Byte"
            TypeKind.SHORT -> "java.lang.Short"
            TypeKind.INT -> "java.lang.Integer"
            TypeKind.LONG -> "java.lang.Long"
            TypeKind.CHAR -> "java.lang.Character"
            TypeKind.FLOAT -> "java.lang.Float"
            TypeKind.DOUBLE -> "java.lang.Double"
            else -> error("Not a primitive type: ${p.kind}")
        }
        val boxedDeclaration = resolver.getClassDeclarationByName(resolver.getKSNameFromString(boxedClassName))
            ?: error("Could not find boxed class for $boxedClassName")
        return KspClassTypeElement(boxedDeclaration, resolver, logger)
    }

    override fun unboxedType(t: TypeMirror): PrimitiveType {
        if (t !is KspTypeMirror) {
            error("Cannot unbox non-KspTypeMirror: ${t::class.simpleName}")
        }
        val qualifiedName = t.element.qualifiedName?.toString()
        val primitiveKind = when (qualifiedName) {
            "java.lang.Boolean" -> TypeKind.BOOLEAN
            "java.lang.Byte" -> TypeKind.BYTE
            "java.lang.Short" -> TypeKind.SHORT
            "java.lang.Integer" -> TypeKind.INT
            "java.lang.Long" -> TypeKind.LONG
            "java.lang.Character" -> TypeKind.CHAR
            "java.lang.Float" -> TypeKind.FLOAT
            "java.lang.Double" -> TypeKind.DOUBLE
            else -> error("Not a boxed type: $qualifiedName")
        }
        return getPrimitiveType(primitiveKind)
    }

    override fun capture(t: TypeMirror): TypeMirror {
        return t
    }

    override fun getPrimitiveType(kind: TypeKind): PrimitiveType {
        return KspPrimitiveType(kind)
    }

    override fun getNullType(): NullType {
        return KspNullType()
    }

    override fun getNoType(kind: TypeKind): NoType {
        return KspNoType(kind)
    }

    override fun getArrayType(componentType: TypeMirror): ArrayType {
        return KspArrayType(componentType)
    }

    override fun getWildcardType(
        extendsBound: TypeMirror?,
        superBound: TypeMirror?
    ): WildcardType {
        return KspWildcardType(extendsBound, superBound)
    }

    override fun getDeclaredType(
        typeElem: TypeElement,
        vararg typeArgs: TypeMirror
    ): DeclaredType {
        if (typeElem !is KspClassTypeElement) {
            error("TypeElement must be KspClassTypeElement")
        }
        return KspTypeMirror(typeElem, resolver, logger)
    }

    override fun getDeclaredType(
        containing: DeclaredType,
        typeElem: TypeElement,
        vararg typeArgs: TypeMirror
    ): DeclaredType {
        return getDeclaredType(typeElem, *typeArgs)
    }

    override fun asMemberOf(
        containing: DeclaredType,
        element: Element
    ): TypeMirror {
        return when (element) {
            is KspClassTypeElement -> element.asType()
            is KspExecutableElement -> {
                // For ExecutableElement, return an ExecutableType representing the method signature
                KspExecutableType(element, resolver, logger)
            }
            is KspVariableElement -> {
                // For VariableElement (fields/parameters), return the variable's type
                element.asType()
            }
            else -> error("asMemberOf not implemented for element type: ${element::class.simpleName}")
        }
    }

}
