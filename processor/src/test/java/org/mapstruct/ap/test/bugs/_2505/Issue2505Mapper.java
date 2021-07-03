/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2505;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.control.DeepClone;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper( mappingControl = DeepClone.class )
public interface Issue2505Mapper {

    Issue2505Mapper INSTANCE = Mappers.getMapper( Issue2505Mapper.class );

    Customer map(CustomerDTO value);

    enum Status {
        ENABLED, DISABLED,
    }

    class Customer {

        private Status status;

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status stat) {
            this.status = stat;
        }
    }

    class CustomerDTO {

        private Status status;

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }
    }
}
