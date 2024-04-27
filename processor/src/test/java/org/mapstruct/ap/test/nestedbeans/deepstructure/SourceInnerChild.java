/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.deepstructure;

import java.util.List;

class SourceInnerChild {
    private List<SourceChild> nestedSourceChild;
    private SourceSecondSubChild nestedSecondSourceChild;

    public SourceSecondSubChild getNestedSecondSourceChild() {
        return nestedSecondSourceChild;
    }

    public void setNestedSecondSourceChild(SourceSecondSubChild nestedSecondSourceChild) {
        this.nestedSecondSourceChild = nestedSecondSourceChild;
    }

    public List<SourceChild> getNestedSourceChild() {
        return nestedSourceChild;
    }

    public void setNestedSourceChild(List<SourceChild> nestedSourceChild) {
        this.nestedSourceChild = nestedSourceChild;
    }
}
