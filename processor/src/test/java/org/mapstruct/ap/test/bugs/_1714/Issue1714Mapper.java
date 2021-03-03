/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1714;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue1714Mapper {

    Issue1714Mapper INSTANCE = Mappers.getMapper( Issue1714Mapper.class );

    @Mapping(target = "seasonNumber", source = "programInstance", qualifiedByName = "getSeasonNumber")
    OfferEntity map(OnDemand offerStatusDTO);

    @Named("getTitle")
    default String mapTitle(Program programInstance) {
        return "dont care";
    }

    @Named("getSeasonNumber")
    default Integer mapSeasonNumber(Program programInstance) {
        return 1;
    }

    class OfferEntity {

        private String seasonNumber;

        public String getSeasonNumber() {
            return seasonNumber;
        }

        public void setSeasonNumber(String seasonNumber) {
            this.seasonNumber = seasonNumber;
        }

    }

    class OnDemand {

        private Program programInstance;

        public Program getProgramInstance() {
            return programInstance;
        }

        public void setProgramInstance(Program programInstance) {
            this.programInstance = programInstance;
        }
    }

    class Program {
    }
}
