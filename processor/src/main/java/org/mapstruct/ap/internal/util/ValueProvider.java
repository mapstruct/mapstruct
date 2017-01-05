/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.util;

import org.mapstruct.ap.internal.util.accessor.Accessor;

/**
 * This a wrapper class which provides the value that needs to be used in the models.
 *
 * It is used to provide the read value for a difference kind of {@link Accessor}.
 *
 * @author Filip Hrisafov
 */
public class ValueProvider {

    private final String value;

    private ValueProvider(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Creates a {@link ValueProvider} from the provided {@code accessor}. The base value is
     * {@link Accessor#getSimpleName()}. If the {@code accessor} is for an executable, then {@code ()} is
     * appended.
     *
     * @param accessor that provides the value
     *
     * @return a {@link ValueProvider} tha provides a read value for the {@code accessor}
     */
    public static ValueProvider of(Accessor accessor) {
        if ( accessor == null ) {
            return null;
        }
        String value = accessor.getSimpleName().toString();
        if ( accessor.getExecutable() != null ) {
            value += "()";
        }
        return new ValueProvider( value );
    }
}
