/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.factories.targettype;

import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;

/**
 * @author Remo Meier
 */
public class Bar9Factory {

    @SuppressWarnings( "unchecked" )
    @ObjectFactory
    public <T extends Bar9Base> T createBar9(Foo9Base foo8, @TargetType Class<T> targetType) {
        if ( targetType == Bar9Base.class ) {
            return (T) new Bar9Base( foo8.getProp().toUpperCase() );
        }
        else {
            return (T) new Bar9Child( foo8.getProp().toUpperCase() );
        }
    }

}
