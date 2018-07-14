/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories;

import org.mapstruct.TargetType;

/**
 * @author Andreas Gudian
 *
 */
public class GenericFactory {
    @SuppressWarnings( "unchecked" )
    public <T extends FactoryCreatable> T createNew(@TargetType Class<T> clazz) {
        if ( clazz == Bar1.class ) {
            return (T) new Bar1( "created by GenericFactory" );
        }

        return null;
    }
}
