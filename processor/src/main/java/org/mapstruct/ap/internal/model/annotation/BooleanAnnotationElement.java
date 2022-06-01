/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.annotation;

import java.util.List;

/**
 * @author Ben Zegveld
 */
public class BooleanAnnotationElement extends AnnotationElement {

    private List<Boolean> values;

    public BooleanAnnotationElement(String elementName, List<Boolean> values) {
        super( elementName );
        this.values = values;
    }

    public List<Boolean> getValues() {
        return values;
    }

}
