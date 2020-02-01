/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.ambiguousfactorymethod.a;

import org.mapstruct.ap.test.erroneous.ambiguousfactorymethod.Bar;

/**
 * @author Sjaak Derksen
 */
public class BarFactory {

    public Bar createBar() {
        return new Bar( "BAR" );
    }

}
