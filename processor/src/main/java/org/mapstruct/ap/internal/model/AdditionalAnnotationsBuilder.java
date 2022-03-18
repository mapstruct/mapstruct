/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.gem.AnnotateWithGem;
import org.mapstruct.ap.internal.gem.AnnotateWithsGem;
import org.mapstruct.ap.internal.gem.ParameterGem;
import org.mapstruct.ap.internal.gem.TargetGem;
import org.mapstruct.ap.internal.model.annotation.BooleanProperty;
import org.mapstruct.ap.internal.model.annotation.ByteProperty;
import org.mapstruct.ap.internal.model.annotation.CharacterProperty;
import org.mapstruct.ap.internal.model.annotation.ClassProperty;
import org.mapstruct.ap.internal.model.annotation.DoubleProperty;
import org.mapstruct.ap.internal.model.annotation.FloatProperty;
import org.mapstruct.ap.internal.model.annotation.IntegerProperty;
import org.mapstruct.ap.internal.model.annotation.LongProperty;
import org.mapstruct.ap.internal.model.annotation.Property;
import org.mapstruct.ap.internal.model.annotation.ShortProperty;
import org.mapstruct.ap.internal.model.annotation.StringProperty;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.ElementUtils;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.RepeatableAnnotations;
import org.mapstruct.ap.internal.util.TypeUtils;

/**
 * @author Ben Zegveld
 * @since 1.5
 */
