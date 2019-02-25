/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1719;

import java.util.HashSet;
import java.util.Set;

public class Source {

    private Set<SourceElement> sourceElements = new HashSet<>();

    public Set<SourceElement> getSourceElements() {
        return sourceElements;
    }

    public void setSourceElements(Set<SourceElement> sourceElements) {
        this.sourceElements = sourceElements;
    }

}
