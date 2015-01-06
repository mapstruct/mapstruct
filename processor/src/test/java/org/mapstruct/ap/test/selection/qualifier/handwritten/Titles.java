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
package org.mapstruct.ap.test.selection.qualifier.handwritten;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.mapstruct.ap.test.selection.qualifier.annotation.EnglishToGerman;
import org.mapstruct.ap.test.selection.qualifier.annotation.TitleTranslator;
//import org.mapstruct.ap.test.selection.qualifier.annotation.TitleTranslator;

/**
 *
 * @author Sjaak Derksen
 */
@TitleTranslator
public class Titles {

    private static final Map<String, String> EN_GER = ImmutableMap.<String, String>builder()
            .put( "Star Wars", "Krieg der Sterne" )
            .put( "Sixth Sense, The", "Der sechste Sinn" )
            .put( "Shawshank Redemption, The", "Die Verurteilten" )
            .put( "Trainspotting", "Neue Helden" )
            .put( "Never Say Never", "Sag niemals nie" )
            .build();

    @EnglishToGerman
    public String translateTitle(String title) {
        return EN_GER.get( title );
    }


    public String methodNotToSelect( String title ) {
        throw new AssertionError( "method should not be called" );
    }

}
