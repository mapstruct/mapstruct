/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.immutabletarget;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sjaak Derksen
 */
public class CupboardEntityOnlyGetter {

    private List<String> content = new ArrayList<>();

    public List<String> getContent() {
        return content;
    }

}
