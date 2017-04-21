/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.overloading;

import java.util.Date;

public class Target {

    private long updatedOnTarget;

    public long getUpdatedOnTarget() {
        return updatedOnTarget;
    }

    public void setUpdatedOnTarget(long updatedOnTarget) {
        this.updatedOnTarget = updatedOnTarget;
    }

    public void setUpdatedOnTarget(Date updatedOnTarget) {
        if ( updatedOnTarget == null ) {
            return;
        }
        this.updatedOnTarget = updatedOnTarget.getTime();
    }

}
