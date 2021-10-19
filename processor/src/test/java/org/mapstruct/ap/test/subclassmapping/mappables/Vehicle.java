/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.mappables;

public class Vehicle {
    private String name;
    private String vehicleManufacturingCompany;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVehicleManufacturingCompany() {
        return vehicleManufacturingCompany;
    }

    public void setVehicleManufacturingCompany(String vehicleManufacturingCompany) {
        this.vehicleManufacturingCompany = vehicleManufacturingCompany;
    }
}
