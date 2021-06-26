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
public interface MapToBeanFromMultipleSources {

    MapToBeanFromMultipleSources INSTANCE = Mappers.getMapper( MapToBeanFromMultipleSources.class );

    @Mapping(target = "integer", source = "integers.number")
    @Mapping(target = "string", source = "strings.string")
    @Mapping(target = "stringFromBean", source = "bean.stringFromBean")
    Target toTarget(Map<String, Integer> integers, Map<String, String> strings, Source bean);

    class Source {
        private String stringFromBean = "stringFromBean";

        public String getStringFromBean() {
            return stringFromBean;
        }

        public void setStringFromBean(String stringFromBean) {
            this.stringFromBean = stringFromBean;
        }
    }

    class Target {

        private int integer;
        private String string;
        private String stringFromBean;

        public int getInteger() {
            return integer;
        }

        public void setInteger(int integer) {
            this.integer = integer;
        }

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }

        public String getStringFromBean() {
            return stringFromBean;
        }

        public void setStringFromBean(String stringFromBean) {
            this.stringFromBean = stringFromBean;
        }
    }

}
