/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3458;

public class IdentityMapper<T> extends GenericMapper<T, T> {

    @Override
    T map(T source) {
        return source;
    }
}
