/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bool;

public class PersonDto {

    private String married;
    private String engaged;
    private String divorced;
    private Boolean widowed;

    public String getMarried() {
        return married;
    }

    public void setMarried(String married) {
        this.married = married;
    }

    public String getEngaged() {
        return engaged;
    }

    public void setEngaged(String engaged) {
        this.engaged = engaged;
    }

    public String getDivorced() {
        return divorced;
    }

    public void setDivorced(String divorced) {
        this.divorced = divorced;
    }

    public Boolean getWidowed() {
        return widowed;
    }

    public void setWidowed(Boolean widowed) {
        this.widowed = widowed;
    }

}
