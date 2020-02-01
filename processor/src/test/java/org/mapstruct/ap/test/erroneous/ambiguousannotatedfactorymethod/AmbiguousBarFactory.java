/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.ambiguousannotatedfactorymethod;

import org.mapstruct.ObjectFactory;

/**
 * @author Remo Meier
 */
public class AmbiguousBarFactory {

    @ObjectFactory
    public Bar createBar( Foo foo ) {
        return new Bar( "BAR" );
    }

}
