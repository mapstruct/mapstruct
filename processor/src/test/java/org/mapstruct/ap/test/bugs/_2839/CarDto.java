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
public final class CarDto {

    private final String id;
    private final List<String> seatIds;
    private final List<String> tireIds;

    public CarDto(String id, List<String> seatIds, List<String> tireIds) {
        this.id = id;
        this.seatIds = seatIds;
        this.tireIds = tireIds;
    }

    public String getId() {
        return id;
    }

    public List<String> getSeatIds() {
        return seatIds;
    }

    public List<String> getTireIds() {
        return tireIds;
    }
}
