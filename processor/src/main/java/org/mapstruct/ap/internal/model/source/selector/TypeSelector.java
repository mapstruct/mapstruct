/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.common.Type;
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

    private FormattingMessager messager;

    public TypeSelector(FormattingMessager messager) {
        this.messager = messager;
    }

    @Override
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(List<SelectedMethod<T>> methods,
                                                                         SelectionContext context) {
        if ( methods.isEmpty() ) {
            return methods;
        }

        Type returnType = context.getReturnType();

        List<SelectedMethod<T>> result = new ArrayList<>();

        List<ParameterBinding> availableBindings = context.getAvailableParameterBindings();

        for ( SelectedMethod<T> method : methods ) {
            List<List<ParameterBinding>> parameterBindingPermutations =
                getCandidateParameterBindingPermutations( availableBindings, method.getMethod().getParameters() );

            if ( parameterBindingPermutations != null ) {
                SelectedMethod<T> matchingMethod =
                    getMatchingParameterBinding( returnType, context, method, parameterBindingPermutations );

                if ( matchingMethod != null ) {
                    result.add( matchingMethod );
                }
            }
        }
        return result;
    }

    private <T extends Method> SelectedMethod<T> getMatchingParameterBinding(Type returnType,
            SelectionContext context, SelectedMethod<T> selectedMethodInfo,
            List<List<ParameterBinding>> parameterAssignmentVariants) {

        List<List<ParameterBinding>> matchingParameterAssignmentVariants = new ArrayList<>(
            parameterAssignmentVariants
        );

        Method selectedMethod = selectedMethodInfo.getMethod();

        // remove all assignment variants that doesn't match the types from the method
        matchingParameterAssignmentVariants.removeIf( parameterAssignments ->
            !selectedMethod.matches( extractTypes( parameterAssignments ), returnType )
        );

        if ( matchingParameterAssignmentVariants.isEmpty() ) {
            // no matching variants found
            return null;
        }
        else if ( matchingParameterAssignmentVariants.size() == 1 ) {
            // we found exactly one set of variants, use this
            selectedMethodInfo.setParameterBindings( first( matchingParameterAssignmentVariants ) );
            return selectedMethodInfo;
        }

        // more than one variant matches, try to find one where also the parameter names are matching
        // -> remove all variants where the binding var-name doesn't match the var-name of the parameter
        List<Parameter> methodParameters = selectedMethod.getParameters();

        matchingParameterAssignmentVariants.removeIf( parameterBindings ->
            parameterBindingNotMatchesParameterVariableNames(
                parameterBindings,
                methodParameters
            )
        );


        if ( matchingParameterAssignmentVariants.isEmpty() ) {
            // we had some matching assignments before, but when checking the parameter names we can't find an
            // appropriate one, in this case the user must chose identical parameter names for the mapping and lifecycle
            // method
            messager.printMessage(
                selectedMethod.getExecutable(),
                Message.LIFECYCLEMETHOD_AMBIGUOUS_PARAMETERS,
                context.getMappingMethod()
            );

            return null;
        }

        // there should never be more then one assignment left after checking the parameter names as it is not possible
        // to use the same parameter name more then once
        // -> we can use the first variant that is left (that should also be the only one)
        selectedMethodInfo.setParameterBindings( first( matchingParameterAssignmentVariants ) );
        return selectedMethodInfo;
    }

    /**
     * Checks if the given parameter-bindings have the same variable name than the given parameters.<br>
     * The first entry in the parameter-bindings belongs to the first entry in the parameters and so on.<br>
     *
     * @param parameterBindings List of parameter bindings
     * @param parameters List of parameters, must have the same size than the {@code parameterBindings} list
     *
     * @return {@code true} as soon as there is a parameter with a different variable name than the binding
     */
    private boolean parameterBindingNotMatchesParameterVariableNames(List<ParameterBinding> parameterBindings,
                                                                     List<Parameter> parameters) {
        if ( parameterBindings.size() != parameters.size() ) {
            return true;
        }

        int i = 0;
        for ( ParameterBinding parameterBinding : parameterBindings ) {
            Parameter parameter = parameters.get( i++ );

            // if the parameterBinding contains a parameter name we must ensure that this matches the name from the
            // method parameter -> remove all variants where this is not the case
            if ( parameterBinding.getVariableName() != null &&
                !parameter.getName().equals( parameterBinding.getVariableName() ) ) {
                return true;
            }
        }

        return false;
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
                && parameter.isMappingContext() == candidate.isMappingContext()
                && parameter.isSourcePropertyName() == candidate.isSourcePropertyName()
                && parameter.isTargetPropertyName() == candidate.isTargetPropertyName()) {
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
