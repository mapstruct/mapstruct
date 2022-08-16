package org.mapstruct.ap.test.bugs._2773;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

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
}
