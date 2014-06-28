/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

/**
 * Work-around for a bug related to sub-typing in the Eclipse JSR 269 implementation.
 * @author Sjaak Derksen
 */
public class TypeUtilsJDK6Fix {

    private TypeUtilsJDK6Fix() { }

    /**
     * Tests whether one type is a subtype of another. Any type is considered to be a subtype of itself. Also see <a
     * href="http://docs.oracle.com/javase/specs/jls/se8/html/jls-4.html">JLS section 4.10, Subtyping</a>.
     *
     * @param types the type utils
     * @param t1 the first type
     * @param t2 the second type
     * @return {@code true} if and only if the first type is a subtype of the second
     * @throws IllegalArgumentException if given an executable or package type
     */
    public static boolean isSubType(Types types, TypeMirror t1, TypeMirror t2) {
        return types.isSubtype( types.erasure( t1 ), types.erasure( t2 ) );
    }
}