public class AdditionalAnnotationsBuilder
    extends RepeatableAnnotations<AnnotateWithGem, AnnotateWithsGem, Annotation> {
    private static final String ANNOTATE_WITH_FQN = "org.mapstruct.AnnotateWith";
    private static final String ANNOTATE_WITHS_FQN = "org.mapstruct.AnnotateWiths";

    private TypeFactory typeFactory;
    private FormattingMessager messager;
    private TypeUtils typeUtils;

    public AdditionalAnnotationsBuilder(ElementUtils elementUtils, TypeUtils typeUtils, TypeFactory typeFactory,
                                        FormattingMessager messager) {
        super( elementUtils, ANNOTATE_WITH_FQN, ANNOTATE_WITHS_FQN );
        this.typeUtils = typeUtils;
        this.typeFactory = typeFactory;
        this.messager = messager;
    }

    @Override
    protected AnnotateWithGem singularInstanceOn(Element element) {
        return AnnotateWithGem.instanceOn( element );
    }

    @Override
    protected AnnotateWithsGem multipleInstanceOn(Element element) {
        return AnnotateWithsGem.instanceOn( element );
    }

    @Override
    protected void addInstance(AnnotateWithGem gem, Element source, Set<Annotation> mappings) {
        buildAnnotation( gem, source ).ifPresent( mappings::add );
    }

    @Override
    protected void addInstances(AnnotateWithsGem gem, Element source, Set<Annotation> mappings) {
        for ( AnnotateWithGem annotateWithGem : gem.value().get() ) {
            buildAnnotation( annotateWithGem, source ).ifPresent( mappings::add );
        }
    }

    private Optional<Annotation> buildAnnotation(AnnotateWithGem annotationGem, Element element) {
        List<ParameterGem> parameters = annotationGem.parameters().get();
        Type annotationType = typeFactory.getType( annotationGem.value().getValue() );
        if ( isValid( annotationType, parameters, element ) ) {
            return Optional.of( new Annotation( annotationType, convertToProperties( parameters ) ) );
        }
        return Optional.empty();
    }

    private List<Property> convertToProperties(List<ParameterGem> parameters) {
        return parameters.stream().map( gem -> convertToProperty( gem, typeFactory ) ).collect( Collectors.toList() );
    }

    private enum ConvertToProperty {
        BOOLEAN(
            (parameter, typeFactory) -> new BooleanProperty( parameter.key().get(), parameter.booleans().get() ),
            parameter -> parameter.booleans().hasValue() ),
        BYTE(
            (parameter, typeFactory) -> new ByteProperty( parameter.key().get(), parameter.bytes().get() ),
            parameter -> parameter.bytes().hasValue() ),
        CHARACTER(
            (parameter, typeFactory) -> new CharacterProperty( parameter.key().get(), parameter.chars().get() ),
            parameter -> parameter.chars().hasValue() ),
        CLASSES(
            (parameter, typeFactory) -> {
                List<Type> typeList =
                    parameter.classes().get().stream().map( typeFactory::getType ).collect( Collectors.toList() );
                return new ClassProperty( parameter.key().get(), typeList );
            },
            parameter -> parameter.classes().hasValue() ),
        DOUBLE(
            (parameter, typeFactory) -> new DoubleProperty( parameter.key().get(), parameter.doubles().get() ),
            parameter -> parameter.doubles().hasValue() ),
        FLOAT(
            (parameter, typeFactory) -> new FloatProperty( parameter.key().get(), parameter.floats().get() ),
            parameter -> parameter.floats().hasValue() ),
        INT(
            (parameter, typeFactory) -> new IntegerProperty( parameter.key().get(), parameter.ints().get() ),
            parameter -> parameter.ints().hasValue() ),
        LONG(
            (parameter, typeFactory) -> new LongProperty( parameter.key().get(), parameter.longs().get() ),
            parameter -> parameter.longs().hasValue() ),
        SHORT(
            (parameter, typeFactory) -> new ShortProperty( parameter.key().get(), parameter.shorts().get() ),
            parameter -> parameter.shorts().hasValue() ),
        STRING(
            (parameter, typeFactory) -> new StringProperty( parameter.key().get(), parameter.strings().get() ),
            parameter -> parameter.strings().hasValue() );

        private BiFunction<ParameterGem, TypeFactory, Property> factory;
        private Predicate<ParameterGem> usabilityChecker;

        ConvertToProperty(BiFunction<ParameterGem, TypeFactory, Property> factory,
                              Predicate<ParameterGem> usabilityChecker) {
            this.factory = factory;
            this.usabilityChecker = usabilityChecker;
        }

        Property toProperty(ParameterGem parameter, TypeFactory typeFactory) {
            return factory.apply( parameter, typeFactory );
        }

        boolean isUsable(ParameterGem parameter) {
            return usabilityChecker.test( parameter );
        }
    }

    private Property convertToProperty(ParameterGem parameter, TypeFactory typeFactory) {
        for ( ConvertToProperty convertToJava : ConvertToProperty.values() ) {
            if ( convertToJava.isUsable( parameter ) ) {
                return convertToJava.toProperty( parameter, typeFactory );
            }
        }
        return null;
    }

    private boolean isValid(Type annotationType, List<ParameterGem> parameters, Element element) {
        boolean isValid = true;
        if ( !annotationIsAllowed( annotationType, element ) ) {
            isValid = false;
        }
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

    private boolean annotationIsAllowed(Type annotationType, Element element) {
        TargetGem target = TargetGem.instanceOn( annotationType.getTypeElement() );
        List<String> annotationTargets = target.value().get();
        boolean isValid = true;
        if ( isTypeTarget( element ) && !annotationTargets.contains( "TYPE" ) ) {
            isValid = false;
            messager.printMessage(
                        element,
                        Message.ANNOTATE_WITH_NOT_ALLOWED_ON_CLASS,
                        annotationType );
        }
        if ( isMethodTarget( element ) && !annotationTargets.contains( "METHOD" ) ) {
            isValid = false;
            messager.printMessage(
                        element,
                        Message.ANNOTATE_WITH_NOT_ALLOWED_ON_METHODS,
                        annotationType );
        }
        return isValid;
    }

    private boolean isTypeTarget(Element element) {
        return element.getKind().isInterface() || element.getKind().isClass();
    }

    private boolean isMethodTarget(Element element) {
        return element.getKind() == ElementKind.METHOD;
    }

    private boolean allParametersAreKnownInAnnotation(Type annotationType, List<ParameterGem> parameters,
                                                      Element element) {
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
                                                    Element element) {
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
                                                  Element element) {
        Map<String, ExecutableElement> annotationParameters =
            annotationType
                          .findAllAnnotationParameters()
                          .collect( Collectors.toMap( ee -> ee.getSimpleName().toString(), Function.identity() ) );
        boolean isValid = true;
        for ( ParameterGem parameter : parameters ) {
            TypeMirror annotationParameterType = getAnnotationParameterType( annotationParameters, parameter );
            TypeMirror annotationParameterTypeSingular = getNonArrayTypeMirror( annotationParameterType );
            Map<TypeMirror, Integer> parameterTypes = getParameterTypes( parameter );
            Set<ParameterGem> reportedSizeError = new HashSet<>();
            for ( TypeMirror parameterType : parameterTypes.keySet() ) {
                if ( typesArePresent( annotationParameterTypeSingular, parameterType ) ) {
                    if ( !sameTypeOrAssignableClass( annotationParameterTypeSingular, parameterType ) ) {
                        isValid = false;
                        messager.printMessage(
                                              element,
                                              parameter.mirror(),
                                              Message.ANNOTATE_WITH_WRONG_PARAMETER,
                                              parameter.key().get(),
                                              parameterType,
                                              annotationParameterType,
                                              annotationType );
                    }
                    else if ( annotationParameterType.getKind() != TypeKind.ARRAY
                        && parameterTypes.get( parameterType ) > 1
                        && !reportedSizeError.contains( parameter ) ) {
                        isValid = false;
                        messager.printMessage(
                                              element,
                                              parameter.mirror(),
                                              Message.ANNOTATE_WITH_PARAMETER_ARRAY_NOT_EXPECTED,
                                              parameter.key().get(),
                                              annotationType );
                        reportedSizeError.add( parameter );
                    }
                }
            }
        }
        return isValid;
    }

    private TypeMirror getNonArrayTypeMirror(TypeMirror annotationParameterType) {
        return annotationParameterType != null && annotationParameterType.getKind() == TypeKind.ARRAY
                    ? ( (ArrayType) annotationParameterType ).getComponentType()
                    : annotationParameterType;
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

    private Map<TypeMirror, Integer> getParameterTypes(ParameterGem parameter) {
        Map<TypeMirror, Integer> suppliedParameterTypes = new HashMap<>();
        if ( parameter.booleans().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( boolean.class ).getTypeMirror(),
                                      parameter.booleans().get().size() );
        }
        if ( parameter.bytes().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( byte.class ).getTypeMirror(),
                                      parameter.bytes().get().size() );
        }
        if ( parameter.chars().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( char.class ).getTypeMirror(),
                                      parameter.chars().get().size() );
        }
        if ( parameter.classes().hasValue() ) {
            for ( TypeMirror mirror : parameter.classes().get() ) {
                suppliedParameterTypes.put( mirror, parameter.classes().get().size() );
            }
        }
        if ( parameter.doubles().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( double.class ).getTypeMirror(),
                                      parameter.doubles().get().size() );
        }
        if ( parameter.floats().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( float.class ).getTypeMirror(),
                                      parameter.floats().get().size() );
        }
        if ( parameter.ints().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( int.class ).getTypeMirror(),
                                      parameter.ints().get().size() );
        }
        if ( parameter.longs().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( long.class ).getTypeMirror(),
                                      parameter.longs().get().size() );
        }
        if ( parameter.shorts().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( short.class ).getTypeMirror(),
                                      parameter.shorts().get().size() );
        }
        if ( parameter.strings().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( String.class ).getTypeMirror(),
                                      parameter.strings().get().size() );
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
