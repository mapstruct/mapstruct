/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.handwritten;

import java.util.Map;

import org.mapstruct.Named;
import org.mapstruct.ap.test.selection.qualifier.annotation.EnglishToGerman;
import org.mapstruct.ap.test.selection.qualifier.annotation.TitleTranslator;

import com.google.common.collect.ImmutableMap;

/**
 *
 * @author Sjaak Derksen
 */
@TitleTranslator
@Named( "TitleTranslator" )
public class Titles {

    private static final Map<String, String> EN_GER = ImmutableMap.<String, String>builder()
            .put( "Star Wars", "Krieg der Sterne" )
            .put( "Sixth Sense, The", "Der sechste Sinn" )
            .put( "Shawshank Redemption, The", "Die Verurteilten" )
            .put( "Trainspotting", "Neue Helden" )
            .put( "Never Say Never", "Sag niemals nie" )
            .build();

    @EnglishToGerman
    @Named( "EnglishToGerman" )
    public String translateTitle(String title) {
        return EN_GER.get( title );
    }

    public String methodNotToSelect( String title ) {
        throw new AssertionError( "method should not be called" );
    }
}
