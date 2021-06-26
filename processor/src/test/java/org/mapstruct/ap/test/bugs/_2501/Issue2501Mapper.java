/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2501;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2501Mapper {

    Issue2501Mapper INSTANCE = Mappers.getMapper( Issue2501Mapper.class );

    Customer map(CustomerDTO value);

    CustomerStatus map(DtoStatus status);

    default <T> T unwrap(Optional<T> optional) {
        return optional.orElse( null );
    }

    enum CustomerStatus {
        ENABLED, DISABLED,
    }

    class Customer {

        private CustomerStatus status;

        public CustomerStatus getStatus() {
            return status;
        }

        public void setStatus(CustomerStatus stat) {
            this.status = stat;
        }
    }

    enum DtoStatus {
        ENABLED, DISABLED
    }

    class CustomerDTO {

        private DtoStatus status;

        public Optional<DtoStatus> getStatus() {
            return Optional.ofNullable( status );
        }

        public void setStatus(DtoStatus stat) {
            this.status = stat;
        }
    }
}
