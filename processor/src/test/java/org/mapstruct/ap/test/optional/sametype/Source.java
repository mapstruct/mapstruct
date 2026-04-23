/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.sametype;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Source {

    private final Optional<String> constructorOptionalToOptional;
    private final Optional<String> constructorOptionalToNonOptional;
    private final String constructorNonOptionalToOptional;

    private Optional<String> optionalToOptional = Optional.empty();
    private Optional<String> optionalToNonOptional = Optional.empty();
    private String nonOptionalToOptional;

    @SuppressWarnings( "VisibilityModifier" )
    public Optional<String> publicOptionalToOptional = Optional.empty();
    @SuppressWarnings( "VisibilityModifier" )
    public Optional<String> publicOptionalToNonOptional = Optional.empty();
    @SuppressWarnings( "VisibilityModifier" )
    public String publicNonOptionalToOptional;

    public Source() {
        this( Optional.empty(), Optional.empty(), null );
    }

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
