/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2213;

public class Car {
    private int[] intData;
    private Long[] longData;

    public int[] getIntData() {
        return intData;
    }

    public void setIntData(int[] intData) {
        this.intData = intData;
    }

    public Long[] getLongData() {
        return longData;
    }

    public void setLongData(Long[] longData) {
        this.longData = longData;
    }

}
