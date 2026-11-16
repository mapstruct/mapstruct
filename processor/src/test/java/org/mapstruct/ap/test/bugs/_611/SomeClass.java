/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._611;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Tillmann Gaida
 */
public class SomeClass {
    @Mapper
    public interface InnerMapper {
        InnerMapper INSTANCE = Mappers.getMapper( InnerMapper.class );

        Target toTarget(Source in);

        class Source {

            private final String value;

            public Source(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }
        }

        class Target {

            private final String value;

            public Target(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }
        }
    }

    public static class SomeInnerClass {
        @Mapper
        public interface InnerMapper {
            InnerMapper INSTANCE = Mappers.getMapper( InnerMapper.class );

            Target toTarget(Source in);

            class Source {

                private final String value;

                public Source(String value) {
                    this.value = value;
                }

                public String getValue() {
                    return value;
                }
            }

            class Target {

                private final String value;

                public Target(String value) {
                    this.value = value;
                }

                public String getValue() {
                    return value;
                }
            }
        }
    }
}
