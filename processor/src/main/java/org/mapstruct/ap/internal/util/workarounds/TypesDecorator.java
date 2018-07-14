/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.workarounds;

import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.version.VersionInformation;

/**
 * Replaces the usage of {@link Types} within MapStruct by delegating to the original implementation or to our specific
 * workarounds if necessary.
 *
 * @author Andreas Gudian
 */
public class TypesDecorator implements Types {
    private final Types delegate;
    private final ProcessingEnvironment processingEnv;
    private final VersionInformation versionInformation;

    public TypesDecorator(ProcessingEnvironment processingEnv, VersionInformation versionInformation) {
        this.delegate = processingEnv.getTypeUtils();
        this.processingEnv = processingEnv;
        this.versionInformation = versionInformation;
    }

    @Override
    public Element asElement(TypeMirror t) {
        return delegate.asElement( t );
    }

    @Override
    public boolean isSameType(TypeMirror t1, TypeMirror t2) {
        return delegate.isSameType( t1, t2 );
    }

    @Override
    public boolean isSubtype(TypeMirror t1, TypeMirror t2) {
        return SpecificCompilerWorkarounds.isSubtype( delegate, t1, t2 );
    }

    @Override
    public boolean isAssignable(TypeMirror t1, TypeMirror t2) {
        return SpecificCompilerWorkarounds.isAssignable( delegate, t1, t2 );
    }

    @Override
    public boolean contains(TypeMirror t1, TypeMirror t2) {
        return delegate.contains( t1, t2 );
    }

    @Override
    public boolean isSubsignature(ExecutableType m1, ExecutableType m2) {
        return delegate.isSubsignature( m1, m2 );
    }

    @Override
    public List<? extends TypeMirror> directSupertypes(TypeMirror t) {
        return delegate.directSupertypes( t );
    }

    @Override
    public TypeMirror erasure(TypeMirror t) {
        return SpecificCompilerWorkarounds.erasure( delegate, t );
    }

    @Override
    public TypeElement boxedClass(PrimitiveType p) {
        return delegate.boxedClass( p );
    }

    @Override
    public PrimitiveType unboxedType(TypeMirror t) {
        return delegate.unboxedType( t );
    }

    @Override
    public TypeMirror capture(TypeMirror t) {
        return delegate.capture( t );
    }

    @Override
    public PrimitiveType getPrimitiveType(TypeKind kind) {
        return delegate.getPrimitiveType( kind );
    }

    @Override
    public NullType getNullType() {
        return delegate.getNullType();
    }

    @Override
    public NoType getNoType(TypeKind kind) {
        return delegate.getNoType( kind );
    }

    @Override
    public ArrayType getArrayType(TypeMirror componentType) {
        return delegate.getArrayType( componentType );
    }

    @Override
    public WildcardType getWildcardType(TypeMirror extendsBound, TypeMirror superBound) {
        return delegate.getWildcardType( extendsBound, superBound );
    }

    @Override
    public DeclaredType getDeclaredType(TypeElement typeElem, TypeMirror... typeArgs) {
        return delegate.getDeclaredType( typeElem, typeArgs );
    }

    @Override
    public DeclaredType getDeclaredType(DeclaredType containing, TypeElement typeElem, TypeMirror... typeArgs) {
        return delegate.getDeclaredType( containing, typeElem, typeArgs );
    }

    @Override
    public TypeMirror asMemberOf(DeclaredType containing, Element element) {
        return SpecificCompilerWorkarounds.asMemberOf(
            delegate,
            processingEnv,
            versionInformation,
            containing,
            element );
    }
}
