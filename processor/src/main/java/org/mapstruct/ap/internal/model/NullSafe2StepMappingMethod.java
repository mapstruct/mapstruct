/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeInstance;

public class NullSafe2StepMappingMethod extends SupportingMappingMethod {

    private final Assignment first;
    private final Assignment second;
    private final Type intermediateType;
    private final String secondVariableName;

    public NullSafe2StepMappingMethod(Collection<String> existingVariableNames, Assignment first,
                                      List<Parameter> parameters, Assignment second, Type intermediateType,
                                      TypeInstance targetType, String secondVariableName,
                                      String name) {
        super( existingVariableNames, getThrownTypes( first, second ),
                getImports( first, second, intermediateType, parameters, targetType.getType() ), targetType,
                parameters, name );
        this.first = first;
        this.second = second;
        this.intermediateType = intermediateType;
        this.secondVariableName = secondVariableName;
    }

    private static List<Type> getThrownTypes(Assignment first, Assignment second) {
        List<Type> thrownTypes = new ArrayList<>(first.getThrownTypes());
        thrownTypes.addAll( second.getThrownTypes() );
        return thrownTypes;
    }

    private static Set<Type> getImports(Assignment first, Assignment second, Type intermediateType,
                                        List<Parameter> parameters, Type targetType) {
        Set<Type> importTypes = new HashSet<>( first.getImportTypes() );
        importTypes.addAll(  second.getImportTypes() );
        importTypes.add( intermediateType );
        importTypes.add( targetType );
        parameters.forEach( p -> importTypes.addAll( p.getImportTypes() ) );
        return importTypes;
    }

    public Type getIntermediateType() {
        return intermediateType;
    }

    public Assignment getFirst() {
        return first;
    }

    public Assignment getSecond() {
        return second;
    }

    public String getSecondVariableName() {
        return secondVariableName;
    }
}
