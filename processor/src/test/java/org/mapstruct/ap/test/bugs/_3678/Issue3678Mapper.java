/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3678;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class Issue3678Mapper {

    @Mapping( target = "name", source = "sourceA.name")
    @Mapping( target = "description", source = "sourceB.description")
    abstract Target mapTwoSources(SourceA sourceA, SourceB sourceB);

    @Mapping( target = "description", constant = "some description")
    abstract Target mapSingleSource(SourceA sourceA);

    List<String> invocations = new ArrayList<>();

    @BeforeMapping
    void beforeMappingSourceA(SourceA sourceA) {
        invocations.add( "beforeMappingSourceA" );
    }

    @AfterMapping
    void afterMappingSourceB(SourceA sourceA) {
        invocations.add( "afterMappingSourceA" );
    }

    @BeforeMapping
    void beforeMappingSourceB(SourceB sourceB) {
        invocations.add( "beforeMappingSourceB" );
    }

    @AfterMapping
    void afterMappingSourceB(SourceB sourceB) {
        invocations.add( "afterMappingSourceB" );
    }

    public List<String> getInvocations() {
        return invocations;
    }

    public static class SourceA {

        private final String name;

        public SourceA(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static class SourceB {

        private final String description;

        public SourceB(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public static final class Target {

        private final String name;
        private final String description;

        private Target(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {

            private String name;
            private String description;

            public Builder() {
            }

            public Builder name(String name) {
                this.name = name;
                return this;
            }

            public Builder description(String description) {
                this.description = description;
                return this;
            }

            public Target build() {
                return new Target( this.name, this.description );
            }
        }
    }

}
