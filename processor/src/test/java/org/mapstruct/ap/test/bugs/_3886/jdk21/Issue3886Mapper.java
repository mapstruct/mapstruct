/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3886.jdk21;

import java.time.LocalDate;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author Filip Hrisafov
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface Issue3886Mapper {

    RangeRecord map(LocalDate validFrom);

    record RangeRecord(LocalDate validFrom) {

        public RangeRecord restrictTo(RangeRecord other) {
            return null;
        }

        public void setName(String name) {
            // This method is here to ensure that MapStruct won't treat it as a setter
        }
    }
}
