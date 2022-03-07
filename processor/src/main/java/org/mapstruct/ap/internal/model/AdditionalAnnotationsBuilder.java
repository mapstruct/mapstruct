/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.gem.AnnotateWithGem;
import org.mapstruct.ap.internal.gem.AnnotateWithsGem;
import org.mapstruct.ap.internal.gem.ParameterGem;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.TypeUtils;

/**
 * @author Ben Zegveld
 * @since 1.5
 */
public class AdditionalAnnotationsBuilder {

    private TypeFactory typeFactory;
    private FormattingMessager messager;
    private TypeUtils typeUtils;

    public AdditionalAnnotationsBuilder(TypeUtils typeUtils, TypeFactory typeFactory, FormattingMessager messager) {
        this.typeUtils = typeUtils;
        this.typeFactory = typeFactory;
        this.messager = messager;
    }

    public Set<Annotation> getAdditionalAnnotations(TypeElement element) {
        LinkedHashSet<Annotation> additionalAnnotations = new LinkedHashSet<>();
        AnnotateWithGem annotationGem = AnnotateWithGem.instanceOn( element );
        if ( annotationGem != null ) {
            buildAnnotation( annotationGem, element ).ifPresent( additionalAnnotations::add );
        }
        AnnotateWithsGem annotationsGem = AnnotateWithsGem.instanceOn( element );
        if ( annotationsGem != null ) {
            for ( AnnotateWithGem annotateWithGem : annotationsGem.value().get() ) {
                buildAnnotation( annotateWithGem, element ).ifPresent( additionalAnnotations::add );
            }
        }
        return additionalAnnotations;
    }

    private Optional<Annotation> buildAnnotation(AnnotateWithGem annotationGem, TypeElement element) {
        List<ParameterGem> parameters = annotationGem.parameters().get();
        Type annotationType = typeFactory.getType( annotationGem.value().getValue() );
        if ( isValid( annotationType, parameters, element ) ) {
            return Optional.of( new Annotation( annotationType, convertToJava( parameters ) ) );
        }
        return Optional.empty();
    }

    private List<String> convertToJava(List<ParameterGem> parameters) {
        return parameters.stream().map( this::convertToJava ).collect( Collectors.toList() );
    }

    private String convertToJava(ParameterGem parameter) {
        if ( parameter.strings().hasValue() ) {
            if ( parameter.strings().get().size() == 1 ) {
                return String.format( "%s = \"%s\"", parameter.key().get(), parameter.strings().get().get( 0 ) );
            }
        }
        if ( parameter.classes().hasValue() ) {
            if ( parameter.classes().get().size() == 1 ) {
                return String.format( "%s = %s.class", parameter.key().get(), parameter.classes().get().get( 0 ) );
            }
        }
        return "";
    }

    private boolean isValid(Type annotationType, List<ParameterGem> parameters, TypeElement element) {
        boolean isValid = true;
        if ( !allRequiredParametersArePresent( annotationType, parameters, element ) ) {
            isValid = false;
        }
        if ( !allParametersAreKnownInAnnotation( annotationType, parameters, element ) ) {
            isValid = false;
        }
        if ( !allParametersAreOfCorrectType( annotationType, parameters, element ) ) {
            isValid = false;
        }
        return isValid;
    }

    private boolean allParametersAreKnownInAnnotation(Type annotationType, List<ParameterGem> parameters,
                                                      TypeElement element) {
        List<String> allowedAnnotationParameters = annotationType
                                                                 .findAllAnnotationParameters()
                                                                 .map( ee -> ee.getSimpleName().toString() )
                                                                 .collect( Collectors.toList() );
        boolean isValid = true;
        for ( ParameterGem parameter : parameters ) {
            if ( parameter.key().isValid() && !allowedAnnotationParameters.contains( parameter.key().get() ) ) {
                isValid = false;
                messager
                        .printMessage(
                            element,
                            Message.ANNOTATE_WITH_UNKNOWN_PARAMETER,
                            parameter.key().get(),
                            annotationType );
            }
        }
        return isValid;
    }

