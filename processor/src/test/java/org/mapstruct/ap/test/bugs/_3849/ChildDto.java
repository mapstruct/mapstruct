/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3849;

public class ChildDto extends ParentDto {
    public ChildDto(String value) {
        super( value );
    }

    public void setValue(String value) {
        super.setValue( value );
    }
}
