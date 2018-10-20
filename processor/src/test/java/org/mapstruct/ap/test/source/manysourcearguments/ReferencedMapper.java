/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.manysourcearguments;

import org.mapstruct.BeforeMapping;

/**
 * @author Andreas Gudian
 *
 */
public class ReferencedMapper {
    private static boolean beforeMappingCalled = false;

    @BeforeMapping
    public void beforeMappingCallback(Person person, Address address) {
        beforeMappingCalled = ( person != null && address != null );
    }

    public static boolean isBeforeMappingCalled() {
        return beforeMappingCalled;
    }

    public static void setBeforeMappingCalled(boolean beforeMappingCalled) {
        ReferencedMapper.beforeMappingCalled = beforeMappingCalled;
    }
}
