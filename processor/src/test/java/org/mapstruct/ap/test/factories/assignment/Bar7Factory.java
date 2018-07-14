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
public class Bar7Factory {

    @ObjectFactory
    public Bar7 createBar7(Foo7B foo7B) {
        return new Bar7( foo7B.getPropB().toUpperCase() );
    }

}
