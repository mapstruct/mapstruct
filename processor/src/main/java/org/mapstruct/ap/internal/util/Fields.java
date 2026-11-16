/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

/**
 * Provides functionality around {@link VariableElement}s.
 *
 * @author Sjaak Derksen
 */
public class Fields {

    private Fields() {
    }

    public static boolean isFieldAccessor(VariableElement method) {
        return isPublic( method ) && isNotStatic( method );
    }

    static boolean isPublic(VariableElement method) {
        return method.getModifiers().contains( Modifier.PUBLIC );
    }

    private static boolean isNotStatic(VariableElement method) {
        return !method.getModifiers().contains( Modifier.STATIC );
    }
}
