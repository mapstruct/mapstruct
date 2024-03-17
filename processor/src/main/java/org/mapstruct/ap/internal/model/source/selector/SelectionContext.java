/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.common.SourceRHS;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;

/**
 * Context passed to the selectors to get the information they need.
 *
 * @author Filip Hrisafov
 */
public class SelectionContext {

    private final Type sourceType;
    private final SelectionCriteria selectionCriteria;
    private final Method mappingMethod;
    private final Type mappingTargetType;
    private final Type returnType;
    private final Supplier<List<ParameterBinding>> parameterBindingsProvider;
    private List<ParameterBinding> parameterBindings;

    private SelectionContext(Type sourceType, SelectionCriteria selectionCriteria, Method mappingMethod,
                             Type mappingTargetType, Type returnType,
                             Supplier<List<ParameterBinding>> parameterBindingsProvider) {
        this.sourceType = sourceType;
        this.selectionCriteria = selectionCriteria;
        this.mappingMethod = mappingMethod;
        this.mappingTargetType = mappingTargetType;
        this.returnType = returnType;
        this.parameterBindingsProvider = parameterBindingsProvider;
    }

    /**
     * @return the source type that should be matched
     */
    public Type getSourceType() {
        return sourceType;
    }

    /**
     * @return the criteria used in the selection process
     */
    public SelectionCriteria getSelectionCriteria() {
        return selectionCriteria;
    }

    /**
     * @return the mapping target type that should be matched
     */
    public Type getMappingTargetType() {
        return mappingTargetType;
    }

    /**
     * @return the return type that should be matched
     */
    public Type getReturnType() {
        return returnType;
    }

    /**
     * @return the available parameter bindings for the matching
     */
    public List<ParameterBinding> getAvailableParameterBindings() {
        if ( this.parameterBindings == null ) {
            this.parameterBindings = this.parameterBindingsProvider.get();
        }
        return parameterBindings;
    }

    /**
     * @return the mapping method, defined in Mapper for which this selection is carried out
     */
    public Method getMappingMethod() {
        return mappingMethod;
    }

    public static SelectionContext forMappingMethods(Method mappingMethod, Type source, Type target,
                                                     SelectionCriteria criteria, TypeFactory typeFactory) {
        return new SelectionContext(
            source,
            criteria,
            mappingMethod,
            target,
            target,
            () -> getAvailableParameterBindingsFromSourceType(
                source,
                target,
                mappingMethod,
                typeFactory
            )
        );
    }

    public static SelectionContext forLifecycleMethods(Method mappingMethod, Type targetType,
                                                       SelectionParameters selectionParameters,
                                                       TypeFactory typeFactory) {
        SelectionCriteria criteria = SelectionCriteria.forLifecycleMethods( selectionParameters );
        return new SelectionContext(
            null,
            criteria,
            mappingMethod,
            targetType,
            mappingMethod.getResultType(),
            () -> getAvailableParameterBindingsFromMethod(
                mappingMethod,
                targetType,
                criteria.getSourceRHS(),
                typeFactory
            )
        );
    }

    public static SelectionContext forFactoryMethods(Method mappingMethod, Type alternativeTarget,
                                                     SelectionParameters selectionParameters,
                                                     TypeFactory typeFactory) {
        SelectionCriteria criteria = SelectionCriteria.forFactoryMethods( selectionParameters );
        return new SelectionContext(
            null,
            criteria,
            mappingMethod,
            alternativeTarget,
            alternativeTarget,
            () -> getAvailableParameterBindingsFromMethod(
                mappingMethod,
                alternativeTarget,
                criteria.getSourceRHS(),
                typeFactory
            )
        );
    }

    public static SelectionContext forPresenceCheckMethods(Method mappingMethod,
                                                           SelectionParameters selectionParameters,
                                                           TypeFactory typeFactory) {
        SelectionCriteria criteria = SelectionCriteria.forPresenceCheckMethods( selectionParameters );
        Type booleanType = typeFactory.getType( Boolean.class );
        return new SelectionContext(
            null,
            criteria,
            mappingMethod,
            booleanType,
            booleanType,
            () -> getAvailableParameterBindingsFromMethod(
                mappingMethod,
                booleanType,
                criteria.getSourceRHS(),
                typeFactory
            )
        );
    }

    public static SelectionContext forSourceParameterPresenceCheckMethods(Method mappingMethod,
                                                                          SelectionParameters selectionParameters,
                                                                          Parameter sourceParameter,
                                                                          TypeFactory typeFactory) {
        SelectionCriteria criteria = SelectionCriteria.forSourceParameterCheckMethods( selectionParameters );
        Type booleanType = typeFactory.getType( Boolean.class );
        return new SelectionContext(
            null,
            criteria,
            mappingMethod,
            booleanType,
            booleanType,
            () -> getParameterBindingsForSourceParameterPresenceCheck(
                mappingMethod,
                booleanType,
                sourceParameter,
                typeFactory
            )
        );
    }

