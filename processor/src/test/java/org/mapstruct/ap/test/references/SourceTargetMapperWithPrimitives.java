/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.references;

import org.mapstruct.Mapper;
import org.mapstruct.TargetType;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 *
 */
@Mapper
public abstract class SourceTargetMapperWithPrimitives {
    public static final SourceTargetMapperWithPrimitives INSTANCE =
        Mappers.getMapper( SourceTargetMapperWithPrimitives.class );

    public abstract TargetWithPrimitives sourceToTarget(SourceWithWrappers source);

    @SuppressWarnings( "unchecked" )
    public <T> T convert(@TargetType Class<T> clazz, SomeType wrapper) {
        if ( clazz == int.class ) {
            return (T) Integer.valueOf( wrapper.getValue() );
        }
        else if ( clazz == long.class ) {
            return (T) Long.valueOf( wrapper.getValue() );
        }
        else if ( clazz == boolean.class ) {
            return (T) Boolean.valueOf( wrapper.getValue() );
        }
        else if ( clazz == char.class ) {
            return (T) Character.valueOf( wrapper.getValue().charAt( 0 ) );
        }

        return null;
    }

    public <T extends BaseType> T unwrapGenericWrapper(GenericWrapper<T> source, @TargetType Class<T> targetType) {
        return source.getWrapped();
    }
}
