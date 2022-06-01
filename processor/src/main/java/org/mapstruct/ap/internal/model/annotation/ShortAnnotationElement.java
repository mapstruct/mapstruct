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
public class ShortAnnotationElement extends AnnotationElement {

    private List<Short> values;

    public ShortAnnotationElement(String elementName, List<Short> values) {
        super( elementName );
        this.values = values;
    }

    public List<Short> getValues() {
        return values;
    }

}
