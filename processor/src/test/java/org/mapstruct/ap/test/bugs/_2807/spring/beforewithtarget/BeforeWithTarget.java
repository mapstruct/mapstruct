/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2807.spring.beforewithtarget;

import java.util.List;

import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;

/**
 * @author Ben Zegveld
 */
public class BeforeWithTarget {
    private BeforeWithTarget() {
    }

    @BeforeMapping
    public static <T, D> void doNothingBeforeWithTarget(Iterable<T> source, @MappingTarget List<D> target) {
    }
}
