/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2983;

import org.mapstruct.AnnotateWith;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;

import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;
import org.mapstruct.ap.test.annotatewith.CustomAnnotation;
import org.mapstruct.ap.test.annotatewith.CustomMethodOnlyAnnotation;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author orange add
 */
@Mapper
public interface Issue2983Mapper {

    Issue2983Mapper INSTANCE = Mappers.getMapper( Issue2983Mapper.class );

    @AnnotateWith(Deprecated.class)
    Target map(Source source);

    @AnnotateWith(Deprecated.class)
    List<String> toStringList(List<Integer> integers);

    @AnnotateWith(Deprecated.class)
    Stream<String> toStringStream(Stream<Integer> integerStream);

    @MapMapping(valueDateFormat = "dd.MM.yyyy")
    @AnnotateWith(Deprecated.class)
    @AnnotateWith(CustomAnnotation.class)
    Map<String, String> longDateMapToStringStringMap(Map<Long, Date> source);

    @ValueMappings({
            @ValueMapping(target = "SPECIAL", source = "EXTRA"),
            @ValueMapping(target = "DEFAULT", source = "STANDARD"),
            @ValueMapping(target = "DEFAULT", source = "NORMAL")
    })
    @AnnotateWith(Deprecated.class)
    @AnnotateWith(CustomMethodOnlyAnnotation.class)
    ExternalOrderType orderTypeToExternalOrderType(OrderType orderType);

}
