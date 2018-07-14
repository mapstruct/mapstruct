/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.erroneous;

import java.text.AttributedString;
import java.util.List;
import java.util.stream.Stream;

import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
@Mapper(disableSubMappingMethodsGeneration = true)
public interface ErroneousStreamToListNoElementMappingFoundDisabledAuto {

    List<String> mapStreamToCollection(Stream<AttributedString> source);
}
