/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3711;

import org.mapstruct.Context;

interface BaseMapper<T, E> {
    E toEntity(T s, @Context JpaContext<E> ctx);
}
