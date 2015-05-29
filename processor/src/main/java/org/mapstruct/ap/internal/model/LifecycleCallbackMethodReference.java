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

import java.beans.Introspector;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.util.Collections;
import org.mapstruct.ap.internal.util.Strings;

/**
 * Represents a reference to a method that is annotated with {@code @BeforeMapping} or {@code @AfterMapping}.
 *
 * @author Andreas Gudian
 */
public class LifecycleCallbackMethodReference extends MappingMethod {

    private final Type declaringType;
    private final List<Parameter> parameterAssignments;

    public LifecycleCallbackMethodReference(SourceMethod method, List<Parameter> parameterAssignments) {
        super( method );
        this.declaringType = method.getDeclaringMapper();
        this.parameterAssignments = parameterAssignments;
    }

    public Type getDeclaringType() {
        return declaringType;
    }

    public String getInstanceVariableName() {
        return Strings.getSaveVariableName( Introspector.decapitalize( declaringType.getName() ) );
    }

    @Override
    public Set<Type> getImportTypes() {
        return declaringType != null ? Collections.asSet( declaringType ) : java.util.Collections.<Type> emptySet();
    }

    public List<Parameter> getParameterAssignments() {
        return parameterAssignments;
    }

    public boolean hasMappingTargetParameter() {
        for ( Parameter param : parameterAssignments ) {
            if ( param.isMappingTarget() ) {
                return true;
            }
        }

        return false;
    }
}
