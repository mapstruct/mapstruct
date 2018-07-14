/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1269.dto;

/**
 * @author Filip Hrisafov
 */
public class VehicleDto {

    private VehicleInfoDto vehicleInfo;

    public VehicleInfoDto getVehicleInfo() {
        return vehicleInfo;
    }

    public void setVehicleInfo(VehicleInfoDto vehicleInfo) {
        this.vehicleInfo = vehicleInfo;
    }

}
