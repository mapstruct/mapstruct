/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.context.objectfactory;

/**
 *
 * @author Sjaak Derksen
 */
public class Valve {

    private boolean oneWay;
    private final String id;

    public Valve(String id) {
        this.id = id;
    }

    public boolean isOneWay() {
        return oneWay;
    }

    public void setOneWay(boolean oneWay) {
        this.oneWay = oneWay;
    }

}
