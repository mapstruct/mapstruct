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
package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;

/**
 * This selector selects a best match based on the result type.
 * <p>
 * Suppose: Sedan -&gt; Car -&gt; Vehicle, MotorCycle -&gt; Vehicle By means of this selector one can pinpoint the exact
 * desired return type (Sedan, Car, MotorCycle, Vehicle)
 *
 * @author Sjaak Derksen
 */
public class TargetTypeSelector implements MethodSelector {

    private final Types typeUtils;

    public TargetTypeSelector( Types typeUtils, Elements elementUtils ) {
        this.typeUtils = typeUtils;
    }

    @Override
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(Method mappingMethod,
                                                                          List<SelectedMethod<T>> methods,
                                                                          List<Type> sourceTypes, Type targetType,
                                                                          SelectionCriteria criteria) {

        TypeMirror qualifyingTypeMirror = criteria.getQualifyingResultType();
        if ( qualifyingTypeMirror != null && !criteria.isLifecycleCallbackRequired() ) {

            List<SelectedMethod<T>> candidatesWithQualifyingTargetType =
                new ArrayList<SelectedMethod<T>>( methods.size() );

            for ( SelectedMethod<T> method : methods ) {
                TypeMirror resultTypeMirror = method.getMethod().getResultType().getTypeElement().asType();
                if ( typeUtils.isSameType( qualifyingTypeMirror, resultTypeMirror ) ) {
                    candidatesWithQualifyingTargetType.add( method );
                }
            }

            return candidatesWithQualifyingTargetType;
        }
        else {
            return methods;
        }
    }
}

