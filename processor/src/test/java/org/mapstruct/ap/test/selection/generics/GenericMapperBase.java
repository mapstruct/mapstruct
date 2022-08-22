/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

public interface GenericMapperBase<T> {

    T toDto(Map<String, Property> prop);

    @Named( "StringArrayToString" )
    static String stringArrayToString(Property property) {
        return "converted";
    }
}

class Property {

}

class ProjectDto {
    private String productionSite;

    public void setProductionSite(String productionSite) {
        this.productionSite = productionSite;
    }

    public String getProductionSite() {
        return productionSite;
    }
}

@Mapper
interface ProjectMapper extends GenericMapperBase<ProjectDto> {
    ProjectMapper INSTANCE = Mappers.getMapper( ProjectMapper.class );

    @Mapping( target = "productionSite", source = "productionSite", qualifiedByName = "StringArrayToString" )
    @Override
    ProjectDto toDto(Map<String, Property> projectProp);
}
