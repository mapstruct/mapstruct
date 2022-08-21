/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2907;

import java.util.Set;

public class Source {

    private Set<SourceNested> nested;

    public Set<SourceNested> getNested() {
        return nested;
    }

    public void setNested(Set<SourceNested> nested) {
        this.nested = nested;
    }
}
