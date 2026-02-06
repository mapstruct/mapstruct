/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.canonicalconstructor.uses.defaultcomponentmodel.otherclass;

public class ExampleMapper {

    private int number = 404;

    public Integer map(Integer entity) {
        return number;
    }

}
