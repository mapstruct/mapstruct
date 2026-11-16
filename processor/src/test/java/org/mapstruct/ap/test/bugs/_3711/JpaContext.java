/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3711;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;

public class JpaContext<T> {
    private final List<String> invokedMethods = new ArrayList<>();

    @BeforeMapping
    void beforeMapping(@MappingTarget T parentEntity) {
        invokedMethods.add( "beforeMapping" );
    }

    @AfterMapping
    void afterMapping(@MappingTarget T parentEntity) {
        invokedMethods.add( "afterMapping" );
    }

    public List<String> getInvokedMethods() {
        return invokedMethods;
    }
}