    private static List<ParameterBinding> getParameterBindingsForSourceParameterPresenceCheck(Method method,
                                                                                              Type targetType,
                                                                                              Parameter sourceParameter,
                                                                                              TypeFactory typeFactory) {

        List<ParameterBinding> availableParams = new ArrayList<>( method.getParameters().size() + 3 );

        availableParams.add( ParameterBinding.fromParameter( sourceParameter ) );
        availableParams.add( ParameterBinding.forTargetTypeBinding( typeFactory.classTypeOf( targetType ) ) );
        for ( Parameter parameter : method.getParameters() ) {
            if ( !parameter.isSourceParameter( ) ) {
                availableParams.add( ParameterBinding.fromParameter( parameter ) );
            }
        }

        return availableParams;
    }

    private static List<ParameterBinding> getAvailableParameterBindingsFromMethod(Method method, Type targetType,
                                                                                  SourceRHS sourceRHS,
                                                                                  TypeFactory typeFactory) {
        List<ParameterBinding> availableParams = new ArrayList<>( method.getParameters().size() + 3 );

        if ( sourceRHS != null ) {
            availableParams.addAll( ParameterBinding.fromParameters( method.getParameters() ) );
            availableParams.add( ParameterBinding.fromSourceRHS( sourceRHS ) );
            addSourcePropertyNameBindings( availableParams, sourceRHS.getSourceType(), typeFactory );
        }
        else {
            availableParams.addAll( ParameterBinding.fromParameters( method.getParameters() ) );
        }

        addTargetRelevantBindings( availableParams, targetType, typeFactory );

        return availableParams;
    }

    private static List<ParameterBinding> getAvailableParameterBindingsFromSourceType(Type sourceType,
                                                                                      Type targetType,
                                                                                      Method mappingMethod,
                                                                                      TypeFactory typeFactory) {

        List<ParameterBinding> availableParams = new ArrayList<>();

        availableParams.add( ParameterBinding.forSourceTypeBinding( sourceType ) );
        addSourcePropertyNameBindings( availableParams, sourceType, typeFactory );

        for ( Parameter param : mappingMethod.getParameters() ) {
            if ( param.isMappingContext() ) {
                availableParams.add( ParameterBinding.fromParameter( param ) );
            }
        }

        addTargetRelevantBindings( availableParams, targetType, typeFactory );

        return availableParams;
    }

    private static void addSourcePropertyNameBindings(List<ParameterBinding> availableParams, Type sourceType,
                                                      TypeFactory typeFactory) {

        boolean sourcePropertyNameAvailable = false;
        for ( ParameterBinding pb : availableParams ) {
            if ( pb.isSourcePropertyName() ) {
                sourcePropertyNameAvailable = true;
                break;
            }
        }
        if ( !sourcePropertyNameAvailable ) {
            availableParams.add( ParameterBinding.forSourcePropertyNameBinding( typeFactory.getType( String.class ) ) );
        }

    }

    /**
     * Adds default parameter bindings for the mapping-target and target-type if not already available.
     *
     * @param availableParams Already available params, new entries will be added to this list
     * @param targetType      Target type
     */
    private static void addTargetRelevantBindings(List<ParameterBinding> availableParams, Type targetType,
                                                  TypeFactory typeFactory) {
        boolean mappingTargetAvailable = false;
        boolean targetTypeAvailable = false;
        boolean targetPropertyNameAvailable = false;

        // search available parameter bindings if mapping-target and/or target-type is available
        for ( ParameterBinding pb : availableParams ) {
            if ( pb.isMappingTarget() ) {
                mappingTargetAvailable = true;
            }
            else if ( pb.isTargetType() ) {
                targetTypeAvailable = true;
            }
            else if ( pb.isTargetPropertyName() ) {
                targetPropertyNameAvailable = true;
            }
        }

        if ( !mappingTargetAvailable ) {
            availableParams.add( ParameterBinding.forMappingTargetBinding( targetType ) );
        }
        if ( !targetTypeAvailable ) {
            availableParams.add( ParameterBinding.forTargetTypeBinding( typeFactory.classTypeOf( targetType ) ) );
        }
        if ( !targetPropertyNameAvailable ) {
            availableParams.add( ParameterBinding.forTargetPropertyNameBinding( typeFactory.getType( String.class ) ) );
        }
    }

}
