/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedsourceproperties._target;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sjaak Derksen
 */
public class ChartPositions {

    private final List<Long> positions = new ArrayList<Long>();

    public List<Long> getPositions() {
        return positions;
    }

    public Long addPosition(Long position) {
        AdderUsageObserver.setUsed( true );
        positions.add( position );
        return position;
    }
}
