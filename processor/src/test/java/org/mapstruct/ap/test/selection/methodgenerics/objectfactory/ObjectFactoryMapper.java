/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.methodgenerics.objectfactory;

import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.TargetType;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ObjectFactoryMapper {

    ObjectFactoryMapper INSTANCE = Mappers.getMapper( ObjectFactoryMapper.class );

    TargetA toTarget(SourceA source);

    @ObjectFactory
    default <T extends Target, S extends Source> T createTarget(S source, @TargetType Class<T> targetType) {
        if ( source.isA() ) {
            return (T) new TargetA();
        }
        return (T) new TargetB();
    }

    abstract class Source {
        public abstract boolean isA();
    }

    class SourceA extends Source {
        @Override
        public boolean isA() {
            return true;
        }
    }

    class SourceB extends Source {
        @Override
        public boolean isA() {
            return false;
        }
    }

    abstract class Target {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    class TargetA extends Target {

        private TargetA() {
        }
    }

    class TargetB extends Target {

        private TargetB() {
        }
    }

}
