/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mapstruct.ap.internal.model.common.Parameter;

/**
 * Provides access to the {@link SourceMethod}s that are provided by {@link org.mapstruct.Context} parameters of a
 * {@link Method} and maintains the relationship between those methods and their originating parameter.
 *
 * @author Andreas Gudian
 */
public class ParameterProvidedMethods {
    private static final ParameterProvidedMethods EMPTY =
        new ParameterProvidedMethods( Collections.emptyMap() );

    private final Map<Parameter, List<SourceMethod>> parameterToProvidedMethods;
    private final Map<SourceMethod, Parameter> methodToProvidingParameter;

    private ParameterProvidedMethods(Map<Parameter, List<SourceMethod>> parameterToProvidedMethods) {
        this.parameterToProvidedMethods = parameterToProvidedMethods;
        this.methodToProvidingParameter = new IdentityHashMap<>();
        for ( Entry<Parameter, List<SourceMethod>> entry : parameterToProvidedMethods.entrySet() ) {
            for ( SourceMethod method : entry.getValue() ) {
                methodToProvidingParameter.put( method, entry.getKey() );
            }
        }
    }

    /**
     * @param orderedParameters The parameters of which the provided methods are to be returned.
     * @return The methods provided by the given parameters in the order as defined by the parameter list, with the
     *         methods of each parameter ordered based on their definition in that parameter's type.
     */
    public List<SourceMethod> getAllProvidedMethodsInParameterOrder(List<Parameter> orderedParameters) {
        List<SourceMethod> result = new ArrayList<>();

        for ( Parameter parameter : orderedParameters ) {
            List<SourceMethod> methods = parameterToProvidedMethods.get( parameter );
            if ( methods != null ) {
                result.addAll( methods );
            }
        }

        return result;
    }

    /**
     * @param method The method for which the defining parameter is to be returned.
     * @return The Parameter on which's type the provided method is defined, or {@code null} if the method was not
     *         defined on one of the tracked parameters.
     */
    public Parameter getParameterForProvidedMethod(Method method) {
        return methodToProvidingParameter.get( method );
    }

    /**
     * @return {@code true}, if no methods are provided by the tracked parameters or no parameters are tracked at all.
     */
    public boolean isEmpty() {
        return methodToProvidingParameter.isEmpty();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static ParameterProvidedMethods empty() {
        return EMPTY;
    }

    public static class Builder {
        private Map<Parameter, List<SourceMethod>> contextProvidedMethods =
            new HashMap<>();

        private Builder() {
        }

        public void addMethodsForParameter(Parameter param, List<SourceMethod> methods) {
            contextProvidedMethods.put( param, methods );
        }

        public ParameterProvidedMethods build() {
            return new ParameterProvidedMethods( contextProvidedMethods );
        }
    }
}
