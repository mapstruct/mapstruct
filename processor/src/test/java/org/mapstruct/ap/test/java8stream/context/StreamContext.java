/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
