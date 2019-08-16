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
public class TargetHuman {

    private List<Integer> teeth;

    public List<Integer> getTeeth() {
        return teeth;
    }

    public void setTeeth(List<Integer> teeth) {
        this.teeth = teeth;
    }

    public void addTooth(Integer pet) {
        AdderUsageObserver.setUsed( true );
        if ( teeth == null ) {
            teeth = new ArrayList<>();
        }
        teeth.add( pet );
    }

    public void addTeeth(Integer tooth) {
        if ( teeth == null ) {
            teeth = new ArrayList<>();
        }
        teeth.add( tooth );
    }
}
