/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.mappables;

import java.util.Objects;

public class Car extends Vehicle {
    private boolean manual;

    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }

    @Override
    public int hashCode() {
        return Objects.hash( manual );
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        Car other = (Car) obj;
        return manual == other.manual
            && Objects.equals( getName(), other.getName() )
            && Objects.equals( getVehicleManufacturingCompany(), other.getVehicleManufacturingCompany() );
    }

}
