/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.presencecheck.spi;

import java.util.List;

/**
 * @author Cindy Wang
 */
public class TargetWithPresenceTracking {
    private double somePrimitiveDouble;
    private Integer someInteger;
    private List<String> someList;
    private String[] someArray;

    private boolean hasSomePrimitiveDouble = false;
    private boolean hasSomeInteger = false;
    private boolean hasSomeList = false;
    private boolean hasSomeArray = false;

    public double getSomePrimitiveDouble() {
        return somePrimitiveDouble;
    }

    public void setSomePrimitiveDouble(double someDouble) {
        this.somePrimitiveDouble = someDouble;
        hasSomePrimitiveDouble = true;
    }

    public Integer getSomeInteger() {
        return someInteger;
    }

    public void setSomeInteger(Integer someInteger) {
        this.someInteger = someInteger;
        hasSomeInteger = true;
    }

    public List<String> getSomeList() {
        return someList;
    }

    public void setSomeList(List<String> someList) {
        this.someList = someList;
        hasSomeList = true;
    }

    public String[] getSomeArray() {
        return someArray;
    }

    public void setSomeArray(String[] someArray) {
        this.someArray = someArray;
        hasSomeArray = true;
    }

    public boolean hasSomePrimitiveDouble() {
        return hasSomePrimitiveDouble;
    }

    public boolean hasSomeInteger() {
        return hasSomeInteger;
    }

    public boolean hasSomeList() {
        return hasSomeList;
    }

    public boolean hasSomeArray() {
        return hasSomeArray;
    }
}
