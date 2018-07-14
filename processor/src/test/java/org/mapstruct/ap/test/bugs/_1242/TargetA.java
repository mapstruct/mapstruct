/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1242;

/**
 * @author Andreas Gudian
 */
class TargetA {
    private TargetB b;

    public TargetB getB() {
        return b;
    }

    public void setB(TargetB b) {
        this.b = b;
    }
}
