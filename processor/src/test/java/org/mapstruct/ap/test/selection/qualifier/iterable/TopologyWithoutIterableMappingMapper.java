/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.iterable;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface TopologyWithoutIterableMappingMapper {

    @Mapping( target = "topologyFeatures", qualifiedBy = Rivers.class )
    TopologyEntity mapTopologyAsRiver(TopologyDto dto);

    @Mapping( target = "topologyFeatures", qualifiedBy = Cities.class )
    TopologyEntity mapTopologyAsCity(TopologyDto dto);

    @Rivers
    default TopologyFeatureEntity mapRiver( TopologyFeatureDto dto ) {
        TopologyFeatureEntity topologyFeatureEntity = null;
        if ( dto instanceof RiverDto ) {
            RiverEntity riverEntity = new RiverEntity();
            riverEntity.setLength( ( (RiverDto) dto ).getLength() );
            topologyFeatureEntity = riverEntity;
            topologyFeatureEntity.setName(  dto.getName() );
        }
        return topologyFeatureEntity;
    }

    @Cities
    default TopologyFeatureEntity mapCity( TopologyFeatureDto dto ) {
        TopologyFeatureEntity topologyFeatureEntity = null;
        if ( dto instanceof CityDto ) {
            CityEntity cityEntity = new CityEntity();
            cityEntity.setPopulation( ( (CityDto) dto ).getPopulation() );
            topologyFeatureEntity = cityEntity;
            topologyFeatureEntity.setName(  dto.getName() );
        }
        return topologyFeatureEntity;
    }

}
