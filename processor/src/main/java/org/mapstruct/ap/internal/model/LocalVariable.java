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
package org.mapstruct.ap.internal.model;

import java.util.Set;
import org.mapstruct.ap.internal.model.assignment.Assignment;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.Collections;

/**
 * Local variable used in a mapping method.
 *
 * @author Sjaak Derksen
 */
public class LocalVariable extends ModelElement {

    private final String name;
    private final Type type;
    private final Assignment factoryMethod;

    public LocalVariable( String name, Type type, Assignment factoryMethod ) {
        this.name = name;
        this.type = type;
        this.factoryMethod = factoryMethod;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Assignment getFactoryMethod() {
        return factoryMethod;
    }

    @Override
    public String toString() {
        return  type.toString() + " " + name;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.asSet( type );
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final LocalVariable other = (LocalVariable) obj;
        if ( (this.name == null) ? (other.name != null) : !this.name.equals( other.name ) ) {
            return false;
        }
        return true;
    }

}
