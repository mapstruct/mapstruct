/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
