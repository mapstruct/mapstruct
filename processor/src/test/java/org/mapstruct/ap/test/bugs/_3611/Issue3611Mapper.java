/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3611;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(config = Issue3611Mapper.MapstructConfig.class)
public interface Issue3611Mapper {
    Issue3611Mapper INSTANCE = Mappers.getMapper( Issue3611Mapper.class );

    @Mapping(source = "sourceAttribute", target = "targetAttribute")
    Target convert(Source source);

    @InheritInverseConfiguration
    Source inverseConvert(Target target);

    class BaseClass {
        private String type;
        private String baseAttribute;

        public BaseClass() {
        }

        public BaseClass(String type, String baseAttribute) {
            this.type = type;
            this.baseAttribute = baseAttribute;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBaseAttribute() {
            return baseAttribute;
        }

        public void setBaseAttribute(String baseAttribute) {
            this.baseAttribute = baseAttribute;
        }
    }

    class Source extends BaseClass {
        private String sourceAttribute;

        public Source(String type, String baseAttribute, String sourceAttribute) {
            super( type, baseAttribute );
            this.sourceAttribute = sourceAttribute;
        }

        public String getSourceAttribute() {
            return sourceAttribute;
        }

        public void setSourceAttribute(String sourceAttribute) {
            this.sourceAttribute = sourceAttribute;
        }
    }

    class Target extends BaseClass {
        private String targetAttribute;

        public Target(String type, String baseAttribute, String targetAttribute) {
            super( type, baseAttribute );
            this.targetAttribute = targetAttribute;
        }

        public String getTargetAttribute() {
            return targetAttribute;
        }

        public void setTargetAttribute(String targetAttribute) {
            this.targetAttribute = targetAttribute;
        }
    }

    @MapperConfig(
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG
    )
    interface MapstructConfig {

        @Mapping(target = "type", ignore = true)
        BaseClass convert(BaseClass dto);
    }
}
