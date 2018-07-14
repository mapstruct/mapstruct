/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bool;

/**
 * @author Andreas Gudian
 *
 */
public class YesNo {
    private boolean yes;

    public YesNo(boolean yes) {
        this.yes = yes;
    }

    public boolean isYes() {
        return yes;
    }

    public void setYes(boolean yes) {
        this.yes = yes;
    }
}
