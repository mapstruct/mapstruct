/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.ParameterProvidedMethods;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.selector.MethodSelectors;
import org.mapstruct.ap.internal.model.source.selector.SelectedMethod;
import org.mapstruct.ap.internal.model.source.selector.SelectionCriteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Factory for creating conditional mapping resolvers
 *
 * @author Desislav Petrov
 */
public final class ConditionMethodResolver {

    private ConditionMethodResolver() {
    }

    public static List<SelectedMethod<SourceMethod>> getConditionalMappingMethods(
            Method method,
            SelectionParameters selectionParameters,
            String conditionalMethodName,
            MappingBuilderContext ctx) {

        MethodSelectors selectors = new MethodSelectors(
                ctx.getTypeUtils(), ctx.getElementUtils(), ctx.getTypeFactory(), ctx.getMessager() );

        return filterByName( selectors.getMatchingMethods(
                method,
                getAllAvailableMethods( method, ctx.getSourceModel() ),
                Arrays.asList( method.getSourceParameters().get( 0 ).getType() ),
                ctx.getTypeFactory().getType( Boolean.class ),
                SelectionCriteria.
                        forMappingMethods( selectionParameters, null, null, false ) ), conditionalMethodName );
    }

    private static List<SelectedMethod<SourceMethod>> filterByName(
            List<SelectedMethod<SourceMethod>> source,
            String conditionalMethodName) {

        if ( source == null || source.isEmpty() ) {
            return source;
        }
        else {
            String[] split = conditionalMethodName.split( "\\." );
            return source.stream().filter( e -> e.getMethod().getName().equals( split[split.length - 1] ) )
                                  .collect( toList() );
        }
    }

    private static List<SourceMethod> getAllAvailableMethods( Method method, List<SourceMethod> sourceModelMethods ) {
        ParameterProvidedMethods contextProvidedMethods = method.getContextProvidedMethods();
        if ( contextProvidedMethods.isEmpty() ) {
            return sourceModelMethods;
        }

        List<SourceMethod> methodsProvidedByParams = contextProvidedMethods
            .getAllProvidedMethodsInParameterOrder( method.getContextParameters() );

        List<SourceMethod> availableMethods =
            new ArrayList<>( methodsProvidedByParams.size() + sourceModelMethods.size() );

        availableMethods.addAll( methodsProvidedByParams );
        availableMethods.addAll( sourceModelMethods );

        return availableMethods;
    }

}
