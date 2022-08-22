/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.textBlocks;

/**
 * @author Filip Hrisafov
 */
public class Car {

    private WheelPosition wheelPosition;

    public WheelPosition getWheelPosition() {
        return wheelPosition;
    }

    public void setWheelPosition(WheelPosition wheelPosition) {
        this.wheelPosition = wheelPosition;
    }
}
