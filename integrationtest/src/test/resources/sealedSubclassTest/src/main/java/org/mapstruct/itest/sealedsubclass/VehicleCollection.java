/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.sealedsubclass;

import java.util.ArrayList;
import java.util.Collection;

public class VehicleCollection {
    private Collection<Vehicle> vehicles = new ArrayList<>();

    public Collection<Vehicle> getVehicles() {
        return vehicles;
    }
}
