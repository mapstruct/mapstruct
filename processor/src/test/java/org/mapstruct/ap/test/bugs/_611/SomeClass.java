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
        }

        class Target {
        }
    }

    public static class SomeInnerClass {
        @Mapper
        public interface InnerMapper {
            InnerMapper INSTANCE = Mappers.getMapper( InnerMapper.class );

            Target toTarget(Source in);

            class Source {
            }

            class Target {
            }
        }
    }
}
