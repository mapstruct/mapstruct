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

class KspTypeMirror(val element: KspClassTypeElement, private val resolver: Resolver, private val logger: KSPLogger) :
    DeclaredType {

    override fun getKind(): TypeKind {
        val builtins = resolver.builtIns
        val ksType = element.declaration.asStarProjectedType()
        return when (ksType) {
            builtins.booleanType -> TypeKind.BOOLEAN
            builtins.byteType -> TypeKind.BYTE
            builtins.shortType -> TypeKind.SHORT
            builtins.intType -> TypeKind.INT
            builtins.longType -> TypeKind.LONG
            builtins.charType -> TypeKind.CHAR
            builtins.floatType -> TypeKind.FLOAT
            builtins.doubleType -> TypeKind.DOUBLE
            builtins.unitType -> TypeKind.VOID
            builtins.anyType -> TypeKind.DECLARED
            else -> TypeKind.DECLARED
        }
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

    override fun <R : Any?, P : Any?> accept(v: TypeVisitor<R?, P?>?, p: P?): R? {
        TODO("Not yet implemented")
    }

    override fun toString(): String = "KspTypeMirror[${element.declaration}]"
}

