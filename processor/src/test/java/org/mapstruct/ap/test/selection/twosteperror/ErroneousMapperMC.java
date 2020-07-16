/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.twosteperror;

import java.math.BigDecimal;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ErroneousMapperMC {

    ErroneousMapperMC INSTANCE = Mappers.getMapper( ErroneousMapperMC.class );

    Target map(Source s);

    default BigDecimal methodX1(SourceType s) {
        return new BigDecimal( s.t1 );
    }

    default Double methodX2(SourceType s) {
        return new Double( s.t1 );
    }

    // CHECKSTYLE:OFF
    class Target {
        public String t1;
    }

    class Source {
        public SourceType t1;
    }

    class SourceType {
        public String t1;
    }

    class TargetType {
        public String t1;

        public TargetType(String test) {
            this.t1 = test;
        }
    }


    // CHECKSTYLE:ON

}
