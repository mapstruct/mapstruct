/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.frommap;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Christian Kosmowski
 */
@Mapper
public interface MapToBeanFromMapAndNestedSource {

    MapToBeanFromMapAndNestedSource INSTANCE = Mappers.getMapper( MapToBeanFromMapAndNestedSource.class );

    @Mapping(target = "integer", source = "integers.number")
    @Mapping(target = "stringFromNestedSource", source = "source.nestedSource.nestedString")
    Target toTarget(Map<String, Integer> integers, Source source);

    class Source {

        private String stringFromBean = "stringFromBean";
        private NestedSource nestedSource = new NestedSource();

        public String getStringFromBean() {
            return stringFromBean;
        }

        public void setStringFromBean(String stringFromBean) {
            this.stringFromBean = stringFromBean;
        }

        public NestedSource getNestedSource() {
            return nestedSource;
        }

        public void setNestedSource(NestedSource nestedSource) {
            this.nestedSource = nestedSource;
        }

        class NestedSource {

            private String nestedString = "nestedString";

            public String getNestedString() {
                return nestedString;
            }
        }
    }

    class Target {

        private int integer;
        private String stringFromNestedSource;

        public int getInteger() {
            return integer;
        }

        public void setInteger(int integer) {
            this.integer = integer;
        }

        public String getStringFromNestedSource() {
            return stringFromNestedSource;
        }

        public void setStringFromNestedSource(String stringFromNestedSource) {
            this.stringFromNestedSource = stringFromNestedSource;
        }
    }

}
