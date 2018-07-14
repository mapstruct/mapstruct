/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._289;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Sjaak Derksen
 */
public class TargetWithoutSetter {

    private final Collection<TargetElement> collection = new ArrayList<TargetElement>();

    public Collection<TargetElement> getCollection() {
        return collection;
    }

}
