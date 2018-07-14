/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1242;

/**
 * @author Andreas Gudian
 */
class SourceA {
    private SourceB b;

    public SourceB getB() {
        return b;
    }

    public void setB(SourceB b) {
        this.b = b;
    }
}
