/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3936.jdk17;

import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

@Mapper(subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)
public interface Issue3936Mapper {

    Issue3936Mapper INSTANCE = Mappers.getMapper( Issue3936Mapper.class );

    @SubclassMapping(source = From.From1.class, target = To.class)
    @SubclassMapping(source = From.From2.class, target = To.class)
    To map(From from);

    sealed interface From {
        record From1(Integer a) implements From {
        }

        record From2(Integer a) implements From {
        }
    }

    record To(Integer a) {
    }
}
