/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.exceptions;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.exceptions.imports.TestExceptionBase;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( uses = ExceptionTestMapper.class )
@DecoratedWith( ExceptionTestDecorator.class )
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    Target sourceToTarget(Source source) throws TestException2, ParseException;

    List<Long> integerListToLongList(List<Integer> sizes) throws TestException2;

    Map<Long, String> integerKeyMapToLongKeyMap(Map<Integer, String> sizes) throws TestException2;

    Map<String, Long> integerValueMapToLongValueMap(Map<String, Integer> sizes) throws TestException2;

    Target sourceToTargetViaBaseException(Source source) throws TestExceptionBase;

}
