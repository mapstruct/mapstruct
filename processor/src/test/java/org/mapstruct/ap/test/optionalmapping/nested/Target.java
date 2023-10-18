/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping.nested;

import java.util.Optional;

public class Target {

    private String optionalToNonOptional;
    private Optional<String> optionalToOptional;
    private String nonOptionalToNonOptional;
    private Optional<String> nonOptionalToOptional;

    public String getOptionalToNonOptional() {
        return optionalToNonOptional;
    }

    public void setOptionalToNonOptional(String optionalToNonOptional) {
        this.optionalToNonOptional = optionalToNonOptional;
    }

    public Optional<String> getOptionalToOptional() {
        return optionalToOptional;
    }

    public void setOptionalToOptional(Optional<String> optionalToOptional) {
        this.optionalToOptional = optionalToOptional;
    }

    public String getNonOptionalToNonOptional() {
        return nonOptionalToNonOptional;
    }

    public void setNonOptionalToNonOptional(String nonOptionalToNonOptional) {
        this.nonOptionalToNonOptional = nonOptionalToNonOptional;
    }

    public Optional<String> getNonOptionalToOptional() {
        return nonOptionalToOptional;
    }

    public void setNonOptionalToOptional(Optional<String> nonOptionalToOptional) {
        this.nonOptionalToOptional = nonOptionalToOptional;
    }
}
