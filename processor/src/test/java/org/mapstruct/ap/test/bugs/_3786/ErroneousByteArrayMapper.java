/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3786;

import org.mapstruct.Mapper;

@Mapper
public interface ErroneousByteArrayMapper {
    byte[] map( String something );
}
