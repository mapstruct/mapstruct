/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1269.dto;

import java.util.List;

/**
 * @author Filip Hrisafov
 */
public class VehicleInfoDto {

    private String type;

    private String name;

    private Integer doors;

    // make sure that mapping on images does not happen based on images mapping
    private List<VehicleImageDto> images;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDoors() {
        return doors;
    }

    public void setDoors(Integer doors) {
        this.doors = doors;
    }

    public List<VehicleImageDto> getImages() {
        return images;
    }

    public void setImages(List<VehicleImageDto> images) {
        this.images = images;
    }
}
