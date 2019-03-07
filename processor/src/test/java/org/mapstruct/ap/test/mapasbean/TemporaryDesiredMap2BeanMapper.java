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
public interface TemporaryDesiredMap2BeanMapper {

    TemporaryDesiredMap2BeanMapper INSTANCE = Mappers.getMapper( TemporaryDesiredMap2BeanMapper.class );

    // manned should be name based (source / target are assumed the same)
    @Mapping( target = "propulsion", source = "propulsionType")
    // string should be name based (source / target are assumed the same)
    RocketDto map(@AsBean Map<String, Object> rocket);

    // leads to builtin method (check whether present already) (here default, must be private)
    default Rocket mapToRocket( Map<String, Object> rocket) {
        return new Rocket( rocket );
    }

    // types should be deepestLevelTypes in SourceReferences
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

    class Rocket {

        private final Map<String, Object> result;

        public Rocket(Map<String, Object> result) {
            this.result = result;
        }

        // name should be camelized target
        public void setPropulsion(Propulsion propulsion) {
            // key should be target
            result.put( "propulsion", propulsion );
        }

        // name should be camelized target
        // what about primitive types? Wrap?
        public void setManned(boolean manned) {
            // key should be target
            result.put( "manned", manned );
        }

        // name should be camelized target
        public void setFuel(String fuel) {
            // key should be target
            result.put( "fuel", fuel );
        }
    }
}
