/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.imports;

import java.util.Date;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.imports.referenced.Source;
import org.mapstruct.ap.test.imports.referenced.Target;
import org.mapstruct.ap.test.imports.to.Foo;
import org.mapstruct.factory.Mappers;

/**
 * @author Gunnar Morling
 */
@Mapper(componentModel = "jsr330")
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    ParseException sourceToTarget(Named source);

    //custom types
    Map listToMap(List list);

    java.util.List<ParseException> namedsToExceptions(java.util.List<Named> source);

    Foo fooToFoo(org.mapstruct.ap.test.imports.from.Foo foo);

    java.util.List<Date> stringsToDates(java.util.List<String> stringDates);

    java.util.Map<Date, Date> stringsToDates(java.util.Map<String, String> stringDates);

    Target sourceToTarget(Source target);
}
