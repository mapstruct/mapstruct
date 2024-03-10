/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping.sametype;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Source {

    private final Optional<String> constructorOptionalToOptional;
    private final Optional<String> constructorOptionalToNonOptional;
    private final String constructorNonOptionalToOptional;

    private Optional<String> optionalToOptional;
    private Optional<String> optionalToNonOptional;
    private String nonOptionalToOptional;

    @SuppressWarnings( "VisibilityModifier" )
    public Optional<String> publicOptionalToOptional;
    @SuppressWarnings( "VisibilityModifier" )
    public Optional<String> publicOptionalToNonOptional;
    @SuppressWarnings( "VisibilityModifier" )
    public String publicNonOptionalToOptional;

    public Source(Optional<String> constructorOptionalToOptional, Optional<String> constructorOptionalToNonOptional,
                  String constructorNonOptionalToOptional) {
        this.constructorOptionalToOptional = constructorOptionalToOptional;
        this.constructorOptionalToNonOptional = constructorOptionalToNonOptional;
        this.constructorNonOptionalToOptional = constructorNonOptionalToOptional;
    }

    public Optional<String> getConstructorOptionalToOptional() {
        return constructorOptionalToOptional;
    }

    public Optional<String> getConstructorOptionalToNonOptional() {
        return constructorOptionalToNonOptional;
    }

    public String getConstructorNonOptionalToOptional() {
        return constructorNonOptionalToOptional;
    }

    public Optional<String> getOptionalToOptional() {
        return optionalToOptional;
    }

    public void setOptionalToOptional(Optional<String> optionalToOptional) {
        this.optionalToOptional = optionalToOptional;
    }

    public Optional<String> getOptionalToNonOptional() {
        return optionalToNonOptional;
    }

    public void setOptionalToNonOptional(Optional<String> optionalToNonOptional) {
        this.optionalToNonOptional = optionalToNonOptional;
    }

    public String getNonOptionalToOptional() {
        return nonOptionalToOptional;
    }

    public void setNonOptionalToOptional(String nonOptionalToOptional) {
        this.nonOptionalToOptional = nonOptionalToOptional;
    }
}
