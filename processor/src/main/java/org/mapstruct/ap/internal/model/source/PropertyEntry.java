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

import java.util.Arrays;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.internal.util.accessor.Accessor;
import org.mapstruct.ap.internal.util.accessor.ExecutableElementAccessor;


/**
 * A PropertyEntry contains information on the name, readAccessor (for source), readAccessor and writeAccessor
 * (for targets) and return type of a property.
 */
public class PropertyEntry {

    private final String[] fullName;
    private final Accessor readAccessor;
    private final Accessor writeAccessor;
    private final ExecutableElementAccessor presenceChecker;
    private final Type type;

    /**
     * Constructor used to create {@link TargetReference} property entries from a mapping
     *
     * @param fullName
     * @param readAccessor
     * @param writeAccessor
     * @param type
     */
    private PropertyEntry(String[] fullName, Accessor readAccessor, Accessor writeAccessor,
                          ExecutableElementAccessor presenceChecker, Type type) {
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
    public static PropertyEntry forTargetReference(String[] fullName, Accessor readAccessor,
                                                   Accessor writeAccessor, Type type) {
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
    public static PropertyEntry forSourceReference(String name, Accessor readAccessor,
                                                   ExecutableElementAccessor presenceChecker, Type type) {
        return new PropertyEntry( new String[]{name}, readAccessor, null, presenceChecker, type );
    }

    public String getName() {
        return fullName[fullName.length - 1];
    }

    public Accessor getReadAccessor() {
        return readAccessor;
    }

    public Accessor getWriteAccessor() {
        return writeAccessor;
    }

    public ExecutableElementAccessor getPresenceChecker() {
        return presenceChecker;
    }

    public Type getType() {
        return type;
    }

    public String getFullName() {
        return Strings.join( Arrays.asList(  fullName ), "." );
    }

    public PropertyEntry pop() {
        if ( fullName.length > 1 ) {
            String[] newFullName = Arrays.copyOfRange( fullName, 1, fullName.length );
            return new PropertyEntry(newFullName, readAccessor, writeAccessor, presenceChecker, type );
        }
        else {
            return null;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Arrays.deepHashCode( this.fullName );
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final PropertyEntry other = (PropertyEntry) obj;
        if ( !Arrays.deepEquals( this.fullName, other.fullName ) ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return type + " " + Strings.join( Arrays.asList( fullName ), "." );
    }
}
