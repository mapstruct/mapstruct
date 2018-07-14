/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.erroneous;

import java.text.AttributedString;
import java.util.List;

import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
@Mapper(disableSubMappingMethodsGeneration = true)
public interface ErroneousCollectionNoElementMappingFoundDisabledAuto {

    List<String> map(List<AttributedString> source);
}
