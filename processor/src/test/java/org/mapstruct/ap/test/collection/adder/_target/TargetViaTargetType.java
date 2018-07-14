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
public class TargetViaTargetType {

    private List<IndoorPet> pets;

    public List<IndoorPet> getPets() {
        return pets;
    }

    public void setPets(List<IndoorPet> pets) {
        this.pets = pets;
    }

    public void addPet(IndoorPet pet) {
        AdderUsageObserver.setUsed( true );
        if ( pets == null ) {
            pets = new ArrayList<IndoorPet>();
        }
        pets.add( pet );
    }
}
