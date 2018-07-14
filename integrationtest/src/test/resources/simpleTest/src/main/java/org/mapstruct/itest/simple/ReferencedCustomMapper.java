/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.simple;

import java.util.Map;

import org.mapstruct.TargetType;

/**
 * @author Andreas Gudian
 *
 */
public class ReferencedCustomMapper {

    public String convert(YetAnotherType source){
        return source.toString();
    }

    public long incrementingIntToLong(int source) {
        return source + 1;
    }

    @SuppressWarnings( "unchecked" )
    public <T extends BaseType> T convert(String string, @TargetType Class<T> clazz) {
        if ( clazz == SomeType.class ) {
            return (T) new SomeType( string );
        }
        else if ( clazz == SomeOtherType.class ) {
            return (T) new SomeOtherType( string );
        }

        return null;
    }

    public String toString(BaseType baseType) {
        return String.valueOf( baseType );
    }

    /**
     * This method should not be chosen for the mapping, as our types are never within the bounds of
     * {@code T extends Map<?,?>}
     */
    public <T extends Map<?, ?>> T unused(String string, @TargetType Class<T> clazz) {
        throw new RuntimeException( "should never be called" );
    }
}
