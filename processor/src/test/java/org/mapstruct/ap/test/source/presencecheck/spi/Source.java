/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.presencecheck.spi;

import java.util.List;

/**
 * @author Sean Huang
 */
public class Source {

    private double somePrimitiveDouble;
    private boolean hasPrimitiveSomeDouble = true;

    private Integer someInteger;
    private boolean hasSomeInteger = true;

    private List<String> someList;
    private boolean hasSomeList = true;

    private String[] someArray;
    private boolean hasSomeArray = true;

    public boolean hasSomePrimitiveDouble() {
        return hasPrimitiveSomeDouble;
    }

    public void setHasSomePrimitiveDouble(boolean has) {
        this.hasPrimitiveSomeDouble = has;
    }

    public double getSomePrimitiveDouble() {
        return somePrimitiveDouble;
    }

    public void setSomePrimitiveDouble(double someDouble) {
        this.somePrimitiveDouble = someDouble;
    }

    public boolean hasSomeInteger() {
        return hasSomeInteger;
    }

    public void setHasSomeInteger(boolean hasSomeInteger) {
        this.hasSomeInteger = hasSomeInteger;
    }

    public Integer getSomeInteger() {
        return someInteger;
    }

    public void setSomeInteger(Integer someInteger) {
        this.someInteger = someInteger;
    }

    public boolean hasSomeList() {
        return hasSomeList;
    }

    public void setHasSomeList(boolean has) {
        this.hasSomeList = has;
    }

    public List<String> getSomeList() {
        return someList;
    }

    public void setSomeList(List<String> someList) {
        this.someList = someList;
    }

    public String[] getSomeArray() {
        return someArray;
    }

    public void setSomeArray(String[] someArray) {
        this.someArray = someArray;
    }

    public boolean hasSomeArray() {
        return hasSomeArray;
    }

    public void setHasSomeArray(boolean hasSomeArray) {
        this.hasSomeArray = hasSomeArray;
    }

}
