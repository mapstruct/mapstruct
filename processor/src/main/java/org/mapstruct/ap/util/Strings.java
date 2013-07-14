/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.util;

/**
 * Helper class for dealing with strings.
 *
 * @author Gunnar Morling
 */
public class Strings {

    private Strings() {
    }

    public static String capitalize(String name) {
        return name == null ? null : name.substring( 0, 1 ).toUpperCase() + name.substring( 1 );
    }

    public static String join(Iterable<?> iterable, String separator) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;

        for ( Object object : iterable ) {
            if ( !isFirst ) {
                sb.append( separator );
            }
            else {
                isFirst = false;
            }

            sb.append( object );
        }

        return sb.toString();
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
