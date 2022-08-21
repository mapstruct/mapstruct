/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2937;

import java.util.ArrayList;
import java.util.Collection;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface Issue2937Mapper {

    Issue2937Mapper INSTANCE = Mappers.getMapper( Issue2937Mapper.class );

    Target map(Source source);

    @Condition
    default boolean isApplicable(Collection<?> collection) {
        return collection == null || collection.size() > 1;
    }

    class Source {
        private final Collection<String> names;

        public Source(Collection<String> names) {
            this.names = names;
        }

        public Collection<String> getNames() {
            return names;
        }

//        public boolean hasNames() {
//            return names.size() > 1;
//        }
    }

    class Target {
        private final Collection<String> names;

        public Target() {
            this.names = new ArrayList<>();
        }

        public Collection<String> getNames() {
            return names;
        }

        public void addName(String name) {
            this.names.add( name );
        }
    }
}
