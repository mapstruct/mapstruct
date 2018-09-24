/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.strategy;

public class HouseEntity {

    private String owner;
    private boolean ownerSet = false;

    private Integer number;
    private boolean numberSet = false;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        ownerSet = true;
        this.owner = owner;
    }

    public boolean ownerSet() {
        return ownerSet;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        numberSet = true;
        this.number = number;
    }

    public boolean numberSet() {
        return numberSet;
    }
}
