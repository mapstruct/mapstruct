/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1242;

/**
 * @author Andreas Gudian
 */
class TargetB {
    private final String passedViaConstructor;

    TargetB(String passedViaConstructor) {
        this.passedViaConstructor = passedViaConstructor;
    }

    String getPassedViaConstructor() {
        return passedViaConstructor;
    }
}
