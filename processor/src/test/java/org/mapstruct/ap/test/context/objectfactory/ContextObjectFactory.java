/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.context.objectfactory;

import org.mapstruct.ObjectFactory;

/**
 * @author Andreas Gudian
 */
public class ContextObjectFactory {

    @ObjectFactory
    public Valve create() {
        return new Valve("123id");
    }

}
