/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2668;

import java.util.ArrayList;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Ben Zegveld
 */
@Mapper
public interface Erroneous2668ListMapper {

    Erroneous2668ListMapper INSTANCE = Mappers.getMapper( Erroneous2668ListMapper.class );

    CollectionTarget map(CollectionSource source);

    class CollectionTarget {
        MyArrayList list;

        public MyArrayList getList() {
            return list;
        }

        public void setList(MyArrayList list) {
            this.list = list;
        }
    }

    class CollectionSource {
        MyArrayList list;

        public MyArrayList getList() {
            return list;
        }

        public void setList(MyArrayList list) {
            this.list = list;
        }
    }

    class MyArrayList extends ArrayList<String> {
        private String unusable;

        public MyArrayList(String unusable) {
            this.unusable = unusable;
        }
    }
}
