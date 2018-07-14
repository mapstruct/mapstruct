/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.named;

import java.util.List;
import java.util.Map;

import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.selection.qualifier.handwritten.Facts;
import org.mapstruct.ap.test.selection.qualifier.handwritten.PlotWords;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( uses = { Facts.class, PlotWords.class } )
public interface FactMapper {

    FactMapper INSTANCE = Mappers.getMapper( FactMapper.class );

    @MapMapping( keyQualifiedByName =  "EnglishToGerman", valueQualifiedByName = "EnglishToGerman" )
    Map<String, List<String>> mapFacts( Map<String, List<String>> keyWords );

}
