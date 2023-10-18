/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping.nullcheckalways;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Target {

    private boolean optionalToOptionalCalled;
    private boolean optionalToNonOptionalCalled;
    private boolean nonOptionalToOptionalCalled;

    public void setOptionalToOptional(Optional<String> optionalToOptional) {
        this.optionalToOptionalCalled = true;
    }

    public void setOptionalToNonOptional(String optionalToNonOptional) {
        this.optionalToNonOptionalCalled = true;
    }

    public void setNonOptionalToOptional(Optional<String> nonOptionalToOptional) {
        this.nonOptionalToOptionalCalled = true;
    }

    public boolean isOptionalToOptionalCalled() {
        return optionalToOptionalCalled;
    }

    public boolean isOptionalToNonOptionalCalled() {
        return optionalToNonOptionalCalled;
    }

    public boolean isNonOptionalToOptionalCalled() {
        return nonOptionalToOptionalCalled;
    }
}
