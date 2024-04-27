/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.deepstructure;

class SourceChild {
    SourceSubChild autoMapChild;

    public SourceSubChild getAutoMapChild() {
        return autoMapChild;
    }

    public void setAutoMapChild(SourceSubChild autoMapChild) {
        this.autoMapChild = autoMapChild;
    }
}
