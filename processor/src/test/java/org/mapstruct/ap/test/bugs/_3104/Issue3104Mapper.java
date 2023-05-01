/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3104;

import java.util.Collections;
import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(collectionMappingStrategy = CollectionMappingStrategy.TARGET_IMMUTABLE)
public interface Issue3104Mapper {

    Issue3104Mapper INSTANCE = Mappers.getMapper( Issue3104Mapper.class );

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Target target, Source source);

    class Target {
        private List<Child> children = Collections.emptyList();

        public List<Child> getChildren() {
            return children;
        }

        public void setChildren(List<Child> children) {
            if ( children == null ) {
                throw new IllegalArgumentException( "children is null" );
            }
            this.children = Collections.unmodifiableList( children );
        }
    }

    class Child {
        private String myField;

        public String getMyField() {
            return myField;
        }

        public void setMyField(String myField) {
            this.myField = myField;
        }
    }

    class Source {
        private final List<ChildSource> children;

        public Source(List<ChildSource> children) {
            this.children = children;
        }

        public List<ChildSource> getChildren() {
            return children;
        }

    }

    class ChildSource {
        private final String myField;

        public ChildSource(String myField) {
            this.myField = myField;
        }

        public String getMyField() {
            return myField;
        }
    }
}
