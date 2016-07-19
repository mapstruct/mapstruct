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
 * A PropertyEntry contains information on the name, readAccessor (for source), readAccessor and writeAccessor
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
    private final ExecutableElement presenceChecker;
    private final Type type;

    /**
     * Constructor used to create {@link TargetReference} property entries from a mapping
     *
     * @param fullName
     * @param readAccessor
     * @param writeAccessor
     * @param type
     */
    private PropertyEntry(String[] fullName, ExecutableElement readAccessor, ExecutableElement writeAccessor,
        ExecutableElement presenceChecker, Type type) {
        this.fullName = fullName;
        this.readAccessor = readAccessor;
        this.writeAccessor = writeAccessor;
        this.presenceChecker = presenceChecker;
        this.type = type;
    }

    /**
     * Constructor used to create {@link TargetReference} property entries
     *
     * @param fullName name of the property (dot separated)
     * @param readAccessor its read accessor
     * @param writeAccessor its write accessor
     * @param type type of the property
     * @return the property entry for given parameters.
     */
    public static PropertyEntry forTargetReference( String[] fullName, ExecutableElement readAccessor,
        ExecutableElement writeAccessor, Type type) {
        return new PropertyEntry( fullName, readAccessor, writeAccessor, null, type );
    }

    /**
     * Constructor used to create {@link SourceReference} property entries from a mapping
     *
     * @param name name of the property (dot separated)
     * @param readAccessor its read accessor
     * @param presenceChecker its presence Checker
     * @param type type of the property
     * @return the property entry for given parameters.
     */
    public static PropertyEntry forSourceReference(String name, ExecutableElement readAccessor,
        ExecutableElement presenceChecker, Type type) {
        return new PropertyEntry( new String[]{name}, readAccessor, null, presenceChecker, type );
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

    public ExecutableElement getPresenceChecker() {
        return presenceChecker;
    }

    public Type getType() {
        return type;
    }

    public String getFullName() {
        return Strings.join( Arrays.asList(  fullName ), "." );
    }

}
