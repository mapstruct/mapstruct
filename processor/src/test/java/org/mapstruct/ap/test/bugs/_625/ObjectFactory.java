/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._625;

public class ObjectFactory {
    public Target createImpl1() {
        return new Target( true );
    }

    public Target createImpl2() {
        return new Target( true );
    }
}
