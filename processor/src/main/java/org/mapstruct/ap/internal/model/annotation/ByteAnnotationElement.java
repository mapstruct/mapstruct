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
public class ByteAnnotationElement extends AnnotationElement {

    private List<Byte> values;

    public ByteAnnotationElement(String elementName, List<Byte> values) {
        super( elementName );
        this.values = values;
    }

    public List<Byte> getValues() {
        return values;
    }

}
