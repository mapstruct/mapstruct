/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

import java.util.Collection;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.util.TypeUtils;
import org.mapstruct.ap.spi.BuilderInfo;

/**
 * Represents the information about a builder.
 * How it can be constructed, the type it is building etc.
 *
 * @author Filip Hrisafov
 */
public class BuilderType {

    private final Type builder;
    private final Type owningType;
    private final Type buildingType;
    private final ExecutableElement builderCreationMethod;
    private final Collection<ExecutableElement> buildMethods;

    private BuilderType(
        Type builder,
        Type owningType,
        Type buildingType,
        ExecutableElement builderCreationMethod,
        Collection<ExecutableElement> buildMethods
    ) {
        this.builder = builder;
        this.owningType = owningType;
        this.buildingType = buildingType;
        this.builderCreationMethod = builderCreationMethod;
        this.buildMethods = buildMethods;
    }

    /**
     * The type of the builder itself.
     *
     * @return the type for the builder
     */
    public Type getBuilder() {
        return builder;
    }

    /**
     * The owning type of the builder, this can be the builder itself, the type that is build by the builder or some
     * other type.
     *
     * @return the owning type
     */
    public Type getOwningType() {
        return owningType;
    }

    /**
     * The type that is being built by the builder.
     *
     * @return the type that is being built
     */
    public Type getBuildingType() {
        return buildingType;
    }

    /**
     * The creation method for the builder.
     *
     * @return the creation method for the builder
     */
    public ExecutableElement getBuilderCreationMethod() {
        return builderCreationMethod;
    }

    /**
     * The build methods that can be invoked to create the type being built.
     * @return the build methods that can be invoked to create the type being built
     */
    public Collection<ExecutableElement> getBuildMethods() {
        return buildMethods;
    }

    public static BuilderType create(BuilderInfo builderInfo, Type typeToBuild, TypeFactory typeFactory,
        TypeUtils typeUtils) {
        if ( builderInfo == null ) {
            return null;
        }

        Type builder = typeFactory.getType( builderInfo.getBuilderCreationMethod().getReturnType() );
        ExecutableElement builderCreationMethod = builderInfo.getBuilderCreationMethod();
        Type owner;
        TypeMirror builderCreationOwner = builderCreationMethod.getEnclosingElement().asType();
        if ( typeUtils.isSameType( builderCreationOwner, typeToBuild.getTypeMirror() ) ) {
            owner = typeToBuild;
        }
        else if ( typeUtils.isSameType( builder.getTypeMirror(), builderCreationOwner ) ) {
            owner = builder;
        }
        else {
            owner = typeFactory.getType( builderCreationOwner );
        }

        // When the builderCreationMethod is constructor, its return type is Void. In this case the
        // builder type should be the owner type.
        if ( builderInfo.getBuilderCreationMethod().getKind() == ElementKind.CONSTRUCTOR ) {
            builder = owner;
        }

        builder = resolveTypeParameters( builder, typeToBuild, builderInfo.getBuildMethods(), typeFactory, typeUtils );

        return new BuilderType(
            builder,
            owner,
            typeToBuild,
            builderCreationMethod,
            builderInfo.getBuildMethods()
        );
    }

    /**
     * Resolves the type parameters of a generic builder against the type that needs to be built.
     * <p>
     * For example, when building {@code Record<String>} with a {@code RecordBuilder<T>} whose build method
     * returns {@code Record<T>}, the resolved builder type is {@code RecordBuilder<String>}.
     *
     * @param builder the builder type
     * @param typeToBuild the type that needs to be built
     * @param buildMethods the build methods of the builder
     * @param typeFactory the type factory
     * @param typeUtils the type utils
     *
     * @return the builder with its type parameters resolved, or the given builder if they cannot be resolved
     */
    private static Type resolveTypeParameters(Type builder, Type typeToBuild,
        Collection<ExecutableElement> buildMethods, TypeFactory typeFactory, TypeUtils typeUtils) {
        if ( builder.getTypeParameters().isEmpty() || typeToBuild.getTypeParameters().isEmpty()
            || !( builder.getTypeMirror() instanceof DeclaredType ) ) {
            return builder;
        }

        for ( ExecutableElement buildMethod : buildMethods ) {
            TypeMirror buildMethodType = typeUtils.asMemberOf( (DeclaredType) builder.getTypeMirror(), buildMethod );
            if ( !( buildMethodType instanceof ExecutableType ) ) {
                continue;
            }
            TypeMirror builtType = ( (ExecutableType) buildMethodType ).getReturnType();
            TypeMirror erasedTypeToBuild = typeUtils.erasure( typeToBuild.getTypeMirror() );
            if ( typeUtils.isSameType( typeUtils.erasure( builtType ), erasedTypeToBuild ) ) {
                return builder.resolveGenericTypeParameters( typeToBuild, typeFactory.getType( builtType ) );
            }
        }

        return builder;
    }
}
