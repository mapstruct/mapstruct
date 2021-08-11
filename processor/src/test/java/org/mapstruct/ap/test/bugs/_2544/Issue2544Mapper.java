/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2544;

import java.math.BigDecimal;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Ben Zegveld
 */
@Mapper
public interface Issue2544Mapper {

    Issue2544Mapper INSTANCE = Mappers.getMapper( Issue2544Mapper.class );

    @Mapping( target = "bigNumber", source = ".", numberFormat = "##0.#####E0" )
    Target map(String s);

    class Target {

        private BigDecimal bigNumber;

        public BigDecimal getBigNumber() {
            return bigNumber;
        }

        public void setBigNumber(BigDecimal bigNumber) {
            this.bigNumber = bigNumber;
        }
    }
}
