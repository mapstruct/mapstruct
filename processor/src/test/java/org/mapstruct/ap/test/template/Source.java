/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.template;

/**
 * @author Sjaak Derksen
 */
public class Source {

    private String stringPropX;

    private Integer integerPropX;

    private NestedSource nestedSourceProp;

    public String getStringPropX() {
        return stringPropX;
    }

    public void setStringPropX(String stringPropX) {
        this.stringPropX = stringPropX;
    }

    public Integer getIntegerPropX() {
        return integerPropX;
    }

    public void setIntegerPropX(Integer integerPropX) {
        this.integerPropX = integerPropX;
    }

    public NestedSource getNestedSourceProp() {
        return nestedSourceProp;
    }

    public void setNestedSourceProp(NestedSource nestedSourceProp) {
        this.nestedSourceProp = nestedSourceProp;
    }
}
