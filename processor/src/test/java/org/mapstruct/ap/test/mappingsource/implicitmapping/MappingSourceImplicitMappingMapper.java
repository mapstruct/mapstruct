/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingsource.implicitmapping;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.MappingSource;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MappingSourceImplicitMappingMapper {

    MappingSourceImplicitMappingMapper INSTANCE = Mappers.getMapper( MappingSourceImplicitMappingMapper.class );

    MapTarget multiSourceWithImplicitMap(@MappingSource Map<String, String> mapSource, OtherSource otherSource);

    BeanTarget multiSourceWithImplicitBean(@MappingSource(implicitMapping = false) BeanSource beanSource,
                                           OtherSource otherSource);

    MapTarget singleWithImplicitMap(@MappingSource(implicitMapping = false) Map<String, String> map);

    class MapTarget {
        private String mapId;
        private String mapName;

        public String getMapId() {
            return mapId;
        }

        public void setMapId(String mapId) {
            this.mapId = mapId;
        }

        public String getMapName() {
            return mapName;
        }

        public void setMapName(String mapName) {
            this.mapName = mapName;
        }
    }

    class BeanTarget {
        private Integer beanId;
        private String beanName;

        public Integer getBeanId() {
            return beanId;
        }

        public void setBeanId(Integer beanId) {
            this.beanId = beanId;
        }

        public String getBeanName() {
            return beanName;
        }

        public void setBeanName(String beanName) {
            this.beanName = beanName;
        }
    }

    class BeanSource {
        private Integer beanId;
        private String beanName;

        public Integer getBeanId() {
            return beanId;
        }

        public void setBeanId(Integer beanId) {
            this.beanId = beanId;
        }

        public String getBeanName() {
            return beanName;
        }

        public void setBeanName(String beanName) {
            this.beanName = beanName;
        }
    }

    class OtherSource {
        private final Long id;

        public OtherSource(Long id) {
            this.id = id;
        }

        public Long getId() {
            return id;
        }
    }
}
