/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3361;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class Issue3361Mapper {

    public static final Issue3361Mapper INSTANCE = Mappers.getMapper( Issue3361Mapper.class );

    @Mapping(target = "someAttribute", source = "source.attribute")
    @Mapping(target = "otherAttribute", source = "otherSource.anotherAttribute")
    public abstract Target mapFromSource(Source source, OtherSource otherSource);

    @InheritConfiguration(name = "mapFromSource")
    @Mapping(target = "otherAttribute", source = "source", qualifiedByName = "otherMapping")
    public abstract Target mapInherited(Source source, OtherSource otherSource);

    @Named("otherMapping")
    protected Long otherMapping(Source source) {
        return source.getAttribute() != null ? 1L : 0L;
    }

    public static class Target {
        private String someAttribute;
        private Long otherAttribute;

        public String getSomeAttribute() {
            return someAttribute;
        }

        public Target setSomeAttribute(String someAttribute) {
            this.someAttribute = someAttribute;
            return this;
        }

        public Long getOtherAttribute() {
            return otherAttribute;
        }

        public Target setOtherAttribute(Long otherAttribute) {
            this.otherAttribute = otherAttribute;
            return this;
        }
    }

    public static class Source {
        private final String attribute;

        public Source(String attribute) {
            this.attribute = attribute;
        }

        public String getAttribute() {
            return attribute;
        }
    }

    public static class OtherSource {

        private final Long anotherAttribute;

        public OtherSource(Long anotherAttribute) {
            this.anotherAttribute = anotherAttribute;
        }

        public Long getAnotherAttribute() {
            return anotherAttribute;
        }
    }

}
