/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2795;

import java.util.Optional;

public class Source {

    private Optional<NestedDto> nested = Optional.empty();

    public Optional<NestedDto> getNested() {
        return nested;
    }

    public void setNested(Optional<NestedDto> nested) {
        this.nested = nested;
    }

}
