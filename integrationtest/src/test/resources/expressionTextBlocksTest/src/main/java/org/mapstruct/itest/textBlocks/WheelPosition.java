/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.textBlocks;

/**
 * @author Filip Hrisafov
 */
public class WheelPosition {

    private final String position;

    public WheelPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}
