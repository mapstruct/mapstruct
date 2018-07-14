/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories.b;

import org.mapstruct.ap.test.factories.Bar3;

/**
 * @author Sjaak Derksen
 */
public class BarFactory {

    public Bar3 createBar3() {
        return new Bar3( "BAR3" );
    }

}
