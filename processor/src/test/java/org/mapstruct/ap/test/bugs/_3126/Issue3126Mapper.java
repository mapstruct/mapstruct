/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */

package org.mapstruct.ap.test.bugs._3126;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

@Mapper(subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface Issue3126Mapper {

    Issue3126Mapper INSTANCE = Mappers.getMapper( Issue3126Mapper.class );

    @SubclassMapping(target = HomeAddressDto.class, source = HomeAddress.class)
    @SubclassMapping(target = OfficeAddressDto.class, source = OfficeAddress.class)
    @Mapping(target = ".", source = "auditable")
    AddressDto map(Address address);

    interface AddressDto {

    }

    class HomeAddressDto implements AddressDto {
        private String createdBy;

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }
    }

    class OfficeAddressDto implements AddressDto {
        private String createdBy;

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }
    }

    class HomeAddress extends Address {

        public HomeAddress(Auditable auditable) {
            super( auditable );
        }
    }

    class OfficeAddress extends Address {

        public OfficeAddress(Auditable auditable) {
            super( auditable );
        }
    }

    abstract class Address {

        private final Auditable auditable;

        protected Address(Auditable auditable) {
            this.auditable = auditable;
        }

        public Auditable getAuditable() {
            return auditable;
        }
    }

    class Auditable {

        private final String createdBy;

        public Auditable(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getCreatedBy() {
            return createdBy;
        }
    }
}
