/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.multisource;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MultiSourceMapper {

    MultiSourceMapper INSTANCE = Mappers.getMapper( MultiSourceMapper.class );

    Target mapFromPrimitiveAndCollection(int value, Collection<String> elements);

    Target mapFromCollectionAndPrimitive(Collection<String> elements, int value);

    class Target {
        private int value;
        private Collection<String> elements;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Collection<String> getElements() {
            return elements;
        }

        public void setElements(Collection<String> elements) {
            this.elements = elements;
        }
    }
}
