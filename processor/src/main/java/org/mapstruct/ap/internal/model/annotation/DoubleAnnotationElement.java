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
public class DoubleAnnotationElement extends AnnotationElement {

    private List<Double> values;

    public DoubleAnnotationElement(String elementName, List<Double> values) {
        super( elementName );
        this.values = values;
    }

    public List<Double> getValues() {
        return values;
    }

}
