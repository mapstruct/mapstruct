/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping.nullcheckalways;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Source {

    private Optional<String> optionalToOptional;
    private Optional<String> optionalToNonOptional;
    private String nonOptionalToOptional;

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
