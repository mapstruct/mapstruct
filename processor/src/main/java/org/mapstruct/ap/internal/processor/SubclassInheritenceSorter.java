/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor;

import java.util.Comparator;

import org.mapstruct.ap.internal.model.source.SubclassMappingOptions;

/**
 * @author Ben Zegveld
 */
class SubclassInheritenceSorter implements Comparator<SubclassMappingOptions> {

    @Override
    public int compare(SubclassMappingOptions o1, SubclassMappingOptions o2) {
        // TODO enforce sorting so the more abstract the class the further to the back it goes.
        return 0;
    }

}
