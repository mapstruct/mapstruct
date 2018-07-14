/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.iterable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Qualifier;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public abstract class TopologyMapper {

    public static final TopologyMapper INSTANCE = Mappers.getMapper( TopologyMapper.class );

    @Mapping( target = "topologyFeatures", qualifiedBy = Rivers.class )
    public abstract TopologyEntity mapTopologyAsRiver(TopologyDto dto);

    @Mapping( target = "topologyFeatures", qualifiedBy = Cities.class )
    public abstract TopologyEntity mapTopologyAsCity(TopologyDto dto);

    @Rivers
    protected TopologyFeatureEntity mapRiver( TopologyFeatureDto dto ) {
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
    protected TopologyFeatureEntity mapCity( TopologyFeatureDto dto ) {
        TopologyFeatureEntity topologyFeatureEntity = null;
        if ( dto instanceof CityDto ) {
            CityEntity cityEntity = new CityEntity();
            cityEntity.setPopulation( ( (CityDto) dto ).getPopulation() );
            topologyFeatureEntity = cityEntity;
            topologyFeatureEntity.setName(  dto.getName() );
        }
        return topologyFeatureEntity;
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    public @interface Rivers {
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    public @interface Cities {
    }

}
