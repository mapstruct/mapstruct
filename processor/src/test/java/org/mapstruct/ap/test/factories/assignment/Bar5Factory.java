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
public class Bar5Factory {

    @ObjectFactory
    public Bar5 createBar5(Foo5A foo5A, Foo5B foo5B) {
        return new Bar5( foo5A.getPropA().toUpperCase(), foo5B.getPropB() );
    }

}
