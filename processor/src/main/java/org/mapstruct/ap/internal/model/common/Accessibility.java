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
package org.mapstruct.ap.internal.model.common;

import java.util.Set;
import javax.lang.model.element.Modifier;

/**
 * Accessibility of an element
 *
 * @author Andreas Gudian
 */
public enum Accessibility {
    PRIVATE( "private" ), DEFAULT( "" ), PROTECTED( "protected" ), PUBLIC( "public" );

    private final String keyword;

    private Accessibility(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public static Accessibility fromModifiers(Set<Modifier> modifiers) {
        if ( modifiers.contains( Modifier.PUBLIC ) ) {
            return PUBLIC;
        }
        else if ( modifiers.contains( Modifier.PROTECTED ) ) {
            return PROTECTED;
        }
        else if ( modifiers.contains( Modifier.PRIVATE ) ) {
            return PRIVATE;
        }

        return DEFAULT;
    }
}
