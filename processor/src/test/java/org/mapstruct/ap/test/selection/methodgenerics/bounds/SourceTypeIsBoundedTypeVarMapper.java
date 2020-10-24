/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.methodgenerics.bounds;

import org.mapstruct.Mapper;
import org.mapstruct.TargetType;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface SourceTypeIsBoundedTypeVarMapper {

    SourceTypeIsBoundedTypeVarMapper INSTANCE = Mappers.getMapper( SourceTypeIsBoundedTypeVarMapper.class );

    Target sourceToTarget(Source source);

    @SuppressWarnings( "unchecked" )
    default <T extends NestedBase> T map(String in, @TargetType Class<T> clz) {
        if ( clz == Nested.class ) {
            return (T) new Nested( in );
        }
        return null;
    }

    default Long map( String in ) {
        return Long.parseLong( in );
    }

    class Source {

        private final String prop1;
        private final String prop2;

        public Source(String prop1, String prop2) {
            this.prop1 = prop1;
            this.prop2 = prop2;
        }

        public String getProp1() {
            return prop1;
        }

        public String getProp2() {
            return prop2;
        }
    }

    class Target {

        private Long prop1;
        private Nested prop2;

        public Long getProp1() {
            return prop1;
        }

        public void setProp1(Long prop1) {
            this.prop1 = prop1;
        }

        public Nested getProp2() {
            return prop2;
        }

        public void setProp2(Nested prop2) {
            this.prop2 = prop2;
        }
    }

    class NestedBase {
    }

    class Nested extends NestedBase {

        private String prop;

        public Nested(String prop) {
            this.prop = prop;
        }

        public String getProp() {
            return prop;
        }

    }
}
