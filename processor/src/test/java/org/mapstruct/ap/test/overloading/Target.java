/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.overloading;

import java.util.Date;

public class Target {

    private long updatedOn;

    public long getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(long updatedOn) {
        this.updatedOn = updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        if ( updatedOn == null ) {
            return;
        }
        this.updatedOn = updatedOn.getTime();
    }

}
