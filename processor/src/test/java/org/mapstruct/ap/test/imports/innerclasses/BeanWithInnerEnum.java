/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.innerclasses;

public class BeanWithInnerEnum {

    private String test;
    private InnerEnum innerEnum;

    public enum InnerEnum {
        A, B;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public InnerEnum getInnerEnum() {
        return innerEnum;
    }

    public void setInnerEnum(InnerEnum innerEnum) {
        this.innerEnum = innerEnum;
    }
}
