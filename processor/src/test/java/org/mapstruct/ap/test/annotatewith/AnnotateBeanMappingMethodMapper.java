/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotatewith;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;

/**
 * @author orange add
 */
@Mapper
public interface AnnotateBeanMappingMethodMapper {

    @AnnotateWith(CustomMethodOnlyAnnotation.class)
    Target map(Source source);

    class Source {

        private  String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    class Target {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}