    private boolean allRequiredParametersArePresent(Type annotationType, List<ParameterGem> parameters,
                                                    TypeElement element) {
        List<ExecutableElement> undefinedParameters =
            annotationType.findAllAnnotationParameters()
                        .filter( ee -> ee.getDefaultValue() == null )
                        .filter( ee -> parameters.stream()
                                                 .noneMatch( p -> p.key().isValid()
                                                       && p.key().get().equals( ee.getSimpleName().toString() ) ) )
                        .collect( Collectors.toList() );
        if ( undefinedParameters.isEmpty() ) {
            return true;
        }
        for ( ExecutableElement ee : undefinedParameters ) {
            messager
                    .printMessage(
                        element,
                        Message.ANNOTATE_WITH_MISSING_REQUIRED_PARAMETER,
                        ee.getSimpleName(),
                        annotationType );
        }
        return false;
    }

    private boolean allParametersAreOfCorrectType(Type annotationType, List<ParameterGem> parameters,
                                                  TypeElement element) {
        Map<String, ExecutableElement> annotationParameters =
            annotationType
                          .findAllAnnotationParameters()
                          .collect( Collectors.toMap( ee -> ee.getSimpleName().toString(), Function.identity() ) );
        boolean isValid = true;
        for ( ParameterGem parameter : parameters ) {
            TypeMirror annotationParameterType = getAnnotationParameterType( annotationParameters, parameter );
            List<TypeMirror> parameterTypes = getParameterType( parameter );
            for ( TypeMirror parameterType : parameterTypes ) {
                if ( parameterType != null && annotationParameterType != null
                    && (
                        !typeUtils.isSameType( annotationParameterType, parameterType )
                     && !typeUtils.isAssignable( parameterType, getTypeBound( annotationParameterType ) )
                       ) ) {
                    isValid = false;
                    messager
                            .printMessage(
                                element,
                                parameter.mirror(),
                                Message.ANNOTATE_WITH_WRONG_PARAMETER,
                                parameter.key().get(),
                                parameterType,
                                annotationParameterType,
                                annotationType );
                }
            }
        }
        return isValid;
    }

    private TypeMirror getTypeBound(TypeMirror annotationParameterType) {
        List<Type> typeParameters = typeFactory.getType( annotationParameterType ).getTypeParameters();
        if ( typeParameters.size() != 1 ) {
            return annotationParameterType;
        }
        return typeFactory.getTypeBound( typeParameters.get( 0 ).getTypeMirror() );
    }

    private List<TypeMirror> getParameterType(ParameterGem parameter) {
        if ( parameter.booleans().hasValue() ) {
            return Collections.singletonList( typeFactory.getType( boolean.class ).getTypeMirror() );
        }
        if ( parameter.bytes().hasValue() ) {
            return Collections.singletonList( typeFactory.getType( byte.class ).getTypeMirror() );
        }
        if ( parameter.chars().hasValue() ) {
            return Collections.singletonList( typeFactory.getType( char.class ).getTypeMirror() );
        }
        if ( parameter.classes().hasValue() ) {
            return parameter.classes().get();
        }
        if ( parameter.doubles().hasValue() ) {
            return Collections.singletonList( typeFactory.getType( double.class ).getTypeMirror() );
        }
        if ( parameter.floats().hasValue() ) {
            return Collections.singletonList( typeFactory.getType( float.class ).getTypeMirror() );
        }
        if ( parameter.ints().hasValue() ) {
            return Collections.singletonList( typeFactory.getType( int.class ).getTypeMirror() );
        }
        if ( parameter.longs().hasValue() ) {
            return Collections.singletonList( typeFactory.getType( long.class ).getTypeMirror() );
        }
        if ( parameter.shorts().hasValue() ) {
            return Collections.singletonList( typeFactory.getType( short.class ).getTypeMirror() );
        }
        if ( parameter.strings().hasValue() ) {
            return Collections.singletonList( typeFactory.getType( String.class ).getTypeMirror() );
        }
        return null;
    }

    private TypeMirror getAnnotationParameterType(Map<String, ExecutableElement> annotationParameters,
                                                  ParameterGem parameter) {
        if ( parameter.key().hasValue() && annotationParameters.containsKey( parameter.key().get() ) ) {
            return annotationParameters.get( parameter.key().get() ).getReturnType();
        }
        else {
            return null;
        }
    }
}
