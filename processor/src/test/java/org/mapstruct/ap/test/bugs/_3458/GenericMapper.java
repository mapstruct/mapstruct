/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3458;

public abstract class GenericMapper<T1, T2> {

    abstract T1 map(T2 source);
}
