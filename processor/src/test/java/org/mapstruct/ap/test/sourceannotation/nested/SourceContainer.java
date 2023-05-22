/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.sourceannotation.nested;

public class SourceContainer {

    private final FirstSource alpha;
    private final SecondSource bravo;

    public SourceContainer(String firstProperty, String secondProperty) {
        this.alpha = new FirstSource( firstProperty );
        this.bravo = new SecondSource( secondProperty );
    }

    public FirstSource getAlpha() {
        return alpha;
    }

    public SecondSource getBravo() {
        return bravo;
    }

}
