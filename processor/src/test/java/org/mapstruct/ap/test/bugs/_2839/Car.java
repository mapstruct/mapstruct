/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2839;

import java.util.List;

/**
 * @author Hakan Özkan
 */
public final class Car {

    private final Id id;
    private final List<? extends Id> seatIds;
    private final List<? extends Id> tireIds;

    public Car(Id id, List<? extends Id> seatIds, List<? extends Id> tireIds) {
        this.id = id;
        this.seatIds = seatIds;
        this.tireIds = tireIds;
    }

    public Id getId() {
        return id;
    }

    public List<? extends Id> getSeatIds() {
        return seatIds;
    }

    public List<? extends Id> getTireIds() {
        return tireIds;
    }
}
