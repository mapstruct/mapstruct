/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2236;

/**
 * @author Filip Hrisafov
 */
public class CarDto {

    private String name;
    private String ownerNameA;
    private String ownerNameB;
    private String ownerCityA;
    private String ownerCityB;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerNameA() {
        return ownerNameA;
    }

    public void setOwnerNameA(String ownerNameA) {
        this.ownerNameA = ownerNameA;
    }

    public String getOwnerNameB() {
        return ownerNameB;
    }

    public void setOwnerNameB(String ownerNameB) {
        this.ownerNameB = ownerNameB;
    }

    public String getOwnerCityA() {
        return ownerCityA;
    }

    public void setOwnerCityA(String ownerCityA) {
        this.ownerCityA = ownerCityA;
    }

    public String getOwnerCityB() {
        return ownerCityB;
    }

    public void setOwnerCityB(String ownerCityB) {
        this.ownerCityB = ownerCityB;
    }
}
