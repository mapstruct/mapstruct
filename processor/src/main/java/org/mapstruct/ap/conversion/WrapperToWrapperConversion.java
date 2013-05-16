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

public class WrapperToWrapperConversion implements Conversion {

    private final static Map<Class<?>, Class<?>> wrapperToPrimitiveTypes;

    static {
        Map<Class<?>, Class<?>> tmp = new HashMap<Class<?>, Class<?>>();
        tmp.put( Byte.class, byte.class );
        tmp.put( Short.class, short.class );
        tmp.put( Integer.class, int.class );
        tmp.put( Long.class, long.class );
        tmp.put( Float.class, float.class );
        tmp.put( Double.class, double.class );

        wrapperToPrimitiveTypes = Collections.unmodifiableMap( tmp );
    }

    private final Class<?> sourceType;
    private final Class<?> targetType;

    public WrapperToWrapperConversion(Class<?> sourceType, Class<?> targetType) {
        if ( sourceType.isPrimitive() ) {
            throw new IllegalArgumentException( sourceType + " is no wrapper type." );
        }
        if ( targetType.isPrimitive() ) {
            throw new IllegalArgumentException( targetType + " is no wrapper type." );
        }

        this.sourceType = wrapperToPrimitiveTypes.get( sourceType );
        this.targetType = wrapperToPrimitiveTypes.get( targetType );
    }

    @Override
    public String to(String sourcePropertyAccessor, Type type) {
        if ( sourceType == targetType ) {
            return sourcePropertyAccessor;
        }
        else {
            return sourcePropertyAccessor + "." + targetType.getName() + "Value()";
        }
    }

    @Override
    public String from(String targetPropertyAccessor, Type type) {
        if ( sourceType == targetType ) {
            return targetPropertyAccessor;
        }
        else {
            return targetPropertyAccessor + "." + sourceType.getName() + "Value()";
        }
    }
}
