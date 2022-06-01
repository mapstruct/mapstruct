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
public class IntegerAnnotationElement extends AnnotationElement {

    private List<Integer> values;

    public IntegerAnnotationElement(String elementName, List<Integer> values) {
        super( elementName );
        this.values = values;
    }

    public List<Integer> getValues() {
        return values;
    }

}
