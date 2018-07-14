/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories.c;

import org.mapstruct.ObjectFactory;
import org.mapstruct.ap.test.factories.Bar4;
import org.mapstruct.ap.test.factories.Foo4;

/**
 * @author Remo Meier
 */
public class BarFactory {

    @ObjectFactory
    public Bar4 createBar4(Foo4 foo4) {
        return new Bar4( foo4.getProp().toUpperCase() );
    }

}
