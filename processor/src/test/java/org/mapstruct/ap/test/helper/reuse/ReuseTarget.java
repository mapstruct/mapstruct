/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.reuse;

public class ReuseTarget {

    private Value valueA;
    private Value valueB;

    public Value getValueA() {
        return valueA;
    }

    public void setValueA(Value valueA) {
        this.valueA = valueA;
    }

    public Value getValueB() {
        return valueB;
    }

    public void setValueB(Value valueB) {
        this.valueB = valueB;
    }
}
