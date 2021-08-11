/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2530;

import java.time.LocalDate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Ben Zegveld
 */
@Mapper
public interface Issue2530Mapper {

    Issue2530Mapper INSTANCE = Mappers.getMapper( Issue2530Mapper.class );

    @Mapping(target = "date", source = ".", dateFormat = "yyyy-MM-dd")
    Test map(String s);

    class Test {

        LocalDate date;

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate dateTime) {
            this.date = dateTime;
        }
    }
}
