/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.writer.formatter;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.internal.formatter.DefaultCodeFormatter;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;

/**
 * Provides means to format Java source code.
 *
 * @author Andreas Gudian
 */
public class Formatter {
    private Formatter() {
    }

    /**
     * Formats the given source code using the Eclipse formatter.
     *
     * @param eclipseFormatterOptions Formatter options
     * @param source the complete source file content
     * @return the formatted source code
     */
    public static String formatSource(Map<String, String> eclipseFormatterOptions, String source) {
        CodeFormatter formatter = new DefaultCodeFormatter( translateToShadedOptions( eclipseFormatterOptions ) );

        TextEdit formatted = formatter.format( CodeFormatter.K_COMPILATION_UNIT, source, 0, source.length(), 0, null );

        Document doc = new Document( source );
        try {
            formatted.apply( doc );
        }
        catch ( Exception e ) {
            throw new RuntimeException( e );
        }

        return doc.get();
    }

    private static Map<String, String> translateToShadedOptions(Map<String, String> originalOptions) {
        String shadingPackage = DefaultCodeFormatter.class.getPackage().getName().replaceAll( "org\\.eclipse.*$", "" );

        if ( shadingPackage.isEmpty() ) {
            return originalOptions;
        }

        Map<String, String> result = new HashMap<String, String>( originalOptions.size() * 3 / 4 );

        for ( Entry<String, String> e : originalOptions.entrySet() ) {
            if ( e.getKey().startsWith( shadingPackage ) ) {
                result.put( e.getKey(), e.getValue() );
            }
            else {
                result.put( shadingPackage + e.getKey(), e.getValue() );
            }
        }

        return result;
    }
}
