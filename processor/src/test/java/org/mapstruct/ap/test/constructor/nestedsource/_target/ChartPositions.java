/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.constructor.nestedsource._target;

import java.util.Collections;
import java.util.List;

/**
 * @author Filip Hrisafov
 */
public class ChartPositions {

    private final List<Long> positions;

    public ChartPositions(List<Long> positions) {
        this.positions = positions;
    }

    public List<Long> getPositions() {
        return Collections.unmodifiableList( positions );
    }
}
