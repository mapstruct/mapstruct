/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.records;

import java.util.List;

/**
 * @author Filip Hrisafov
 */
public class Car {

    private List<WheelPosition> wheelPositions;

    public List<WheelPosition> getWheelPositions() {
        return wheelPositions;
    }

    public void setWheelPositions(List<WheelPosition> wheelPositions) {
        this.wheelPositions = wheelPositions;
    }
}
