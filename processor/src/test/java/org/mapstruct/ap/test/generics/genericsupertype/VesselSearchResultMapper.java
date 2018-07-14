/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.genericsupertype;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Gunnar Morling
 */
@Mapper
public abstract class VesselSearchResultMapper extends MapperBase<Vessel, VesselDto> {

    public static final VesselSearchResultMapper INSTANCE = Mappers.getMapper( VesselSearchResultMapper.class );

    public abstract SearchResult<VesselDto> vesselSearchResultToDto(SearchResult<Vessel> vessel);
}
