/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.context;

import java.util.Collection;
import java.util.stream.Stream;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;

/**
 * @author Filip Hrisafov
 */
public class StreamContext {

    @BeforeMapping
    public void beforeArrayMapping(Integer[] strings) {
        if ( strings != null && strings.length > 0 ) {
            strings[0] = 30;
        }
    }

    @BeforeMapping
    public void beforeMapping(@MappingTarget Stream<Integer> stream) {

    }

    @BeforeMapping
    public void beforeMapping(@MappingTarget Collection<String> collection) {
        collection.add( "23" );
    }

    @AfterMapping
    public void afterMapping(@MappingTarget Collection<String> collection) {
        collection.add( "230" );
    }

    @AfterMapping
    public Stream<String> afterMapping(@MappingTarget Stream<String> stringStream) {
        return stringStream.limit( 2 );
    }
}
