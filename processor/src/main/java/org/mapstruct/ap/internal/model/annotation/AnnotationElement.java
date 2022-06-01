/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.annotation;

import java.util.Collections;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * @author Ben Zegveld
 */
public abstract class AnnotationElement extends ModelElement {

    private final String elementName;

    AnnotationElement(String elementName) {
        this.elementName = elementName;
    }

    public String getElementName() {
        return elementName;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }
}
