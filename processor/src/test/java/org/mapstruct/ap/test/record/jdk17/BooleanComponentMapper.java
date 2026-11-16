/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.record.jdk17;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface BooleanComponentMapper {

    BooleanComponentMapper INSTANCE = Mappers.getMapper( BooleanComponentMapper.class );

    MemberEntity fromRecord(MemberDto record);

    MemberDto toRecord(MemberEntity entity);

    record MemberDto(Boolean isActive, Boolean premium) {
    }

    class MemberEntity {

        private Boolean isActive;
        private Boolean premium;

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean active) {
            isActive = active;
        }

        public Boolean getPremium() {
            return premium;
        }

        public void setPremium(Boolean premium) {
            this.premium = premium;
        }
    }
}
