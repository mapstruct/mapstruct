/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2807.spring.after;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;

/**
 * @author Ben Zegveld
 */
public class AfterMethod {
    private AfterMethod() {
    }

    @AfterMapping
    public static <D> void doNothing(@MappingTarget List<D> source) {
    }
}
