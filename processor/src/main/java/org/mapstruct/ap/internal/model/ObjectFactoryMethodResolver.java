/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;

import org.mapstruct.ap.internal.model.common.BuilderType;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.ParameterProvidedMethods;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.selector.MethodSelectors;
import org.mapstruct.ap.internal.model.source.selector.SelectedMethod;
import org.mapstruct.ap.internal.model.source.selector.SelectionCriteria;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 *
 * @author Sjaak Derksen
 */
public class ObjectFactoryMethodResolver {

    private ObjectFactoryMethodResolver() {
    }

    /**
     * returns a no arg factory method
     *
     * @param method target mapping method
     * @param selectionParameters parameters used in the selection process
     * @param ctx
     *
     * @return a method reference to the factory method, or null if no suitable, or ambiguous method found
     *
     */
    public static MethodReference getFactoryMethod( Method method,
                                                    SelectionParameters selectionParameters,
                                                    MappingBuilderContext ctx) {
        return getFactoryMethod( method, method.getResultType(), selectionParameters, ctx );
    }



    /**
     * returns a no arg factory method
     *
     * @param method target mapping method
     * @param alternativeTarget alternative to {@link Method#getResultType()} e.g. when target is abstract
     * @param selectionParameters parameters used in the selection process
     * @param ctx
     *
     * @return a method reference to the factory method, or null if no suitable, or ambiguous method found
     *
     */
    public static MethodReference getFactoryMethod( Method method,
        Type alternativeTarget,
        SelectionParameters selectionParameters,
        MappingBuilderContext ctx) {

        MethodSelectors selectors =
            new MethodSelectors( ctx.getTypeUtils(), ctx.getElementUtils(), ctx.getTypeFactory(), ctx.getMessager() );

        List<SelectedMethod<SourceMethod>> matchingFactoryMethods =
            selectors.getMatchingMethods(
                method,
                getAllAvailableMethods( method, ctx.getSourceModel() ),
                java.util.Collections.<Type> emptyList(),
                alternativeTarget,
                SelectionCriteria.forFactoryMethods( selectionParameters ) );

        if (matchingFactoryMethods.isEmpty()) {
            return null;
        }

        if ( matchingFactoryMethods.size() > 1 ) {
            ctx.getMessager().printMessage(
                method.getExecutable(),
                Message.GENERAL_AMBIGIOUS_FACTORY_METHOD,
                alternativeTarget,
                Strings.join( matchingFactoryMethods, ", " ) );

            return null;
        }

        SelectedMethod<SourceMethod> matchingFactoryMethod = first( matchingFactoryMethods );

        Parameter providingParameter =
                method.getContextProvidedMethods().getParameterForProvidedMethod( matchingFactoryMethod.getMethod() );

        if ( providingParameter != null ) {
            return MethodReference.forParameterProvidedMethod(
                matchingFactoryMethod.getMethod(),
                providingParameter,
                matchingFactoryMethod.getParameterBindings() );
        }
        else {
            MapperReference ref = MapperReference.findMapperReference(
                ctx.getMapperReferences(),
                matchingFactoryMethod.getMethod() );

            return MethodReference.forMapperReference(
                matchingFactoryMethod.getMethod(),
                ref,
                matchingFactoryMethod.getParameterBindings() );
        }
    }

    public static MethodReference getBuilderFactoryMethod(Method method, BuilderType builder) {
        if ( builder == null ) {
            return null;
        }

        ExecutableElement builderCreationMethod = builder.getBuilderCreationMethod();
        if ( builderCreationMethod.getKind() == ElementKind.CONSTRUCTOR ) {
            // If the builder creation method is a constructor it would be handled properly down the line
            return null;
        }

        if ( !builder.getBuildingType().isAssignableTo( method.getReturnType() ) ) {
            //TODO print error message
            return null;
        }

        return MethodReference.forStaticBuilder(
            builderCreationMethod.getSimpleName().toString(),
            builder.getOwningType()
        );
    }

    private static List<SourceMethod> getAllAvailableMethods(Method method, List<SourceMethod> sourceModelMethods) {
        ParameterProvidedMethods contextProvidedMethods = method.getContextProvidedMethods();
        if ( contextProvidedMethods.isEmpty() ) {
            return sourceModelMethods;
        }

        List<SourceMethod> methodsProvidedByParams = contextProvidedMethods
            .getAllProvidedMethodsInParameterOrder( method.getContextParameters() );

        List<SourceMethod> availableMethods =
            new ArrayList<>( methodsProvidedByParams.size() + sourceModelMethods.size() );

        for ( SourceMethod methodProvidedByParams : methodsProvidedByParams ) {
            // add only methods from context that do have the @ObjectFactory annotation
            if ( methodProvidedByParams.hasObjectFactoryAnnotation() ) {
                availableMethods.add( methodProvidedByParams );
            }
        }
        availableMethods.addAll( sourceModelMethods );

        return availableMethods;
    }

}
