/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.update;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Source {

    private Optional<String> optionalToOptional = Optional.empty();
    private String nonOptionalToOptional;

    @SuppressWarnings( "VisibilityModifier" )
    public Optional<String> publicOptionalToOptional = Optional.empty();
    @SuppressWarnings( "VisibilityModifier" )
    public String publicNonOptionalToOptional;

    public Optional<String> getOptionalToOptional() {
        return optionalToOptional;
    }

    public void setOptionalToOptional(Optional<String> optionalToOptional) {
        this.optionalToOptional = optionalToOptional;
    }

    public String getNonOptionalToOptional() {
        return nonOptionalToOptional;
    }

    public void setNonOptionalToOptional(String nonOptionalToOptional) {
        this.nonOptionalToOptional = nonOptionalToOptional;
    }

}
