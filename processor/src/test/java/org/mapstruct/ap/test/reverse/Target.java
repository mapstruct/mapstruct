/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.reverse;

/**
 * @author Sjaak Derksen
 */
public class Target {

    private String stringPropY;

    private Integer integerPropY;

    private String propertyNotToIgnoreUpstream;

    public String getStringPropY() {
        return stringPropY;
    }

    public void setStringPropY(String stringPropY) {
        this.stringPropY = stringPropY;
    }

    public Integer getIntegerPropY() {
        return integerPropY;
    }

    public void setIntegerPropY(Integer integerPropY) {
        this.integerPropY = integerPropY;
    }

    public String getPropertyNotToIgnoreUpstream() {
        return propertyNotToIgnoreUpstream;
    }

    public void setPropertyNotToIgnoreUpstream(String propertyNotToIgnoreUpstream) {
        this.propertyNotToIgnoreUpstream = propertyNotToIgnoreUpstream;
    }
}
