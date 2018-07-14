/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1269.model;

/**
 * @author Filip Hrisafov
 */
public class VehicleTypeInfo {

    private final String type;

    private final String name;

    private final Integer doors;

    public VehicleTypeInfo(String type, String name, Integer doors) {
        this.type = type;
        this.name = name;
        this.doors = doors;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Integer getDoors() {
        return doors;
    }
}
