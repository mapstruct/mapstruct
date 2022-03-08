/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
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

    private enum ConvertToJava {
        BOOLEAN( "%s = %s",
            parameter -> String.valueOf( parameter.booleans().get().get( 0 ) ),
            parameter -> parameter.booleans().hasValue() ),
        BYTE( "%s = %s",
            parameter -> "0x" + Integer.toHexString( parameter.bytes().get().get( 0 ) ),
            parameter -> parameter.bytes().hasValue() ),
        CHARACTER( "%s = '%s'",
            parameter -> String.valueOf( parameter.chars().get().get( 0 ) ),
            parameter -> parameter.chars().hasValue() ),
        CLASSES( "%s = %s.class",
            parameter -> String.valueOf( parameter.classes().get().get( 0 ) ),
            parameter -> parameter.classes().hasValue() ),
        DOUBLE( "%s = %s",
            parameter -> String.valueOf( parameter.doubles().get().get( 0 ) ),
            parameter -> parameter.doubles().hasValue() ),
        FLOAT( "%s = %sf",
            parameter -> String.valueOf( parameter.floats().get().get( 0 ) ),
            parameter -> parameter.floats().hasValue() ),
        INT( "%s = %s",
            parameter -> String.valueOf( parameter.ints().get().get( 0 ) ),
            parameter -> parameter.ints().hasValue() ),
        LONG( "%s = %sL",
            parameter -> String.valueOf( parameter.longs().get().get( 0 ) ),
            parameter -> parameter.longs().hasValue() ),
        SHORT( "%s = %s",
            parameter -> String.valueOf( parameter.shorts().get().get( 0 ) ),
            parameter -> parameter.shorts().hasValue() ),
        STRING( "%s = \"%s\"",
            parameter -> parameter.strings().get().get( 0 ),
            parameter -> parameter.strings().hasValue() );

        private String format;
        private Function<ParameterGem, String> valueExtractor;
        private Predicate<ParameterGem> usabilityChecker;

        ConvertToJava(String format, Function<ParameterGem, String> valueExtractor,
                              Predicate<ParameterGem> usabilityChecker) {
            this.format = format;
            this.valueExtractor = valueExtractor;
            this.usabilityChecker = usabilityChecker;
        }

        String toParameter(ParameterGem parameter) {
            return String.format( format, parameter.key().get(), valueExtractor.apply( parameter ) );
        }

        boolean isUsable(ParameterGem parameter) {
            return usabilityChecker.test( parameter );
        }
    }

    private String convertToJava(ParameterGem parameter) {
        for ( ConvertToJava convertToJava : ConvertToJava.values() ) {
            if ( convertToJava.isUsable( parameter ) ) {
                return convertToJava.toParameter( parameter );
            }
        }
        if ( parameter.strings().hasValue() ) {
            if ( parameter.strings().get().size() == 1 ) {
                return String.format( "%s = \"%s\"", parameter.key().get(), parameter.strings().get().get( 0 ) );
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
            List<TypeMirror> parameterTypes = getParameterTypes( parameter );
            for ( TypeMirror parameterType : parameterTypes ) {
                if ( typesArePresent( annotationParameterType, parameterType )
                    && !sameTypeOrAssignableClass( annotationParameterType, parameterType ) ) {
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

    private boolean sameTypeOrAssignableClass(TypeMirror annotationParameterType, TypeMirror parameterType) {
        return typeUtils.isSameType( annotationParameterType, parameterType )
            || typeUtils.isAssignable( parameterType, getTypeBound( annotationParameterType ) );
    }

    private boolean typesArePresent(TypeMirror annotationParameterType, TypeMirror parameterType) {
        return parameterType != null && annotationParameterType != null;
    }

    private TypeMirror getTypeBound(TypeMirror annotationParameterType) {
        List<Type> typeParameters = typeFactory.getType( annotationParameterType ).getTypeParameters();
        if ( typeParameters.size() != 1 ) {
            return annotationParameterType;
        }
        return typeFactory.getTypeBound( typeParameters.get( 0 ).getTypeMirror() );
    }

    private List<TypeMirror> getParameterTypes(ParameterGem parameter) {
        List<TypeMirror> suppliedParameterTypes = new ArrayList<>();
        if ( parameter.booleans().hasValue() ) {
            suppliedParameterTypes.add( typeFactory.getType( boolean.class ).getTypeMirror() );
        }
        if ( parameter.bytes().hasValue() ) {
            suppliedParameterTypes.add( typeFactory.getType( byte.class ).getTypeMirror() );
        }
        if ( parameter.chars().hasValue() ) {
            suppliedParameterTypes.add( typeFactory.getType( char.class ).getTypeMirror() );
        }
        if ( parameter.classes().hasValue() ) {
            suppliedParameterTypes.addAll( parameter.classes().get() );
        }
        if ( parameter.doubles().hasValue() ) {
            suppliedParameterTypes.add( typeFactory.getType( double.class ).getTypeMirror() );
        }
        if ( parameter.floats().hasValue() ) {
            suppliedParameterTypes.add( typeFactory.getType( float.class ).getTypeMirror() );
        }
        if ( parameter.ints().hasValue() ) {
            suppliedParameterTypes.add( typeFactory.getType( int.class ).getTypeMirror() );
        }
        if ( parameter.longs().hasValue() ) {
            suppliedParameterTypes.add( typeFactory.getType( long.class ).getTypeMirror() );
        }
        if ( parameter.shorts().hasValue() ) {
            suppliedParameterTypes.add( typeFactory.getType( short.class ).getTypeMirror() );
        }
        if ( parameter.strings().hasValue() ) {
            suppliedParameterTypes.add( typeFactory.getType( String.class ).getTypeMirror() );
        }
        return suppliedParameterTypes;
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
