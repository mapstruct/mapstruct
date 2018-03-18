/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.internal.model.common;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import org.mapstruct.ap.spi.BuilderInfo;

/**
 * @author Filip Hrisafov
 */
public class BuilderType {

    private final Type builder;
    private final Type owningType;
    private final Type buildingType;
    private final ExecutableElement builderCreationMethod;
    private final ExecutableElement buildMethod;

    private BuilderType(
        Type builder,
        Type owningType,
        Type buildingType,
        ExecutableElement builderCreationMethod,
        ExecutableElement buildMethod
    ) {
        this.builder = builder;
        this.owningType = owningType;
        this.buildingType = buildingType;
        this.builderCreationMethod = builderCreationMethod;
        this.buildMethod = buildMethod;
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
     * The name of the method that needs to be invoked on the builder to create the type being built.
     *
     * @return the name of the method that needs to be invoked on the type that is being built
     */
    public String getBuildMethod() {
        return buildMethod.getSimpleName().toString();
    }

    public BuilderInfo asBuilderInfo() {
        return new BuilderInfo.Builder()
            .builderCreationMethod( this.builderCreationMethod )
            .buildMethod( this.buildMethod )
            .build();
    }

    public static BuilderType create(BuilderInfo builderInfo, Type typeToBuild, TypeFactory typeFactory,
        Types typeUtils) {
        if ( builderInfo == null ) {
            return null;
        }
        ExecutableElement buildMethod = builderInfo.getBuildMethod();
        if ( !typeUtils.isAssignable( buildMethod.getReturnType(), typeToBuild.getTypeMirror() ) ) {
            //TODO throw error
            throw new IllegalArgumentException( "Build return type is not assignable" );
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


        return new BuilderType(
            builder,
            owner,
            typeToBuild,
            builderCreationMethod,
            buildMethod
        );
    }
}
