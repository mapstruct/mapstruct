/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.differenttypes;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Source {

    private final Optional<SubType> constructorOptionalToOptional;
    private final Optional<SubType> constructorOptionalToNonOptional;
    private final SubType constructorNonOptionalToOptional;

    private Optional<SubType> optionalToOptional = Optional.empty();
    private Optional<SubType> optionalToNonOptional = Optional.empty();
    private SubType nonOptionalToOptional;

    @SuppressWarnings( "VisibilityModifier" )
    public Optional<SubType> publicOptionalToOptional = Optional.empty();
    @SuppressWarnings( "VisibilityModifier" )
    public Optional<SubType> publicOptionalToNonOptional = Optional.empty();
    @SuppressWarnings( "VisibilityModifier" )
    public SubType publicNonOptionalToOptional;

    public Source() {
        this( Optional.empty(), Optional.empty(), null );
    }

    public Source(Optional<SubType> constructorOptionalToOptional, Optional<SubType> constructorOptionalToNonOptional,
                  SubType constructorNonOptionalToOptional) {
        this.constructorOptionalToOptional = constructorOptionalToOptional;
        this.constructorOptionalToNonOptional = constructorOptionalToNonOptional;
        this.constructorNonOptionalToOptional = constructorNonOptionalToOptional;
    }

    public Optional<SubType> getConstructorOptionalToOptional() {
        return constructorOptionalToOptional;
    }

    public Optional<SubType> getConstructorOptionalToNonOptional() {
        return constructorOptionalToNonOptional;
    }

    public SubType getConstructorNonOptionalToOptional() {
        return constructorNonOptionalToOptional;
    }

    public Optional<SubType> getOptionalToOptional() {
        return optionalToOptional;
    }

    public void setOptionalToOptional(Optional<SubType> optionalToOptional) {
        this.optionalToOptional = optionalToOptional;
    }

    public Optional<SubType> getOptionalToNonOptional() {
        return optionalToNonOptional;
    }

    public void setOptionalToNonOptional(Optional<SubType> optionalToNonOptional) {
        this.optionalToNonOptional = optionalToNonOptional;
    }

    public SubType getNonOptionalToOptional() {
        return nonOptionalToOptional;
    }

    public void setNonOptionalToOptional(SubType nonOptionalToOptional) {
        this.nonOptionalToOptional = nonOptionalToOptional;
    }

    public static class SubType {

        private final String value;

        public SubType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

    }

}
