/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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


import java.util.Arrays;
import javax.lang.model.element.ExecutableElement;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Strings;


 /**
 * A PropertyEntry contains information on the name, readAccessor (for source), readAccessor & writeAccessor
 * (for targets) and return type of a property.
 *
 * It can be shared between several nested properties. For example
 *
 * bean
 *
 * nestedMapping1 = "x.y1.z1" nestedMapping2 = "x.y1.z2" nestedMapping3 = "x.y2.z3"
 *
 * has property entries x, y1, y2, z1, z2, z3.
 */
public class PropertyEntry {

    private final String[] fullName;
    private final ExecutableElement readAccessor;
    private final ExecutableElement writeAccessor;
    private final Type type;

    /**
     * Constructor used to create {@link TargetReference} property entries from a mapping
     *
     * @param fullName
     * @param readAccessor
     * @param writeAccessor
     * @param type
     */
    PropertyEntry(String[] fullName, ExecutableElement readAccessor, ExecutableElement writeAccessor, Type type) {
        this.fullName = fullName;
        this.readAccessor = readAccessor;
        this.writeAccessor = writeAccessor;
        this.type = type;
    }


    /**
     * Constructor used to create {@link TargetReference} property entries
     *
     * @param name
     * @param readAccessor
     * @param writeAccessor
     * @param type
     */

    PropertyEntry(String name, ExecutableElement readAccessor, ExecutableElement writeAccessor, Type type) {
        this( new String[]{name}, readAccessor, writeAccessor, type );
    }

    /**
     * Constructor used to create {@link SourceReference}
     *
     * @param name
     * @param readAccessor
     * @param type
     */
    public PropertyEntry(String name, ExecutableElement readAccessor, Type type) {
        this( new String[]{name}, readAccessor, null, type );
    }

    /**
     * Constructor used to create {@link SourceReference} property entries from a mapping
     *
     * @param fullName
     * @param readAccessor
     * @param type
     */
    PropertyEntry(String[] fullName, ExecutableElement readAccessor, Type type) {
        this( fullName, readAccessor, null, type );
    }

    public String getName() {
        return fullName[fullName.length - 1];
    }

    public ExecutableElement getReadAccessor() {
        return readAccessor;
    }

    public ExecutableElement getWriteAccessor() {
        return writeAccessor;
    }

    public Type getType() {
        return type;
    }

    public String getFullName() {
        return Strings.join( Arrays.asList(  fullName ), "." );
    }

}
