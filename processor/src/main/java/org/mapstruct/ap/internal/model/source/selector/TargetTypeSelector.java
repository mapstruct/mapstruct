/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.type.TypeMirror;
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

    public TargetTypeSelector( Types typeUtils ) {
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
                new ArrayList<>( methods.size() );

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

