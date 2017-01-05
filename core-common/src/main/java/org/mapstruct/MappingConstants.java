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
package org.mapstruct;

/**
 * Contains all constants defined in the mapping process.
 *
 * @author Sjaak Derksen
 */
public final class MappingConstants {

    private MappingConstants() {
    }

    /**
     * Represents a {@code null} source or target.
     */
    public static final String NULL = "<NULL>";

    /**
     * Represents any source that is not already mapped by either a defined mapping or by means of name based mapping.
     */
    public static final String ANY_REMAINING = "<ANY_REMAINING>";

    /**
     * Represents any source that is not already mapped by a defined mapping.
     */
    public static final String ANY_UNMAPPED = "<ANY_UNMAPPED>";

}
