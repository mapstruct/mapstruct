/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._590;

import java.util.logging.XMLFormatter;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.TargetType;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN)
public abstract class ErroneousSourceTargetMapper {
    public static final ErroneousSourceTargetMapper INSTANCE = Mappers.getMapper( ErroneousSourceTargetMapper.class );

    public abstract void sourceToTarget(@MappingTarget Target target, Source source);

    public <T extends Target> T unused(String string, @TargetType Class<T> clazz) {
        throw new RuntimeException( "should never be called" );
    }

    public static class Source {
        private String prop;

        public String getProp() {
            return prop;
        }

        public void setProp(String prop) {
            this.prop = prop;
        }
    }

    public static class Target {
        private XMLFormatter prop;

        public XMLFormatter getProp() {
            return prop;
        }

        public void setProp(XMLFormatter prop) {
            this.prop = prop;
        }
    }
}
