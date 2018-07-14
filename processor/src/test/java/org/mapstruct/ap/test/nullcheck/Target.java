/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck;

import java.util.List;

/**
 * @author Sjaak Derksen
 */
public class Target {

    private String someObject;
    private Integer number;
    private List<String> someList;
    private MyBigIntWrapper someInteger;
    private MyLongWrapper someLong;

    public String getSomeObject() {
        return someObject;
    }

    public void setSomeObject(String someObject) {
        this.someObject = someObject;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<String> getSomeList() {
        return someList;
    }

    public void setSomeList(List<String> someList) {
        this.someList = someList;
    }

    public MyBigIntWrapper getSomeInteger() {
        return someInteger;
    }

    public void setSomeInteger(MyBigIntWrapper someInteger) {
        this.someInteger = someInteger;
    }

    public MyLongWrapper getSomeLong() {
        return someLong;
    }

    public void setSomeLong(MyLongWrapper someLong) {
        this.someLong = someLong;
    }
}
