/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.inheritance;

public class TargetExt extends TargetBase {

    private int bar;

    public int getBar() {
        return bar;
    }

    public void setBar(int bar) {
        this.bar = bar;
    }
}
