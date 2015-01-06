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
    public String translateFactName( String fact ) {
        String result = EN_GER.get( fact );
        return result != null ? result : fact;
    }

}
