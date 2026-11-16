/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2807.spring.before;

import org.mapstruct.BeforeMapping;

/**
 * @author Ben Zegveld
 */
public class BeforeMethod {
    private BeforeMethod() {
    }

    @BeforeMapping
    public static <T> void doNothing(Iterable<T> source) {
        return;
    }
}
