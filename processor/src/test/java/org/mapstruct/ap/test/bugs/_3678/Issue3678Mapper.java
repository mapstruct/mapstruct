/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3678;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR)
public abstract class Issue3678Mapper {

    abstract SimpleDestination sourceToDestination(SimpleSource source);

    int afterMappingInvocationCount = 0;

    @AfterMapping
    void afterMapping(SimpleSource simpleSource) {
        afterMappingInvocationCount++;
    }

    public int getAfterMappingInvocationCount() {
        return afterMappingInvocationCount;
    }

    public static class SimpleSource {

        private final String name;
        private final String description;

        public SimpleSource(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }

    public static final class SimpleDestination {

        private final String name;
        private final String description;

        private SimpleDestination(String name, String description) {
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

            public SimpleDestination build() {
                return new SimpleDestination( this.name, this.description );
            }
        }
    }

}
