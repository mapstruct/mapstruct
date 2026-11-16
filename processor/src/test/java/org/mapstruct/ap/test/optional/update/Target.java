/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.update;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Target {

    private Optional<String> optionalToOptional = Optional.of( "initial" );
    private Optional<String> nonOptionalToOptional = Optional.of( "initial" );

    @SuppressWarnings( "VisibilityModifier" )
    public Optional<String> publicOptionalToOptional = Optional.of( "initial" );
    @SuppressWarnings("VisibilityModifier")
    public Optional<String> publicNonOptionalToOptional = Optional.of( "initial" );

    public Optional<String> getOptionalToOptional() {
        return optionalToOptional;
    }

    public void setOptionalToOptional(Optional<String> optionalToOptional) {
        this.optionalToOptional = optionalToOptional;
    }

    public Optional<String> getNonOptionalToOptional() {
        return nonOptionalToOptional;
    }

    public void setNonOptionalToOptional(Optional<String> nonOptionalToOptional) {
        this.nonOptionalToOptional = nonOptionalToOptional;
    }

}
