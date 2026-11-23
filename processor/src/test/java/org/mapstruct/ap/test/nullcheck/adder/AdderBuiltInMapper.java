/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.adder;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper( collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED )
public interface AdderBuiltInMapper {

    AdderBuiltInMapper INSTANCE = Mappers.getMapper( AdderBuiltInMapper.class );

    Target map(Source source);

    class Source {

        private List<Calendar> dates;

        public List<Calendar> getDates() {
            return dates;
        }

        public void setDates(List<Calendar> dates) {
            this.dates = dates;
        }
    }

    class Target {

        private List<ZonedDateTime> dates = new ArrayList<>();

        public List<ZonedDateTime> getDates() {
            return dates;
        }

        public void setDates(List<ZonedDateTime> dates) {
            this.dates = dates;
        }

        public void addDate(ZonedDateTime date) {
            this.dates.add( date );
        }
    }
}
