/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.annotation;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Type;

/**
 * @author Ben Zegveld
 */
public class ClassAnnotationElement extends AnnotationElement {

    private List<Type> values;

    public ClassAnnotationElement(String elementName, List<Type> values) {
        super( elementName );
        this.values = values;
    }

    public List<Type> getValues() {
        return values;
    }

    @Override
    public Set<Type> getImportTypes() {
        return new LinkedHashSet<>( values );
    }
}
