/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4060;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SetToDefaultMapper {

    SetToDefaultMapper INSTANCE = Mappers.getMapper( SetToDefaultMapper.class );

    class Nested {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class Target {
        private Nested nested;
        private String text;

        public Nested getNested() {
            return nested;
        }

        public void setNested(Nested nested) {
            this.nested = nested;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    class Source {
        private Nested nested;
        private String text;

        public Nested getNested() {
            return nested;
        }

        public void setNested(Nested nested) {
            this.nested = nested;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
    void update(@MappingTarget Target target, Source source);
}
