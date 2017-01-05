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
package org.mapstruct.ap.test.callbacks.returning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;

/**
 * @author Pascal Grün
 */
public class NumberMapperContext {
    private static final Map<Number, Number> CACHE = new HashMap<Number, Number>();

    private static final List<Number> VISITED = new ArrayList<Number>();

    private NumberMapperContext() {
        // Only allow static access
    }

    public static void putCache(Number number) {
        CACHE.put( number, number );
    }

    public static void clearCache() {
        CACHE.clear();
    }

    public static List<Number> getVisited() {
        return VISITED;
    }

    public static void clearVisited() {
        VISITED.clear();
    }

    @AfterMapping
    public static Number getInstance(Integer source, @MappingTarget Number target) {
        Number cached = CACHE.get( target );
        return ( cached == null ? null : cached );
    }

    @AfterMapping
    public static <T extends Number> T visitNumber(@MappingTarget T number) {
        VISITED.add( number );
        return number;
    }

    @AfterMapping
    public static Map<String, Integer> withMap(Map<String, Long> source, @MappingTarget Map<String, Integer> target) {
        return target;
    }

    @AfterMapping
    public static List<String> withList(Set<Integer> source, @MappingTarget List<String> target) {
        return target;
    }

    @BeforeMapping
    public static String neverCalled1(Integer integer) {
        throw new IllegalStateException( "This method must never be called, because the return type does not match!" );
    }

    @AfterMapping
    public static String neverCalled2(Integer integer) {
        throw new IllegalStateException( "This method must never be called, because the return type does not match!" );
    }
}
