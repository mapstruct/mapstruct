/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2537;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * @author Ben Zegveld
 */
@Mapper( unmappedSourcePolicy = ReportingPolicy.ERROR )
public interface UnmappedSourcePolicyWithImplicitSourceMapper {
    @BeanMapping( ignoreByDefault = true )
    @Mapping( target = "property" )
    Target map(Source source);

    class Source {
        private String property;

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }
    }

    class Target {
        private String property;

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }
    }
}
