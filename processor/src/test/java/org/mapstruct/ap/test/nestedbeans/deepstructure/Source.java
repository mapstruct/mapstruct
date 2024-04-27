/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.deepstructure;

class Source {
    SourceInnerChild sourceInnerChild;

    public SourceInnerChild getSourceInnerChild() {
        return sourceInnerChild;
    }

    public void setSourceInnerChild(SourceInnerChild sourceInnerChild) {
        this.sourceInnerChild = sourceInnerChild;
    }
}
