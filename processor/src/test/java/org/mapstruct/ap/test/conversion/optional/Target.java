/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.optional;

import java.util.Optional;

public class Target {

    // CHECKSTYLE:OFF
    public Optional<String> toOptionalPubProp;
    // CHECKSTYLE:ON

    // CHECKSTYLE:OFF
    public String fromOptionalPubProp;
    // CHECKSTYLE:ON

    private Optional<String> toOptionalProp;

    private String fromOptionalProp;

    private Optional<String> optional;

    public Optional<String> getToOptionalProp() {
        return toOptionalProp;
    }

    public void setToOptionalProp(Optional<String> toOptionalProp) {
        this.toOptionalProp = toOptionalProp;
    }

    public String getFromOptionalProp() {
        return fromOptionalProp;
    }

    public void setFromOptionalProp(String fromOptionalProp) {
        this.fromOptionalProp = fromOptionalProp;
    }

    public Optional<String> getOptional() {
        return optional;
    }

    public void setOptional(Optional<String> optional) {
        this.optional = optional;
    }
}
