/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.sealedsubclass;

public final class Davidson extends Motor {
    private int numberOfExhausts;

    public int getNumberOfExhausts() {
        return numberOfExhausts;
    }

    public void setNumberOfExhausts(int numberOfExhausts) {
        this.numberOfExhausts = numberOfExhausts;
    }
}
