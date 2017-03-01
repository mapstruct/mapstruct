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
package org.mapstruct.ap.internal.model.source;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * @author Filip Hrisafov
 */
public final class MappingMethodUtils {

    /**
     * Hide default constructor.
     */
    private MappingMethodUtils() {
    }


    /**
     * Checks if the provided {@code method} is for enum mapping. A Method is an Enum Mapping method when the
     * source parameter and result type are enum types.
     *
     * @param method to check
     *
     * @return {@code true} if the method is for enum mapping, {@code false} otherwise
     */
    public static boolean isEnumMapping(Method method) {
        return method.getSourceParameters().size() == 1
            && first( method.getSourceParameters() ).getType().isEnumType()
            && method.getResultType().isEnumType();
    }
}
