/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping.nested;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Source {

    private Optional<NestedOptional> optionalToNonOptional;
    private Optional<NestedOptional> optionalToOptional;
    private Optional<NestedNonOptional> nonOptionalToNonOptional;
    private Optional<NestedNonOptional> nonOptionalToOptional;

    public Optional<NestedOptional> getOptionalToNonOptional() {
        return optionalToNonOptional;
    }

    public void setOptionalToNonOptional(
        Optional<NestedOptional> optionalToNonOptional) {
        this.optionalToNonOptional = optionalToNonOptional;
    }

    public Optional<NestedOptional> getOptionalToOptional() {
        return optionalToOptional;
    }

    public void setOptionalToOptional(
        Optional<NestedOptional> optionalToOptional) {
        this.optionalToOptional = optionalToOptional;
    }

    public Optional<NestedNonOptional> getNonOptionalToNonOptional() {
        return nonOptionalToNonOptional;
    }

    public void setNonOptionalToNonOptional(
        Optional<NestedNonOptional> nonOptionalToNonOptional) {
        this.nonOptionalToNonOptional = nonOptionalToNonOptional;
    }

    public Optional<NestedNonOptional> getNonOptionalToOptional() {
        return nonOptionalToOptional;
    }

    public void setNonOptionalToOptional(
        Optional<NestedNonOptional> nonOptionalToOptional) {
        this.nonOptionalToOptional = nonOptionalToOptional;
    }

    public static class NestedOptional {

        private final Optional<String> value;

        public NestedOptional(Optional<String> value) {
            this.value = value;
        }

        public Optional<String> getValue() {
            return value;
        }
    }

    public static class NestedNonOptional {

        private final String value;

        public NestedNonOptional(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
    
}
