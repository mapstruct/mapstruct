/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports;

import java.util.Date;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ap.test.imports.referenced.Source;
import org.mapstruct.ap.test.imports.referenced.Target;
import org.mapstruct.ap.test.imports.to.Foo;
import org.mapstruct.factory.Mappers;

/**
 * @author Gunnar Morling
 */
@Mapper(componentModel = MappingConstants.ComponentModel.JSR330)
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    ParseException sourceToTarget(Named source);

    //custom types
    Value<Map> listToMap(List list);

    java.util.List<ParseException> namedsToExceptions(java.util.List<Named> source);

    Foo fooToFoo(org.mapstruct.ap.test.imports.from.Foo foo);

    java.util.List<Date> stringsToDates(java.util.List<String> stringDates);

    java.util.Map<Date, Date> stringsToDates(java.util.Map<String, String> stringDates);

    Target sourceToTarget(Source target);

    class Value<T> {
        private T value;

        public Value(List list) {

        }
    }
}
