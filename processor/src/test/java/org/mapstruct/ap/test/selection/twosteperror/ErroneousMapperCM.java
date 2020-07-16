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
public interface ErroneousMapperCM {

    ErroneousMapperCM INSTANCE = Mappers.getMapper( ErroneousMapperCM.class );

    Target map(Source s);

    default TargetType methodY1(String s) {
        return new TargetType( s );
    }

    default TargetType methodY2(Double d) {
        return new TargetType( d.toString() );
    }

    // CHECKSTYLE:OFF
    class Target {
        public TargetType t1;
    }

    class Source {
        public BigDecimal t1;
    }

    class TargetType {
        public String t1;

        public TargetType(String test) {
            this.t1 = test;
        }
    }

    class TypeInTheMiddleA {

        TypeInTheMiddleA(String t1) {
            this.test = t1;
        }

        public String test;
    }

    // CHECKSTYLE:ON

}
