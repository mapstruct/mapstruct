/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.adder;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

/**
 * @author Sjaak Derksen
 */
public class TeethMapper {

    private static final Map<String, Integer> TEETH = ImmutableMap.<String, Integer>builder()
        .put( "incisor", 1 )
        .put( "canine", 2 )
        .put( "moler", 3 ).build();

    public Integer toTooth(String tooth) {
        return TEETH.get( tooth );
    }
}
