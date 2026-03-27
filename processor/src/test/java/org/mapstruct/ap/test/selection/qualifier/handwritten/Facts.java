/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.handwritten;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import org.mapstruct.Named;
import org.mapstruct.ap.test.selection.qualifier.annotation.EnglishToGerman;

/**
 *
 * @author Sjaak Derksen
 */
public class Facts {

    private static final Map<String, String> EN_GER = ImmutableMap.<String, String>builder()
            .put( "director", "Regisseur" )
            .put( "cast", "Besetzung" )
            .put( "cameo", "Kurzauftritt" )
            .put( "soundtrack", "Filmmusik" )
            .put( "plot keywords", "Handlungstichwörter" )
            .build();

    @EnglishToGerman
    @Named( "EnglishToGerman"  )
    public String translateFactName( String fact ) {
        String result = EN_GER.get( fact );
        return result != null ? result : fact;
    }

}
