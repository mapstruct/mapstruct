/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.ignore.expand;

/**
 *
 * @author Sjaak Derksen
 */
public class FlattenedToolBox {

    private String brand;
    private String hammerDescription;
    private Integer hammerSize;
    private Boolean wrenchIsBahco;
    private String wrenchDescription;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getHammerDescription() {
        return hammerDescription;
    }

    public void setHammerDescription(String hammerDescription) {
        this.hammerDescription = hammerDescription;
    }

    public Integer getHammerSize() {
        return hammerSize;
    }

    public void setHammerSize(Integer hammerSize) {
        this.hammerSize = hammerSize;
    }

    public Boolean getWrenchIsBahco() {
        return wrenchIsBahco;
    }

    public void setWrenchIsBahco(Boolean wrenchIsBahco) {
        this.wrenchIsBahco = wrenchIsBahco;
    }

    public String getWrenchDescription() {
        return wrenchDescription;
    }

    public void setWrenchDescription(String wrenchDescription) {
        this.wrenchDescription = wrenchDescription;
    }

}
