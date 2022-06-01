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
public class LongAnnotationElement extends AnnotationElement {

    private List<Long> values;

    public LongAnnotationElement(String elementName, List<Long> values) {
        super( elementName );
        this.values = values;
    }

    public List<Long> getValues() {
        return values;
    }

}
