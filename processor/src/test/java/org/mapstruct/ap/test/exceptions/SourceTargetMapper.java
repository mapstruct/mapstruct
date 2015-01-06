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
package org.mapstruct.ap.test.exceptions;


import java.text.ParseException;
import org.mapstruct.ap.test.exceptions.imports.TestExceptionBase;
import java.util.List;
import java.util.Map;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( uses = ExceptionTestMapper.class )
@DecoratedWith( ExceptionTestDecorator.class )
public  interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    Target sourceToTarget(Source source) throws TestException2, ParseException;

    List<Long> integerListToLongList(List<Integer> sizes) throws TestException2;

    Map<Long, String> integerKeyMapToLongKeyMap(Map<Integer, String> sizes) throws TestException2;

    Map<String, Long> integerValueMapToLongValueMap(Map<String, Integer> sizes) throws TestException2;

    Target sourceToTargetViaBaseException(Source source) throws TestExceptionBase;

}
