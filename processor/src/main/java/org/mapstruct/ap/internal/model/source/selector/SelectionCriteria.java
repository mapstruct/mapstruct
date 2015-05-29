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
package org.mapstruct.ap.internal.model.source.selector;

import java.util.List;
import javax.lang.model.type.TypeMirror;

/**
 * This class groups the selection criteria in one class
 *
 * @author Sjaak Derksen
 */
public class SelectionCriteria {

    private final List<TypeMirror> qualifiers;
    private final String targetPropertyName;
    private final TypeMirror qualifyingResultType;
    private boolean preferUpdateMapping;

    public SelectionCriteria(List<TypeMirror> qualifiers, String targetPropertyName, TypeMirror qualifyingResultType,
        boolean preferUpdateMapping ) {
        this.qualifiers = qualifiers;
        this.targetPropertyName = targetPropertyName;
        this.qualifyingResultType = qualifyingResultType;
        this.preferUpdateMapping = preferUpdateMapping;
    }

    public List<TypeMirror> getQualifiers() {
        return qualifiers;
    }

    public String getTargetPropertyName() {
        return targetPropertyName;
    }

    public TypeMirror getQualifyingResultType() {
        return qualifyingResultType;
    }

    public boolean isPreferUpdateMapping() {
        return preferUpdateMapping;
    }

    public void setPreferUpdateMapping(boolean preferUpdateMapping) {
        this.preferUpdateMapping = preferUpdateMapping;
    }

}
