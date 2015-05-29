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

import org.mapstruct.ap.internal.model.assignment.Assignment;
import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Direct Assignment. Just a source reference
 *
 * @author Sjaak Derksen
 */
public class Direct extends ModelElement implements Assignment {

    private final String sourceReference;

    public Direct( String sourceReference ) {
        this.sourceReference = sourceReference;
    }

    @Override
    public String getSourceReference() {
        return sourceReference;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.emptySet();
    }

    @Override
    public List<Type> getThrownTypes() {
        return Collections.emptyList();
    }

    @Override
    public void setAssignment( Assignment assignment ) {
        throw new UnsupportedOperationException( "Not supported." );
    }

    @Override
    public AssignmentType getType() {
        return AssignmentType.DIRECT;
    }

    @Override
    public boolean isUpdateMethod() {
        return false;
    }

}
