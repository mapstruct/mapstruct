/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.sealedsubclass;

import java.util.ArrayList;
import java.util.Collection;

public class VehicleCollectionDto {
    private Collection<VehicleDto> vehicles = new ArrayList<>();

    public Collection<VehicleDto> getVehicles() {
        return vehicles;
    }
}
