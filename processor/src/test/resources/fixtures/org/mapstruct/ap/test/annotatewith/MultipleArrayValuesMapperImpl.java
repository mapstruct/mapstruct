/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotatewith;

import javax.annotation.processing.Generated;
import org.mapstruct.Mapper;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-06-06T16:48:23+0200",
    comments = "version: , compiler: javac, environment: Java 11.0.12 (Azul Systems, Inc.)"
)
@CustomAnnotationWithParams(
    stringArray = { "test1", "test2" },
    booleanArray = { false, true },
    byteArray = { 8, 31 },
    charArray = { 'b', 'c' },
    doubleArray = { 1.2, 3.4 },
    floatArray = { 1.2000000476837158f, 3.4000000953674316f },
    intArray = { 12, 34 },
    longArray = { 12L, 34L },
    shortArray = { 12, 34 },
    classArray = { Mapper.class, CustomAnnotationWithParams.class },
    stringParam = "required parameter"
)
public class MultipleArrayValuesMapperImpl implements MultipleArrayValuesMapper {
}
