/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories.assignment;

import org.mapstruct.ObjectFactory;

/**
 * @author Remo Meier
 */
public class Bar6Factory {

    @ObjectFactory
    public Bar6 createBar6(Foo6A foo6A) {
        return new Bar6( foo6A.getPropA().toUpperCase() );
    }

}
