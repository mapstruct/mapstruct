/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.adder._target;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sjaak Derksen
 */
public class TargetWithoutSetter {

    private List<Long> pets;

    public List<Long> getPets() {
        return pets;
    }

    public void addPet(Long pet) {
        AdderUsageObserver.setUsed( true );
        if ( pets == null ) {
            pets = new ArrayList<Long>();
        }
        pets.add( pet );
    }
}
