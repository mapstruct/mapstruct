/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.immutables;

public class TopLevelFixture {

    public static final String CHILD_VALUE = "simple";

    public static TopLevelDto createDto() {
        TopLevelDto.ChildDto child = new TopLevelDto.ChildDto().setString( CHILD_VALUE );
        return new TopLevelDto().setChild( child );
    }
}
