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
public class StringAnnotationElement extends AnnotationElement {

    private List<String> values;

    public StringAnnotationElement(String elementName, List<String> values) {
        super( elementName );
        this.values = values;
    }

    public List<String> getValues() {
        return values;
    }

}
