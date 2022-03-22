/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2795.dto;

import java.util.Optional;

public class TripDto {

    private long id;

    private String name;

    private Optional<CarDto> car = Optional.empty();

    private Optional<PlaneDto> plane = Optional.empty();

    private Optional<ShipDto> ship = Optional.empty();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<CarDto> getCar() {
        return car;
    }

    public void setCar(Optional<CarDto> car) {
        this.car = car;
    }

    public Optional<PlaneDto> getPlane() {
        return plane;
    }

    public void setPlane(Optional<PlaneDto> plane) {
        this.plane = plane;
    }

    public Optional<ShipDto> getShip() {
        return ship;
    }

    public void setShip(Optional<ShipDto> ship) {
        this.ship = ship;
    }

}
