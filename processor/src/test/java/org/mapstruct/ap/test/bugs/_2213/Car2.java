/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2213;

public class Car2 {
    private int[] intData;
    private Long[] longData;

    public @NotNull int[] getIntData() {
        return intData;
    }

    public void setIntData(int[] intData) {
        this.intData = intData;
    }

    public @NotNull Long[] getLongData() {
        return longData;
    }

    public void setLongData(Long[] longData) {
        this.longData = longData;
    }

}
