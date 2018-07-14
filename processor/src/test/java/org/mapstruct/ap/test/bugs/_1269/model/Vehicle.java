/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1269.model;

import java.util.List;

/**
 * @author Filip Hrisafov
 */
public class Vehicle {

    private final VehicleTypeInfo vehicleTypeInfo;

    private final List<VehicleImage> images;

    public Vehicle(VehicleTypeInfo vehicleTypeInfo, List<VehicleImage> images) {
        this.vehicleTypeInfo = vehicleTypeInfo;
        this.images = images;
    }

    public VehicleTypeInfo getVehicleTypeInfo() {
        return vehicleTypeInfo;
    }

    public List<VehicleImage> getImages() {
        return images;
    }
}
