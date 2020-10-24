package org.mapstruct.ap.test.selection.methodgenerics.plain;

import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReturnTypeIsRawTypeMapper {

    ReturnTypeIsRawTypeMapper INSTANCE = Mappers.getMapper( ReturnTypeIsRawTypeMapper.class );

    Target sourceToTarget(Source source);

    Set<String> selectMe(Set<Integer> integers);

    Set<Integer> doNotSelectMe(Set<String> strings);

    class Source {

        private final Set<Integer> prop;

        public Source(Set<Integer> prop) {
            this.prop = prop;
        }

        public Set<Integer> getProp() {
            return prop;
        }
    }

    class Target {

        private Set prop;

        public Set getProp() {
            return prop;
        }

        public Target setProp(Set prop) {
            this.prop = prop;
            return this;
        }
    }
}
