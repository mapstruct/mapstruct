/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.reverse;

/**
 * @author Sjaak Derksen
 */
public class Source {

    private String stringPropX;

    private Integer integerPropX;

    private String someConstantDownstream;

    private String propertyToIgnoreDownstream;

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

    public String getSomeConstantDownstream() {
        return someConstantDownstream;
    }

    public void setSomeConstantDownstream(String someConstantDownstream) {
        this.someConstantDownstream = someConstantDownstream;
    }

    public String getPropertyToIgnoreDownstream() {
        return propertyToIgnoreDownstream;
    }

    public void setPropertyToIgnoreDownstream(String propertyToIgnoreDownstream) {
        this.propertyToIgnoreDownstream = propertyToIgnoreDownstream;
    }
}
