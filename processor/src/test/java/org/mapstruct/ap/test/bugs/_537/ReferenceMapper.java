/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._537;

/**
 * @author Christian Bandowski
 */
public class ReferenceMapper {
    public Integer stringLength(String source) {
        return source == null ? null : source.length();
    }
}
