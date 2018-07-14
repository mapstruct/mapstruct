/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bool;

public class Person {

    private Boolean married;
    private Boolean engaged;
    private YesNo divorced;
    private YesNo widowed;

    public Boolean isMarried() {
        return married;
    }

    public void setMarried(Boolean married) {
        this.married = married;
    }

    // START: please note: deliberately ordered, first getEngaged, then isEngaged.
    public Boolean getEngaged() {
        return engaged;
    }

    public Boolean isEngaged() {
        return engaged != null && !engaged;
    }
    // END

    public void setEngaged(Boolean engaged) {
        this.engaged = engaged;
    }

    public YesNo getDivorced() {
        return divorced;
    }

    public void setDivorced(YesNo divorced) {
        this.divorced = divorced;
    }

    public YesNo getWidowed() {
        return widowed;
    }

    public void setWidowed(YesNo widowed) {
        this.widowed = widowed;
    }
}
