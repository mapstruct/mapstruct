/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories.assignment;

/**
 * @author Remo Meier
 */
public class Bar6 {
    private String propA;
    private String propB;

    private final String someTypeProp0;

    public Bar6(String someTypeProp0) {
        this.someTypeProp0 = someTypeProp0;
    }

    public String getPropA() {
        return propA;
    }

    public void setPropA(String propA) {
        this.propA = propA;
    }

    public String getPropB() {
        return propB;
    }

    public void setPropB(String propB) {
        this.propB = propB;
    }

    public String getSomeTypeProp0() {
        return someTypeProp0;
    }
}
