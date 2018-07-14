/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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

    Accessibility(String keyword) {
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
