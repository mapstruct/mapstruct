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
import java.util.Objects;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeInstance;
import org.mapstruct.ap.internal.util.accessor.Nullability;

public class NullSafe2StepMappingMethod extends SupportingMappingMethod {

    private final Assignment first;
    private final Assignment second;
    private final Type intermediateType;
    private final String secondVariableName;

    public static NullSafe2StepMappingMethod create(Collection<String> existingVariableNames, Assignment first,
                                                     List<Parameter> parameters, Assignment second,
                                                     Type intermediateType,
                                                     TypeInstance targetType, String secondVariableName,
                                                     String name) {
        TypeInstance nullableTargetType = nullableTargetType( targetType );
        return new NullSafe2StepMappingMethod( existingVariableNames, first, parameters, second, intermediateType,
                getImports( first, second, intermediateType, parameters, nullableTargetType.getType() ),
                nullableTargetType, secondVariableName, name );
    }

    private NullSafe2StepMappingMethod(Collection<String> existingVariableNames, Assignment first,
                                       List<Parameter> parameters, Assignment second,
                                       Type intermediateType,
                                       Set<Type> importTypes, TypeInstance returnType,
                                       String secondVariableName, String name) {
        super( existingVariableNames, getThrownTypes( first, second ), importTypes, returnType, parameters, name );
        this.first = first;
        this.second = second;
        this.intermediateType = intermediateType;
        this.secondVariableName = secondVariableName;
    }

    /**
     * The null-safe two-step helper may return {@code null} when the intermediate result is {@code null}, so its
     * return type must be nullable. For a primitive target the helper therefore returns the boxed equivalent type
     * (e.g. {@code Long} instead of {@code long}). Declaring a nullable return type lets the caller apply MapStruct's
     * standard null handling to the result: an existing target value is preserved on update, and the primitive
     * default is used on create.
     */
    private static TypeInstance nullableTargetType( TypeInstance targetType ) {
        if ( targetType.getType().isPrimitive() ) {
            return TypeInstance.of( targetType.getType().getBoxedEquivalent(),
                    Nullability.hardcodedNullability( Nullability.NullabilityState.NULLABLE ) );
        }
        return targetType;
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

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( !super.equals( obj ) ) {
            return false;
        }
        return getParameters().equals( ((NullSafe2StepMappingMethod) obj).getParameters() );
    }

    @Override
    public int hashCode() {
        return Objects.hash( super.hashCode(), secondVariableName );
    }
}
