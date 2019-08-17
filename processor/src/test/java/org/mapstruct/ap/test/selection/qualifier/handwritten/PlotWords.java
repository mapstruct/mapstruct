/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.handwritten;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mapstruct.ap.test.selection.qualifier.annotation.EnglishToGerman;

import com.google.common.collect.ImmutableMap;
import org.mapstruct.Named;

/**
 *
 * @author Sjaak Derksen
 */
public class PlotWords {

    private static final Map<String, String> EN_GER = ImmutableMap.<String, String>builder()
            .put( "boy", "Jungen" )
            .put( "child psychologist", "Kinderpsychologe" )
            .put( "I see dead people", "Ich sehe tote Menschen" )
            .build();

    @EnglishToGerman
    @Named( "EnglishToGerman"  )
    public List<String> translate( List<String> keywords ) {
        List<String> result = new ArrayList<>();
        for ( String keyword : keywords ) {
            result.add( EN_GER.getOrDefault( keyword, keyword ) );
        }
        return result;
    }

    public List<String> methodNotToSelect( List<String> title ) {
        throw new AssertionError( "method should not be called" );
    }

}
