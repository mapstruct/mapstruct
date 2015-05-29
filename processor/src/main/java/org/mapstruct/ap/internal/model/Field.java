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
package org.mapstruct.ap.internal.model;

import java.util.Collections;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

/**
 * A field of a mapper type, e.g. a reference to another mapper.
 *
 * @author Gunnar Morling
 */
public class Field extends ModelElement {

    private final Type type;
    private final String variableName;
    private boolean used;
    private boolean typeRequiresImport;

    public Field(Type type, String variableName, boolean used) {
        this.type = type;
        this.variableName = variableName;
        this.used = used;
        this.typeRequiresImport = used;
    }
    public Field(Type type, String variableName) {
        this.type = type;
        this.variableName = variableName;
        this.used = false;
        this.typeRequiresImport = false;
    }

    /**
     * Returns the type of this field.
     *
     * @return the type of this field
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns the variable name of this field.
     *
     * @return the variable name of this reference
     */
    public String getVariableName() {
        return variableName;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

    /**
     * indicates whether the field is indeed used
     * @return true when field is used
     */
    public boolean isUsed() {
        return used;
    }

    /**
     * sets the field as being used
     * @param isUsed must be true when being used.
     */
    public void setUsed(boolean isUsed) {
        this.used = isUsed;
    }

    /**
     * field needs to be imported
     * @return true if the type should be included in the import of the generated type
     */
    public boolean isTypeRequiresImport() {
        return typeRequiresImport;
    }

    /**
     * set field needs to be imported
     * @param typeRequiresImport needs to be imported
     */
    public void setTypeRequiresImport(boolean typeRequiresImport) {
        this.typeRequiresImport = typeRequiresImport;
    }

}
