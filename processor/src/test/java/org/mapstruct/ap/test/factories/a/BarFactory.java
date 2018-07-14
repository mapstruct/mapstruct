/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories.a;

import org.mapstruct.ap.test.factories.Bar1;

/**
 * @author Sjaak Derksen
 */
public class BarFactory {

    public Bar1 createBar1() {
        return new Bar1( "BAR1" );
    }

}
