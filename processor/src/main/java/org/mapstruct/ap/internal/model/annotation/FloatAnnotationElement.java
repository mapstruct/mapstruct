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
public class FloatAnnotationElement extends AnnotationElement {

    private List<Float> values;

    public FloatAnnotationElement(String elementName, List<Float> values) {
        super( elementName );
        this.values = values;
    }

    public List<Float> getValues() {
        return values;
    }

}
