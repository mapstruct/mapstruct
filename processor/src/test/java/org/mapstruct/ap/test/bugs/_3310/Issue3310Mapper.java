/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3310;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface Issue3310Mapper {

    Issue3310Mapper INSTANCE = Mappers.getMapper( Issue3310Mapper.class );

    Target map(Source source);

    abstract class BaseClass<T> {

        private List<T> items;

        public List<T> getItems() {
            return items;
        }

        public void setItems(List<T> items) {
            throw new UnsupportedOperationException( "adder should be used instead" );
        }

        public void addItem(T item) {
            if ( items == null ) {
                items = new ArrayList<>();
            }
            items.add( item );
        }
    }

    class Target extends BaseClass<String> {

    }

    class Source {

        private final List<String> items;

        public Source(List<String> items) {
            this.items = items;
        }

        public List<String> getItems() {
            return items;
        }
    }
}
