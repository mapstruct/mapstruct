/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories.assignment;

/**
 * @author Remo Meier
 */
public class Foo6A {
    private String propA;

    public String getPropA() {
        return propA;
    }

    public void setPropB(String prop) {
        this.propA = prop;
    }
}
