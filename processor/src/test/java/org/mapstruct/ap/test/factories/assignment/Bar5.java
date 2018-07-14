/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories.assignment;

/**
 * @author Remo Meier
 */
public class Bar5 {
    private String propA;
    private String propB;

    private final String someTypeProp0;
    private final String someTypeProp1;

    public Bar5(String someTypeProp0, String someTypeProp1) {
        this.someTypeProp0 = someTypeProp0;
        this.someTypeProp1 = someTypeProp1;
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

    public String getSomeTypeProp1() {
        return someTypeProp1;
    }
}
