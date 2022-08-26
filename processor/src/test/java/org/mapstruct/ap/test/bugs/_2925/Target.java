/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2925;

import java.util.Optional;

public class Target {

    private Long value;

    public Optional<Long> getValue() {
        return Optional.ofNullable( value );
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public void setValue(Optional<Long> value) {
        this.value = value.orElse( null );
    }
}
