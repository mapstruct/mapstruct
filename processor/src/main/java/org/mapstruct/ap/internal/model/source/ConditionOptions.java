/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.gem.ConditionGem;
import org.mapstruct.ap.internal.gem.ConditionStrategyGem;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

/**
 * @author Filip Hrisafov
 */
public class ConditionOptions {

    private final Set<ConditionStrategyGem> conditionStrategies;

    private ConditionOptions(Set<ConditionStrategyGem> conditionStrategies) {
        this.conditionStrategies = conditionStrategies;
    }

    public Collection<ConditionStrategyGem> getConditionStrategies() {
        return conditionStrategies;
    }

    public static ConditionOptions getInstanceOn(ConditionGem condition, ExecutableElement method,
                                                 List<Parameter> parameters,
                                                 FormattingMessager messager) {
        if ( condition == null ) {
            return null;
        }

        TypeMirror returnType = method.getReturnType();
        TypeKind returnTypeKind = returnType.getKind();
        // We only allow methods that return boolean or Boolean to be condition methods
        if ( returnTypeKind != TypeKind.BOOLEAN ) {
            if ( returnTypeKind != TypeKind.DECLARED ) {
                return null;
            }
            DeclaredType declaredType = (DeclaredType) returnType;
            TypeElement returnTypeElement = (TypeElement) declaredType.asElement();
            if ( !returnTypeElement.getQualifiedName().contentEquals( Boolean.class.getCanonicalName() ) ) {
                return null;
            }
        }

        Set<ConditionStrategyGem> strategies = condition.appliesTo().get()
            .stream()
            .map( ConditionStrategyGem::valueOf )
            .collect( Collectors.toCollection( () -> EnumSet.noneOf( ConditionStrategyGem.class ) ) );

        if ( strategies.isEmpty() ) {
            messager.printMessage(
                method,
                condition.mirror(),
                condition.appliesTo().getAnnotationValue(),
                Message.CONDITION_MISSING_APPLIES_TO_STRATEGY
            );

            return null;
        }

        boolean allStrategiesValid = true;

        for ( ConditionStrategyGem strategy : strategies ) {
            boolean isStrategyValid = isValid( strategy, condition, method, parameters, messager );
            allStrategiesValid &= isStrategyValid;
        }

        return allStrategiesValid ? new ConditionOptions( strategies ) : null;
    }

    protected static boolean isValid(ConditionStrategyGem strategy, ConditionGem condition,
                                     ExecutableElement method, List<Parameter> parameters,
                                     FormattingMessager messager) {
        if ( strategy == ConditionStrategyGem.SOURCE_PARAMETERS ) {
            return hasValidStrategyForSourceProperties( condition, method, parameters, messager );
        }
        else if ( strategy == ConditionStrategyGem.PROPERTIES ) {
            return hasValidStrategyForProperties( condition, method, parameters, messager );
        }
        else {
            throw new IllegalStateException( "Invalid condition strategy: " + strategy );
        }
    }

    protected static boolean hasValidStrategyForSourceProperties(ConditionGem condition, ExecutableElement method,
                                                                 List<Parameter> parameters,
                                                                 FormattingMessager messager) {
        for ( Parameter parameter : parameters ) {
            if ( parameter.isSourceParameter() ) {
                // source parameter is a valid parameter for a source condition check
                continue;
            }

            if ( parameter.isMappingContext() ) {
                // mapping context parameter is a valid parameter for a source condition check
                continue;
            }

            messager.printMessage(
                method,
                condition.mirror(),
                Message.CONDITION_SOURCE_PARAMETERS_INVALID_PARAMETER,
                parameter.describe()
            );
            return false;
        }
        return true;
    }

    protected static boolean hasValidStrategyForProperties(ConditionGem condition, ExecutableElement method,
                                                           List<Parameter> parameters,
                                                           FormattingMessager messager) {
        for ( Parameter parameter : parameters ) {
            if ( parameter.isSourceParameter() ) {
                // source parameter is a valid parameter for a property condition check
                continue;
            }

            if ( parameter.isMappingContext() ) {
                // mapping context parameter is a valid parameter for a property condition check
                continue;
            }

            if ( parameter.isTargetType() ) {
                // target type parameter is a valid parameter for a property condition check
                continue;
            }

            if ( parameter.isMappingTarget() ) {
                // mapping target parameter is a valid parameter for a property condition check
                continue;
            }

            if ( parameter.isSourcePropertyName() ) {
                // source property name parameter is a valid parameter for a property condition check
                continue;
            }

            if ( parameter.isTargetPropertyName() ) {
                // target property name parameter is a valid parameter for a property condition check
                continue;
            }

            messager.printMessage(
                method,
                condition.mirror(),
                Message.CONDITION_PROPERTIES_INVALID_PARAMETER,
                parameter
            );
            return false;
        }
        return true;
    }
}
