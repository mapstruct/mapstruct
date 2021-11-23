/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2668;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Ben Zegveld
 */
@Mapper
public interface Issue2668Mapper {

    Issue2668Mapper INSTANCE = Mappers.getMapper( Issue2668Mapper.class );

    CollectionTarget map(CollectionSource source);

    class CollectionTarget {
        MyArrayList list;
        MyHashMap map;
        MyCopyArrayList copyList;
        MyCopyHashMap copyMap;

        public MyArrayList getList() {
            return list;
        }

        public MyHashMap getMap() {
            return map;
        }

        public void setList(MyArrayList list) {
            this.list = list;
        }

        public void setMap(MyHashMap map) {
            this.map = map;
        }

        public MyCopyArrayList getCopyList() {
            return copyList;
        }

        public MyCopyHashMap getCopyMap() {
            return copyMap;
        }

        public void setCopyList(MyCopyArrayList copyList) {
            this.copyList = copyList;
        }

        public void setCopyMap(MyCopyHashMap copyMap) {
            this.copyMap = copyMap;
        }
    }

    class CollectionSource {
        MyArrayList list;
        MyHashMap map;
        MyCopyArrayList copyList;
        MyCopyHashMap copyMap;

        public MyArrayList getList() {
            return list;
        }

        public MyHashMap getMap() {
            return map;
        }

        public void setList(MyArrayList list) {
            this.list = list;
        }

        public void setMap(MyHashMap map) {
            this.map = map;
        }

        public MyCopyArrayList getCopyList() {
            return copyList;
        }

        public MyCopyHashMap getCopyMap() {
            return copyMap;
        }

        public void setCopyList(MyCopyArrayList copyList) {
            this.copyList = copyList;
        }

        public void setCopyMap(MyCopyHashMap copyMap) {
            this.copyMap = copyMap;
        }
    }

    class MyArrayList extends ArrayList<String> {
    }

    class MyHashMap extends HashMap<String, String> {
    }

    class MyCopyArrayList extends ArrayList<String> {
        public MyCopyArrayList(Collection<String> copy) {
            super( copy );
        }
    }

    class MyCopyHashMap extends HashMap<String, String> {
        public MyCopyHashMap(HashMap<String, String> copy) {
            super( copy );
        }
    }
}
