/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.conversion;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.mapstruct.ap.model.Type;
import org.mapstruct.ap.util.Strings;

public class PrimitiveToStringConversion implements Conversion {

    private final static Map<Class<?>, Class<?>> primitiveToWrapperTypes;

    static {
        Map<Class<?>, Class<?>> tmp = new HashMap<Class<?>, Class<?>>();
        tmp.put( byte.class, Byte.class );
        tmp.put( short.class, Short.class );
        tmp.put( int.class, Integer.class );
        tmp.put( long.class, Long.class );
        tmp.put( float.class, Float.class );
        tmp.put( double.class, Double.class );
        tmp.put( boolean.class, Boolean.class );

        primitiveToWrapperTypes = Collections.unmodifiableMap( tmp );
    }

    private final Class<?> sourceType;
    private final Class<?> wrapperType;

    public PrimitiveToStringConversion(Class<?> sourceType) {
        if ( !sourceType.isPrimitive() ) {
            throw new IllegalArgumentException( sourceType + " is no primitive type." );
        }

        this.sourceType = sourceType;
        this.wrapperType = primitiveToWrapperTypes.get( sourceType );
    }

    @Override
    public String to(String sourcePropertyAccessor, Type type) {
        return "String.valueOf( " + sourcePropertyAccessor + " )";
    }

    @Override
    public String from(String targetPropertyAccessor, Type type) {
        return wrapperType.getSimpleName() + ".parse" + Strings.capitalize( sourceType.getSimpleName() ) + "( " + targetPropertyAccessor + " )";
    }
}
