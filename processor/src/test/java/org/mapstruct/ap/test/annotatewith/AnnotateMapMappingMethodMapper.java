/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotatewith;

import org.mapstruct.AnnotateWith;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;

import java.util.Date;
import java.util.Map;

/**
 * @author orange add
 */
@Mapper
public interface AnnotateMapMappingMethodMapper {

    @MapMapping(valueDateFormat = "dd.MM.yyyy")
    @AnnotateWith(CustomMethodOnlyAnnotation.class)
    Map<String, String> longDateMapToStringStringMap(Map<Long, Date> source);
}
