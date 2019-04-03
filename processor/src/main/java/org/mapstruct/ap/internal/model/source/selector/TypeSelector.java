/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.MethodMatcher;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * Selects those methods from the given input set which match the given source and target types (via
 * {@link MethodMatcher}).
 *
 * @author Sjaak Derksen
 */
public class TypeSelector implements MethodSelector {

    private TypeFactory typeFactory;
    private FormattingMessager messager;

    public TypeSelector(TypeFactory typeFactory, FormattingMessager messager) {
        this.typeFactory = typeFactory;
        this.messager = messager;
    }

    @Override
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(Method mappingMethod,
            List<SelectedMethod<T>> methods,
            List<Type> sourceTypes, Type targetType,
            SelectionCriteria criteria) {

        if ( methods.isEmpty() ) {
            return methods;
        }

        List<SelectedMethod<T>> result = new ArrayList<>();

        List<ParameterBinding> availableBindings;
        if ( sourceTypes.isEmpty() ) {
            // if no source types are given, we have a factory or lifecycle method
            availableBindings = getAvailableParameterBindingsFromMethod(
                mappingMethod,
                targetType,
                criteria.getSourceRHS()
            );
        }
        else {
            availableBindings = getAvailableParameterBindingsFromSourceTypes( sourceTypes, targetType, mappingMethod );
        }

        for ( SelectedMethod<T> method : methods ) {
            List<List<ParameterBinding>> parameterBindingPermutations =
                getCandidateParameterBindingPermutations( availableBindings, method.getMethod().getParameters() );

            if ( parameterBindingPermutations != null ) {
                SelectedMethod<T> matchingMethod =
                    getMatchingParameterBinding( targetType, mappingMethod, method, parameterBindingPermutations );

                if ( matchingMethod != null ) {
                    result.add( matchingMethod );
                }
            }
        }
        return result;
    }

    private List<ParameterBinding> getAvailableParameterBindingsFromMethod(Method method, Type targetType,
        SourceRHS sourceRHS) {
        List<ParameterBinding> availableParams = new ArrayList<>( method.getParameters().size() + 3 );

        if ( sourceRHS != null ) {
            availableParams.addAll( ParameterBinding.fromParameters( method.getContextParameters() ) );
            availableParams.add( ParameterBinding.fromSourceRHS( sourceRHS ) );
        }
        else {
            availableParams.addAll( ParameterBinding.fromParameters( method.getParameters() ) );
        }

        addMappingTargetAndTargetTypeBindings( availableParams, targetType );

        return availableParams;
    }

    private List<ParameterBinding> getAvailableParameterBindingsFromSourceTypes(List<Type> sourceTypes,
            Type targetType, Method mappingMethod) {

        List<ParameterBinding> availableParams = new ArrayList<>( sourceTypes.size() + 2 );

        for ( Type sourceType : sourceTypes ) {
            availableParams.add( ParameterBinding.forSourceTypeBinding( sourceType ) );
        }

        for ( Parameter param : mappingMethod.getParameters() ) {
            if ( param.isMappingContext() ) {
                availableParams.add( ParameterBinding.fromParameter( param ) );
            }
        }

        addMappingTargetAndTargetTypeBindings( availableParams, targetType );

        return availableParams;
    }

    /**
     * Adds default parameter bindings for the mapping-target and target-type if not already available.
     *
     * @param availableParams Already available params, new entries will be added to this list
     * @param targetType Target type
     */
    private void addMappingTargetAndTargetTypeBindings(List<ParameterBinding> availableParams, Type targetType) {
        boolean mappingTargetAvailable = false;
        boolean targetTypeAvailable = false;

        // search available parameter bindings if mapping-target and/or target-type is available
        for ( ParameterBinding pb : availableParams ) {
            if ( pb.isMappingTarget() ) {
                mappingTargetAvailable = true;
            }
            else if ( pb.isTargetType() ) {
                targetTypeAvailable = true;
            }
        }

        if ( !mappingTargetAvailable ) {
            availableParams.add( ParameterBinding.forMappingTargetBinding( targetType ) );
        }
        if ( !targetTypeAvailable ) {
            availableParams.add( ParameterBinding.forTargetTypeBinding( typeFactory.classTypeOf( targetType ) ) );
        }
    }

