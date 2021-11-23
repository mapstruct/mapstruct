/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2668;

import java.util.HashMap;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Ben Zegveld
 */
@Mapper
public interface Erroneous2668MapMapper {

    Erroneous2668MapMapper INSTANCE = Mappers.getMapper( Erroneous2668MapMapper.class );

    CollectionTarget map(CollectionSource source);

    class CollectionTarget {
        MyHashMap map;

        public MyHashMap getMap() {
            return map;
        }

        public void setMap(MyHashMap map) {
            this.map = map;
        }
    }

    class CollectionSource {
        MyHashMap map;

        public MyHashMap getMap() {
            return map;
        }

        public void setMap(MyHashMap map) {
            this.map = map;
        }
    }

    class MyHashMap extends HashMap<String, String> {
        private String unusable;

        public MyHashMap(String unusable) {
            this.unusable = unusable;
        }
    }
}
