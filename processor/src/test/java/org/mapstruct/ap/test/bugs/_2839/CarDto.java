/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2839;

import java.util.List;
import java.util.UUID;

/**
 * @author Hakan Özkan
 */
public final class CarDto {

    private final UUID id;
    private final List<UUID> seatIds;
    private final List<UUID> tireIds;

    public CarDto(UUID id, List<UUID> seatIds, List<UUID> tireIds) {
        this.id = id;
        this.seatIds = seatIds;
        this.tireIds = tireIds;
    }

    public UUID getId() {
        return id;
    }

    public List<UUID> getSeatIds() {
        return seatIds;
    }

    public List<UUID> getTireIds() {
        return tireIds;
    }
}
