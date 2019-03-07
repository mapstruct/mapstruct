/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mapasbean;

import java.util.Map;

import org.mapstruct.AsBean;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Map2BeanMapper {

    Map2BeanMapper INSTANCE = Mappers.getMapper( Map2BeanMapper.class );

    // manned should be name based (source / target are assumed the same)
    @Mapping( target = "propulsion", source = "propulsionType")
    // string should be name based (source / target are assumed the same)
    RocketDto map(@AsBean Map<String, Object> rocket );


    class RocketDto{

        private boolean manned;
        private Propulsion propulsion;
        private String fuel;

        public boolean isManned() {
            return manned;
        }

        public void setManned(boolean manned) {
            this.manned = manned;
        }

        public Propulsion getPropulsion() {
            return propulsion;
        }

        public void setPropulsion(Propulsion propulsion) {
            this.propulsion = propulsion;
        }

        public String getFuel() {
            return fuel;
        }

        public void setFuel(String fuel) {
            this.fuel = fuel;
        }
    }

    class Propulsion { }
}
