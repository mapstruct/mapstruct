/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.index;

import java.util.Set;

public class DriverList {

    Set<Person> drivers;

    public Set<Person> getDrivers() {
        return drivers;
    }

    public void setDrivers(Set<Person> drivers) {
        this.drivers = drivers;
    }
}
