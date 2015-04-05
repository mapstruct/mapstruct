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
