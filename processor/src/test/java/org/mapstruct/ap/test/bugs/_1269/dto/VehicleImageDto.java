/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1269.dto;

/**
 * @author Filip Hrisafov
 */
public class VehicleImageDto {

    private Integer pictureSize;

    private String src;

    public Integer getPictureSize() {
        return pictureSize;
    }

    public void setPictureSize(Integer pictureSize) {
        this.pictureSize = pictureSize;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
