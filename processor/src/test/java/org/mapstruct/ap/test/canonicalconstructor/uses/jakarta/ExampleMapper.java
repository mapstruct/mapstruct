/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.canonicalconstructor.uses.jakarta;

import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Singleton
@Named
public class ExampleMapper {

    private int number = 404;

    public Integer map(Integer entity) {
        return number;
    }

}
