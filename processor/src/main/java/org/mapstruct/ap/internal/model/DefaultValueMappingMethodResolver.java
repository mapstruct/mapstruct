/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.AnnotationMirror;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.ParameterProvidedMethods;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.selector.MethodSelectors;
import org.mapstruct.ap.internal.model.source.selector.SelectedMethod;
import org.mapstruct.ap.internal.model.source.selector.SelectionContext;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * Utility for resolving handler methods for unmapped enum branches in
 * {@link org.mapstruct.ValueMapping}.
 * <p>
 * Specifically used for cases like:
 * <pre>{@code
 *   @ValueMapping(
 *     source = MappingConstants.ANY_UNMAPPED,
 *     qualifiedByName = "unknownMapping"
 *   )}
 * </pre>
 * When {@code MappingConstants#ANY_UNMAPPED} is specified, this resolver finds a user-defined
 * method (typically annotated with {@link org.mapstruct.Named}) to be invoked in the
 * default branch of the generated switch statement.
 *
 * <p>
 * The handler method's parameters will be filled by the source parameters of the mapping method
 * where possible. If the handler method has a return value (i.e., its return type is not void),
 * a return statement will be generated and no further statements will be executed in the default
 * branch. If the handler method is void, it will be invoked for side effects (such as logging)
 * and subsequent statements in the default branch will still be executed.
 * <p>
 *
 * <p>
 * This behavior is consistent with MapStruct's handling of {@link org.mapstruct.AfterMapping} and
 * {@link org.mapstruct.BeforeMapping} lifecycle methods.
 * <p>
 * This allows users to insert custom logic (such as logging, exception handling, etc.)
 * for unmapped enum values during mapping.
 */
public final class DefaultValueMappingMethodResolver {

    private DefaultValueMappingMethodResolver() {
    }

    /**
     * Finds a matching method for unmapped branch handling.
     *
     * @param method                The mapping method.
     * @param selectionParameters   Selection parameters for method matching.
     * @param ctx                   Builder context.
     * @return The matched MethodReference, or null if none found.
     */
    public static MethodReference getMatchingMethods(Method method, SelectionParameters selectionParameters,
                                                     AnnotationMirror positionHint, MappingBuilderContext ctx) {
        if (selectionParameters.getQualifiers().isEmpty() && selectionParameters.getQualifyingNames().isEmpty()) {
            return null;
        }
        List<SourceMethod> namedMethods = getAllAvailableMethods( method, ctx.getSourceModel() );
        MethodSelectors selectors = new MethodSelectors( ctx.getTypeUtils(), ctx.getElementUtils(), ctx.getMessager() );
        Type targetType = method.getReturnType();
        List<SelectedMethod<SourceMethod>> matchingMethods = selectors.getMatchingMethods(
            namedMethods,
            SelectionContext.forDefaultValueMappingMethod(
                method,
                targetType,
                selectionParameters,
                ctx.getTypeFactory()
            )
        );
        if ( matchingMethods.isEmpty() ) {
            return null;
        }

        reportErrorWhenAmbiguous( method, matchingMethods, targetType, positionHint, ctx );

        List<MethodReference> result = new ArrayList<>(matchingMethods.size());
        for ( SelectedMethod<SourceMethod> candidate : matchingMethods ) {
            Parameter providingParameter =
                method.getContextProvidedMethods().getParameterForProvidedMethod( candidate.getMethod() );

            MapperReference mapperReference = MapperReference.findMapperReference(
                ctx.getMapperReferences(), candidate.getMethod() );

            result.add( new MethodReference(
                candidate.getMethod(),
                mapperReference,
                providingParameter,
                candidate.getParameterBindings()
            ) );
        }
        return first( result );
    }

    private static <T extends Method> void reportErrorWhenAmbiguous(Method mappingMethod,
                                                                    List<SelectedMethod<T>> candidates,
                                                                    Type target,
                                                                    AnnotationMirror positionHint,
                                                                    MappingBuilderContext ctx) {
        // raise an error if more than one mapping method is suitable
        if ( candidates.size() <= 1 ) {
            return;
        }
        FormattingMessager messager = ctx.getMessager();
        messager.printMessage(
            mappingMethod.getExecutable(),
            positionHint,
            Message.GENERAL_AMBIGUOUS_MAPPING_METHOD,
            null,
            target.describe(),
            join( candidates, ctx )
        );
    }

    private static <T extends Method> String join(List<SelectedMethod<T>> candidates, MappingBuilderContext ctx) {
        int reportingLimitAmbiguous = ctx.getOptions().isVerbose() ? Integer.MAX_VALUE : 5;
        String candidateStr = candidates.stream()
            .limit( reportingLimitAmbiguous )
            .map( m -> m.getMethod().describe() )
            .collect( Collectors.joining( ", " ) );

        if ( candidates.size() > reportingLimitAmbiguous ) {
            candidateStr += String.format( "... and %s more", candidates.size() - reportingLimitAmbiguous );
        }
        return candidateStr;
    }

    /**
     * Gets all available methods from context and source.
     *
     * @param method             The mapping method.
     * @param sourceModelMethods Source methods from the model.
     * @return List of SourceMethod.
     */
    private static List<SourceMethod> getAllAvailableMethods(
        Method method,
        List<SourceMethod> sourceModelMethods) {
        ParameterProvidedMethods contextProvidedMethods = method.getContextProvidedMethods();
        List<SourceMethod> allMethods = new ArrayList<>();
        if ( !contextProvidedMethods.isEmpty() ) {
            allMethods.addAll(
                contextProvidedMethods.getAllProvidedMethodsInParameterOrder( method.getContextParameters() )
            );
        }
        allMethods.addAll( sourceModelMethods );
        return allMethods;
    }
}
