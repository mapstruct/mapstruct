/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.innerclasses;

public class BeanFacade {

    private String test;
    private String innerEnum;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getInnerEnum() {
        return innerEnum;
    }

    public void setInnerEnum(String innerEnum) {
        this.innerEnum = innerEnum;
    }
}
