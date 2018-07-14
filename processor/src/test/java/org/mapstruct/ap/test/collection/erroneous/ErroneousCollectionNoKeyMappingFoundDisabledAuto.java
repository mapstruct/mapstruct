/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.erroneous;

import java.text.AttributedString;
import java.util.Map;

import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
@Mapper(disableSubMappingMethodsGeneration = true)
public interface ErroneousCollectionNoKeyMappingFoundDisabledAuto {

    Map<String, String> map(Map<AttributedString, String> source);
}
