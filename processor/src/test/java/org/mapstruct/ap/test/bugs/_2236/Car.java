/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2236;

/**
 * @author Filip Hrisafov
 */
public class Car {

    private String name;
    private String type;
    private Owner ownerA;
    private Owner ownerB;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Owner getOwnerA() {
        return ownerA;
    }

    public void setOwnerA(Owner ownerA) {
        this.ownerA = ownerA;
    }

    public Owner getOwnerB() {
        return ownerB;
    }

    public void setOwnerB(Owner ownerB) {
        this.ownerB = ownerB;
    }
}
