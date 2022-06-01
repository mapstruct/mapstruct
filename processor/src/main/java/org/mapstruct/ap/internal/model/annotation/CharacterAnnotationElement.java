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
public class CharacterAnnotationElement extends AnnotationElement {

    private List<Character> values;

    public CharacterAnnotationElement(String elementName, List<Character> values) {
        super( elementName );
        this.values = values;
    }

    public List<Character> getValues() {
        return values;
    }

}
