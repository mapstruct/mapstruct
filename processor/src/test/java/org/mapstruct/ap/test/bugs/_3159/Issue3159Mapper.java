/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3159;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue3159Mapper {

    Issue3159Mapper INSTANCE = Mappers.getMapper( Issue3159Mapper.class );

    @Mapping(target = "elements", defaultExpression = "java(new ArrayList<>())")
    Target map(Source source);

    default String elementName(Element element) {
        return element != null ? element.getName() : null;
    }

    class Target {
        private final Collection<String> elements;

        public Target(Collection<String> elements) {
            this.elements = elements;
        }

        public Collection<String> getElements() {
            return elements;
        }
    }

    class Source {
        private final Collection<Element> elements;

        public Source(Collection<Element> elements) {
            this.elements = elements;
        }

        public Collection<Element> getElements() {
            return elements;
        }
    }

    class Element {
        private final String name;

        public Element(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