    private <T extends Method> SelectedMethod<T> getMatchingParameterBinding(Type targetType,
            Method mappingMethod, SelectedMethod<T> method, List<List<ParameterBinding>> parameterAssignmentVariants) {

        List<List<ParameterBinding>> matchingParameterAssignmentVariants = new ArrayList<>(
            parameterAssignmentVariants
        );

        Method realMethod = method.getMethod();

        // remove all assignment variants that doesn't match the types from the method
        matchingParameterAssignmentVariants.removeIf(
            parameterAssignments ->
                !realMethod.matches( extractTypes( parameterAssignments ), targetType )
        );

        if ( matchingParameterAssignmentVariants.isEmpty() ) {
            // no matching variants found
            return null;
        }
        else if ( matchingParameterAssignmentVariants.size() == 1 ) {
            // we found exactly one set of variants, use this
            method.setParameterBindings( first( matchingParameterAssignmentVariants ) );
            return method;
        }

        // more than one variant matches, try to find one where even the parameter names are matching
        for ( Iterator<List<ParameterBinding>> it = matchingParameterAssignmentVariants.iterator(); it.hasNext(); ) {
            List<ParameterBinding> parameterAssignments = it.next();

            int i = 0;
            for ( ParameterBinding parameterBinding : parameterAssignments ) {
                Parameter parameter = realMethod.getParameters().get( i++ );

                // if the parameterBinding contains a parameter name we must ensure that this matches the name from the
                // method parameter -> remove all variants where this is not the case
                if ( parameterBinding.getVariableName() != null &&
                    !parameter.getName().contentEquals( parameterBinding.getVariableName() ) ) {
                    it.remove();

                    break;
                }
            }
        }

        if ( matchingParameterAssignmentVariants.isEmpty() ) {
            // we had some matching assignments before, but when checking the parameter names we can't find an
            // appropriate one, in this case the user must chose identical parameter names for the mapping and lifecycle
            // method
            messager.printMessage(
                realMethod.getExecutable(),
                Message.LIFECYCLEMETHOD_AMBIGUOUS_PARAMETERS,
                mappingMethod
            );

            return null;
        }

        // there should never be more then one assignment left after checking the parameter names as it is not possible
        // to use the same parameter name more then once
        // -> we can use the first variant that is left (that should also be the only one)
        method.setParameterBindings( first( matchingParameterAssignmentVariants ) );
        return method;
    }

    /**
     * @param availableParams parameter bindings available in the scope of the method call
     * @param methodParameters parameters of the method that is inspected
     * @return all parameter binding permutations for which proper type checks need to be conducted.
     */
    private static List<List<ParameterBinding>> getCandidateParameterBindingPermutations(
            List<ParameterBinding> availableParams,
            List<Parameter> methodParameters) {

        if ( methodParameters.size() > availableParams.size() ) {
            return null;
        }

        List<List<ParameterBinding>> bindingPermutations = new ArrayList<>( 1 );
        bindingPermutations.add( new ArrayList<>( methodParameters.size() ) );

        for ( Parameter methodParam : methodParameters ) {
            List<ParameterBinding> candidateBindings =
                findCandidateBindingsForParameter( availableParams, methodParam );

            if ( candidateBindings.isEmpty() ) {
                return null;
            }

            if ( candidateBindings.size() == 1 ) {
                // short-cut to avoid list-copies for the usual case where only one binding fits
                for ( List<ParameterBinding> variant : bindingPermutations ) {
                    // add binding to each existing variant
                    variant.add( first( candidateBindings ) );
                }
            }
            else {
                List<List<ParameterBinding>> newVariants =
                    new ArrayList<>( bindingPermutations.size() * candidateBindings.size() );
                for ( List<ParameterBinding> variant : bindingPermutations ) {
                    // create a copy of each variant for each binding
                    for ( ParameterBinding binding : candidateBindings ) {
                        List<ParameterBinding> extendedVariant =
                            new ArrayList<>( methodParameters.size() );
                        extendedVariant.addAll( variant );
                        extendedVariant.add( binding );

                        newVariants.add( extendedVariant );
                    }
                }

                bindingPermutations = newVariants;
            }
        }

        return bindingPermutations;
    }

    /**
     * @param candidateParameters available for assignment.
     * @param parameter that need assignment from one of the candidate parameter bindings.
     * @return list of candidate parameter bindings that might be assignable.
     */
    private static List<ParameterBinding> findCandidateBindingsForParameter(List<ParameterBinding> candidateParameters,
            Parameter parameter) {
        List<ParameterBinding> result = new ArrayList<>( candidateParameters.size() );

        for ( ParameterBinding candidate : candidateParameters ) {
            if ( parameter.isTargetType() == candidate.isTargetType()
                && parameter.isMappingTarget() == candidate.isMappingTarget()
                && parameter.isMappingContext() == candidate.isMappingContext() ) {
                result.add( candidate );
            }
        }

        return result;
    }

    private static List<Type> extractTypes(List<ParameterBinding> parameters) {
        return parameters.stream()
            .map( ParameterBinding::getType )
            .collect( Collectors.toList() );
    }
}
