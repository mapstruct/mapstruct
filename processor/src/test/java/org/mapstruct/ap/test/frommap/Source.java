/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.frommap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Christian Kosmowski
 */
public class Source {

    // CHECKSTYLE:OFF
    public final List<Integer> finalList = Arrays.asList( 1, 2, 3 );
    public List<Integer> normalList;
    public Integer fieldOnlyWithGetter;
    // CHECKSTYLE:ON

    private final List<Integer> privateFinalList = new ArrayList<>( Arrays.asList( 3, 4, 5 ) );

    public List<Integer> getPrivateFinalList() {
        return privateFinalList;
    }

    public Integer getFieldOnlyWithGetter() {
        return fieldOnlyWithGetter + 21;
    }
}
