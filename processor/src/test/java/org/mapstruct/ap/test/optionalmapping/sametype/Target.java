/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping.sametype;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Target {

    private final Optional<String> constructorOptionalToOptional;
    private final String constructorOptionalToNonOptional;
    private final Optional<String> constructorNonOptionalToOptional;

    private Optional<String> optionalToOptional;
    private String optionalToNonOptional;
    private Optional<String> nonOptionalToOptional;

    @SuppressWarnings( "VisibilityModifier" )
    public Optional<String> publicOptionalToOptional;
    @SuppressWarnings( "VisibilityModifier" )
    public String publicOptionalToNonOptional;
    @SuppressWarnings( "VisibilityModifier" )
    public Optional<String> publicNonOptionalToOptional;

    public Target(Optional<String> constructorOptionalToOptional, String constructorOptionalToNonOptional,
                  Optional<String> constructorNonOptionalToOptional) {
        this.constructorOptionalToOptional = constructorOptionalToOptional;
        this.constructorOptionalToNonOptional = constructorOptionalToNonOptional;
        this.constructorNonOptionalToOptional = constructorNonOptionalToOptional;
    }

    public Optional<String> getConstructorOptionalToOptional() {
        return constructorOptionalToOptional;
    }

    public String getConstructorOptionalToNonOptional() {
        return constructorOptionalToNonOptional;
    }

    public Optional<String> getConstructorNonOptionalToOptional() {
        return constructorNonOptionalToOptional;
    }

    public Optional<String> getOptionalToOptional() {
        return optionalToOptional;
    }

    public void setOptionalToOptional(Optional<String> optionalToOptional) {
        this.optionalToOptional = optionalToOptional;
    }

    public String getOptionalToNonOptional() {
        return optionalToNonOptional;
    }

    public void setOptionalToNonOptional(String optionalToNonOptional) {
        this.optionalToNonOptional = optionalToNonOptional;
    }

    public Optional<String> getNonOptionalToOptional() {
        return nonOptionalToOptional;
    }

    public void setNonOptionalToOptional(Optional<String> nonOptionalToOptional) {
        this.nonOptionalToOptional = nonOptionalToOptional;
    }
}
