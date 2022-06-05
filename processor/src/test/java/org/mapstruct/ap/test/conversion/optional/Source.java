/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.optional;

import java.util.Optional;

public class Source {

    // CHECKSTYLE:OFF
    public Optional<String> fromOptionalPubProp;
    // CHECKSTYLE:ON

    // CHECKSTYLE:OFF
    public String toOptionalPubProp;
    // CHECKSTYLE:ON

    private Optional<String> fromOptionalProp;

    private String toOptionalProp;

    private Optional<String> optional;

    public Optional<String> getFromOptionalProp() {
        return fromOptionalProp;
    }

    public void setFromOptionalProp(Optional<String> fromOptionalProp) {
        this.fromOptionalProp = fromOptionalProp;
    }

    public String getToOptionalProp() {
        return toOptionalProp;
    }

    public void setToOptionalProp(String toOptionalProp) {
        this.toOptionalProp = toOptionalProp;
    }

    public Optional<String> getOptional() {
        return optional;
    }

    public void setOptional(Optional<String> optional) {
        this.optional = optional;
    }
}
