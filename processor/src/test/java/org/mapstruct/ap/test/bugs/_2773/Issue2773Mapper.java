/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2773;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
/**
 * @author orange add
 */
@Mapper
public interface Issue2773Mapper {

    Issue2773Mapper INSTANCE = Mappers.getMapper(Issue2773Mapper.class);

    @Deprecated
    @TestAnnotation(name = "hello", id = 1, address = {"shenzhen", "guangzhou"})
    @Mapping(target = "name", source = "chartEntry1.recordedAt")
    @Mapping(target = "city", source = "chartEntry2.city")
    Studio toStudio(ChartEntry chartEntry1, ChartEntry chartEntry2);



    @Mappings({
            @Mapping(target = "city",source = "city"),
            @Mapping(target = "name",source = "recordedAt")}
    )
    Studio map(ChartEntry chartEntry);

    @Deprecated
    @IterableMapping(numberFormat = "$#.00")
    List<String> prices(List<Integer> prices);

    @Deprecated
    Set<String> integerStreamToStringSet(Stream<Integer> integers);

    @Deprecated
    @MapMapping(valueDateFormat = "dd.MM.yyyy")
    Map<String, String> longDateMapToStringStringMap(Map<Long, Date> source);

}
