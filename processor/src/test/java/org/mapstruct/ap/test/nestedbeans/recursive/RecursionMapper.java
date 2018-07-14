/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.recursive;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class RecursionMapper {

    public static final RecursionMapper INSTANCE = Mappers.getMapper( RecursionMapper.class );

    public abstract Root mapRoot(RootDto rootDto);

    public static class Root {
        private Child child;

        public Root() {
        }

        public Root(Child child) {
            this.child = child;
        }

        public Child getChild() {
            return child;
        }

        public void setChild(Child child) {
            this.child = child;
        }

    }

    public static class Child {
        private String name;
        private Child child;

        public Child() {
        }

        public Child(String name, Child child) {
            this.name = name;
            this.child = child;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Child getChild() {
            return child;
        }

        public void setChild(Child child) {
            this.child = child;
        }

    }

    public static class RootDto {
        private ChildDto child;

        public RootDto() {
        }

        public RootDto(ChildDto child) {
            this.child = child;
        }

        public ChildDto getChild() {
            return child;
        }

        public void setChild(ChildDto child) {
            this.child = child;
        }

    }

    public static class ChildDto {
        private String name;
        private ChildDto child;

        public ChildDto() {
        }

        public ChildDto(String name, ChildDto child) {
            this.name = name;
            this.child = child;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ChildDto getChild() {
            return child;
        }

        public void setChild(ChildDto child) {
            this.child = child;
        }

    }
}
