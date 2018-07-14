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
public class ExpandedToolbox {

    private String brand;
    private Hammer hammer;
    private Wrench wrench;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Hammer getHammer() {
        return hammer;
    }

    public void setHammer(Hammer hammer) {
        this.hammer = hammer;
    }

    public Wrench getWrench() {
        return wrench;
    }

    public void setWrench(Wrench wrench) {
        this.wrench = wrench;
    }

}
