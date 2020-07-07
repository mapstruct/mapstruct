/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.twosteperror;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ErroneousMapperMM {

    ErroneousMapperMM INSTANCE = Mappers.getMapper( ErroneousMapperMM.class );

    Target map( Source s );

    default TargetType methodY1(TypeInTheMiddleA s) {
        return new TargetType( s.test );
    }

    default TypeInTheMiddleA methodX1(SourceType s) {
        return new TypeInTheMiddleA( s.t1 );
    }

    default TargetType methodY2(TypeInTheMiddleB s) {
        return new TargetType( s.test );
    }

    default TypeInTheMiddleB methodX2(SourceType s) {
        return new TypeInTheMiddleB( s.t1 );
    }

    // CHECKSTYLE:OFF
    class Target {
        public TargetType t1;
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

    class TypeInTheMiddleA {

        TypeInTheMiddleA(String t1) {
            this.test = t1;
        }

        public String test;
    }

    class TypeInTheMiddleB {

        TypeInTheMiddleB(String t1) {
            this.test = t1;
        }

        public String test;
    }
    // CHECKSTYLE:ON

}
