/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1269.model;

/**
 * @author Filip Hrisafov
 */
public class VehicleImage {

    private final Integer pictureSize;

    private final String src;

    public VehicleImage(Integer pictureSize, String src) {
        this.pictureSize = pictureSize;
        this.src = src;
    }

    public Integer getPictureSize() {
        return pictureSize;
    }

    public String getSrc() {
        return src;
    }
}
