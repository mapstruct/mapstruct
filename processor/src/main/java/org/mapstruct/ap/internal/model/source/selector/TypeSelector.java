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

import static org.mapstruct.ap.internal.util.Collections.first;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.MethodMatcher;

/**
 * Selects those methods from the given input set which match the given source and target types (via
 * {@link MethodMatcher}).
 *
 * @author Sjaak Derksen
 */
public class TypeSelector implements MethodSelector {

    private TypeFactory typeFactory;

    public TypeSelector(TypeFactory typeFactory) {
        this.typeFactory = typeFactory;
    }

    @Override
    public <T extends Method> List<SelectedMethod<T>> getMatchingMethods(Method mappingMethod,
            List<SelectedMethod<T>> methods,
            List<Type> sourceTypes, Type targetType,
            SelectionCriteria criteria) {

        if ( methods.isEmpty() ) {
            return methods;
        }

        List<SelectedMethod<T>> result = new ArrayList<SelectedMethod<T>>();

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
                    getFirstMatchingParameterBinding( targetType, method, parameterBindingPermutations );

                if ( matchingMethod != null ) {
                    result.add( matchingMethod );
                }
            }
        }
        return result;
    }

    private List<ParameterBinding> getAvailableParameterBindingsFromMethod(Method method, Type targetType,
        SourceRHS sourceRHS) {
        List<ParameterBinding> availableParams = new ArrayList<ParameterBinding>( method.getParameters().size() + 3 );

        addMappingTargetAndTargetTypeBindings( availableParams, targetType );
        if ( sourceRHS != null ) {
            availableParams.addAll( ParameterBinding.fromParameters( method.getContextParameters() ) );
            availableParams.add( ParameterBinding.fromSourceRHS( sourceRHS ) );
        }
        else {
            availableParams.addAll( ParameterBinding.fromParameters( method.getParameters() ) );
        }

        return availableParams;
    }

    private List<ParameterBinding> getAvailableParameterBindingsFromSourceTypes(List<Type> sourceTypes,
            Type targetType, Method mappingMethod) {

        List<ParameterBinding> availableParams = new ArrayList<ParameterBinding>( sourceTypes.size() + 2 );

        addMappingTargetAndTargetTypeBindings( availableParams, targetType );

        for ( Type sourceType : sourceTypes ) {
            availableParams.add( ParameterBinding.forSourceTypeBinding( sourceType ) );
        }

        for ( Parameter param : mappingMethod.getParameters() ) {
            if ( param.isMappingContext() ) {
                availableParams.add( ParameterBinding.fromParameter( param ) );
            }
        }

        return availableParams;
    }

    private void addMappingTargetAndTargetTypeBindings(List<ParameterBinding> availableParams, Type targetType) {
        availableParams.add( ParameterBinding.forMappingTargetBinding( targetType ) );
        availableParams.add( ParameterBinding.forTargetTypeBinding( typeFactory.classTypeOf( targetType ) ) );
    }

    private <T extends Method> SelectedMethod<T> getFirstMatchingParameterBinding(Type targetType,
            SelectedMethod<T> method, List<List<ParameterBinding>> parameterAssignmentVariants) {

        for ( List<ParameterBinding> parameterAssignments : parameterAssignmentVariants ) {
            if ( method.getMethod().matches( extractTypes( parameterAssignments ), targetType ) ) {
                method.setParameterBindings( parameterAssignments );
                return method;
            }
        }
        return null;
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

        List<List<ParameterBinding>> bindingPermutations = new ArrayList<List<ParameterBinding>>( 1 );
        bindingPermutations.add( new ArrayList<ParameterBinding>( methodParameters.size() ) );

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
                    new ArrayList<List<ParameterBinding>>( bindingPermutations.size() * candidateBindings.size() );
                for ( List<ParameterBinding> variant : bindingPermutations ) {
                    // create a copy of each variant for each binding
                    for ( ParameterBinding binding : candidateBindings ) {
                        List<ParameterBinding> extendedVariant =
                            new ArrayList<ParameterBinding>( methodParameters.size() );
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
        List<ParameterBinding> result = new ArrayList<ParameterBinding>( candidateParameters.size() );

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
        List<Type> result = new ArrayList<Type>( parameters.size() );

        for ( ParameterBinding param : parameters ) {
            result.add( param.getType() );
        }

        return result;
    }
}
