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
public class CharacterProperty extends Property {

    private List<Character> values;

    public CharacterProperty(String key, List<Character> values) {
        super( key );
        this.values = values;
    }

    public List<Character> getValues() {
        return values;
    }

}
