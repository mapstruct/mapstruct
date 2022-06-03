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
import org.mapstruct.ap.internal.gem.ElementGem;
import org.mapstruct.ap.internal.gem.EnumElementGem;
import org.mapstruct.ap.internal.gem.TargetGem;
import org.mapstruct.ap.internal.model.annotation.AnnotationElement;
import org.mapstruct.ap.internal.model.annotation.BooleanAnnotationElement;
import org.mapstruct.ap.internal.model.annotation.ByteAnnotationElement;
import org.mapstruct.ap.internal.model.annotation.CharacterAnnotationElement;
import org.mapstruct.ap.internal.model.annotation.ClassAnnotationElement;
import org.mapstruct.ap.internal.model.annotation.DoubleAnnotationElement;
import org.mapstruct.ap.internal.model.annotation.EnumAnnotationElement;
import org.mapstruct.ap.internal.model.annotation.EnumAnnotationElementHolder;
import org.mapstruct.ap.internal.model.annotation.FloatAnnotationElement;
import org.mapstruct.ap.internal.model.annotation.IntegerAnnotationElement;
import org.mapstruct.ap.internal.model.annotation.LongAnnotationElement;
import org.mapstruct.ap.internal.model.annotation.ShortAnnotationElement;
import org.mapstruct.ap.internal.model.annotation.StringAnnotationElement;
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
        List<ElementGem> eleGems = annotationGem.elements().get();
        Type annotationType = typeFactory.getType( annotationGem.value().getValue() );
        if ( isValid( annotationType, eleGems, element ) ) {
            return Optional.of( new Annotation( annotationType, convertToProperties( eleGems ) ) );
        }
        return Optional.empty();
    }

    private List<AnnotationElement> convertToProperties(List<ElementGem> eleGems) {
        return eleGems.stream().map( gem -> convertToProperty( gem, typeFactory ) ).collect( Collectors.toList() );
    }

    private enum ConvertToProperty {
        BOOLEAN(
            (eleGem,
             typeFactory) -> new BooleanAnnotationElement( eleGem.name().get(), eleGem.booleans().get() ),
            eleGem -> eleGem.booleans().hasValue() ),
        BYTE(
            (eleGem, typeFactory) -> new ByteAnnotationElement( eleGem.name().get(), eleGem.bytes().get() ),
            eleGem -> eleGem.bytes().hasValue() ),
        CHARACTER(
            (eleGem,
             typeFactory) -> new CharacterAnnotationElement( eleGem.name().get(), eleGem.chars().get() ),
            eleGem -> eleGem.chars().hasValue() ),
        CLASSES(
            (eleGem, typeFactory) -> {
                List<Type> typeList =
                    eleGem.classes().get().stream().map( typeFactory::getType ).collect( Collectors.toList() );
                return new ClassAnnotationElement( eleGem.name().get(), typeList );
            },
            eleGem -> eleGem.classes().hasValue() ),
        DOUBLE(
            (eleGem,
             typeFactory) -> new DoubleAnnotationElement( eleGem.name().get(), eleGem.doubles().get() ),
            eleGem -> eleGem.doubles().hasValue() ),
        ENUM(
            (eleGem, typeFactory) -> {
                List<EnumAnnotationElementHolder> values = eleGem.enums().get().stream()
                    .map( enumGem -> {
                        Type type = typeFactory.getType( enumGem.enumClass().get() );
                        return new EnumAnnotationElementHolder( type, enumGem.name().get() );
                    } )
                    .collect( Collectors.toList() );
                return new EnumAnnotationElement( eleGem.name().get(), values );
            },
            eleGem -> eleGem.enums().hasValue() ),
        FLOAT(
            (eleGem, typeFactory) -> new FloatAnnotationElement( eleGem.name().get(), eleGem.floats().get() ),
            eleGem -> eleGem.floats().hasValue() ),
        INT(
            (eleGem, typeFactory) -> new IntegerAnnotationElement( eleGem.name().get(), eleGem.ints().get() ),
            eleGem -> eleGem.ints().hasValue() ),
        LONG(
            (eleGem, typeFactory) -> new LongAnnotationElement( eleGem.name().get(), eleGem.longs().get() ),
            eleGem -> eleGem.longs().hasValue() ),
        SHORT(
            (eleGem, typeFactory) -> new ShortAnnotationElement( eleGem.name().get(), eleGem.shorts().get() ),
            eleGem -> eleGem.shorts().hasValue() ),
        STRING(
            (eleGem,
             typeFactory) -> new StringAnnotationElement( eleGem.name().get(), eleGem.strings().get() ),
            eleGem -> eleGem.strings().hasValue() );

        private BiFunction<ElementGem, TypeFactory, AnnotationElement> factory;
        private Predicate<ElementGem> usabilityChecker;

        ConvertToProperty(BiFunction<ElementGem, TypeFactory, AnnotationElement> factory,
                          Predicate<ElementGem> usabilityChecker) {
            this.factory = factory;
            this.usabilityChecker = usabilityChecker;
        }

        AnnotationElement toProperty(ElementGem eleGem, TypeFactory typeFactory) {
            return factory.apply( eleGem, typeFactory );
        }

        boolean isUsable(ElementGem eleGem) {
            return usabilityChecker.test( eleGem );
        }
    }

    private AnnotationElement convertToProperty(ElementGem eleGem, TypeFactory typeFactory) {
        for ( ConvertToProperty convertToJava : ConvertToProperty.values() ) {
            if ( convertToJava.isUsable( eleGem ) ) {
                return convertToJava.toProperty( eleGem, typeFactory );
            }
        }
        return null;
    }

    private boolean isValid(Type annotationType, List<ElementGem> eleGems, Element element) {
        boolean isValid = true;
        if ( !annotationIsAllowed( annotationType, element ) ) {
            isValid = false;
        }
        if ( !allRequiredParametersArePresent( annotationType, eleGems, element ) ) {
            isValid = false;
        }
        if ( !allParametersAreKnownInAnnotation( annotationType, eleGems, element ) ) {
            isValid = false;
        }
        if ( !allParametersAreOfCorrectType( annotationType, eleGems, element ) ) {
            isValid = false;
        }
        if ( !allEnumParametersExist( annotationType, eleGems, element ) ) {
            isValid = false;
        }
        return isValid;
    }

    private boolean allEnumParametersExist(Type annotationType, List<ElementGem> eleGems, Element element) {
        boolean isValid = true;
        for ( ElementGem elementGem : eleGems ) {
            if ( elementGem.enums().hasValue() ) {
                for ( EnumElementGem enumElementGem : elementGem.enums().get() ) {
                    TypeMirror typeMirror = enumElementGem.enumClass().get();
                    if ( typeUtils.asElement( typeMirror ).getKind() == ElementKind.ENUM ) {
                        if ( typeUtils
                                 .asElement( typeMirror )
                                 .getEnclosedElements()
                                 .stream()
                                 .filter( ele -> ele.getKind() == ElementKind.ENUM_CONSTANT )
                                 .map( Element::getSimpleName )
                                      .noneMatch( val -> val.contentEquals( enumElementGem.name().get() ) ) ) {
                            isValid = false;
                            messager
                                    .printMessage(
                                        element,
                                        Message.ANNOTATE_WITH_ENUM_VALUE_DOES_NOT_EXIST,
                                        typeMirror,
                                        enumElementGem.name().get() );
                        }
                    }
                }
            }
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

    private boolean allParametersAreKnownInAnnotation(Type annotationType, List<ElementGem> eleGems,
                                                      Element element) {
        List<String> allowedAnnotationParameters = annotationType
                                                                 .findAllAnnotationParameters()
                                                                 .map( ee -> ee.getSimpleName().toString() )
                                                                 .collect( Collectors.toList() );
        boolean isValid = true;
        for ( ElementGem eleGem : eleGems ) {
            if ( eleGem.name().isValid()
                && !allowedAnnotationParameters.contains( eleGem.name().get() ) ) {
                isValid = false;
                messager
                        .printMessage(
                            element,
                            Message.ANNOTATE_WITH_UNKNOWN_PARAMETER,
                            eleGem.name().get(),
                            annotationType );
            }
        }
        return isValid;
    }

    private boolean allRequiredParametersArePresent(Type annotationType, List<ElementGem> eleGems,
                                                    Element element) {
        List<ExecutableElement> undefinedParameters =
            annotationType.findAllAnnotationParameters()
                        .filter( ee -> ee.getDefaultValue() == null )
                        .filter( ee -> eleGems.stream()
                                                   .noneMatch(
                                                       eleGem -> eleGem.name().isValid()
                                                           && eleGem
                                                               .name()
                                                               .get()
                                                               .equals( ee.getSimpleName().toString() ) ) )
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

    private boolean allParametersAreOfCorrectType(Type annotationType, List<ElementGem> elements,
                                                  Element element) {
        Map<String, ExecutableElement> annotationParameters =
            annotationType
                          .findAllAnnotationParameters()
                          .collect( Collectors.toMap( ee -> ee.getSimpleName().toString(), Function.identity() ) );
        boolean isValid = true;
        for ( ElementGem eleGem : elements ) {
            TypeMirror annotationParameterType = getAnnotationParameterType( annotationParameters, eleGem );
            TypeMirror annotationParameterTypeSingular = getNonArrayTypeMirror( annotationParameterType );
            Map<TypeMirror, Integer> elementTypes = getParameterTypes( eleGem );
            Set<ElementGem> reportedSizeError = new HashSet<>();
            for ( TypeMirror eleGemType : elementTypes.keySet() ) {
                if ( typesArePresent( annotationParameterTypeSingular, eleGemType ) ) {
                    if ( !sameTypeOrAssignableClass( annotationParameterTypeSingular, eleGemType ) ) {
                        isValid = false;
                        messager.printMessage(
                            element,
                            eleGem.mirror(),
                                              Message.ANNOTATE_WITH_WRONG_PARAMETER,
                            eleGem.name().get(),
                                              eleGemType,
                                              annotationParameterType,
                                              annotationType );
                    }
                    else if ( annotationParameterType.getKind() != TypeKind.ARRAY
                        && elementTypes.get( eleGemType ) > 1
                        && !reportedSizeError.contains( eleGem ) ) {
                        isValid = false;
                        messager.printMessage(
                            element,
                            eleGem.mirror(),
                                              Message.ANNOTATE_WITH_PARAMETER_ARRAY_NOT_EXPECTED,
                            eleGem.name().get(),
                                              annotationType );
                        reportedSizeError.add( eleGem );
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

    private boolean sameTypeOrAssignableClass(TypeMirror annotationParameterType, TypeMirror eleGemType) {
        return typeUtils.isSameType( annotationParameterType, eleGemType )
            || typeUtils.isAssignable( eleGemType, getTypeBound( annotationParameterType ) );
    }

    private boolean typesArePresent(TypeMirror annotationParameterType, TypeMirror eleGemType) {
        return eleGemType != null && annotationParameterType != null;
    }

    private TypeMirror getTypeBound(TypeMirror annotationParameterType) {
        List<Type> typeParameters = typeFactory.getType( annotationParameterType ).getTypeParameters();
        if ( typeParameters.size() != 1 ) {
            return annotationParameterType;
        }
        return typeFactory.getTypeBound( typeParameters.get( 0 ).getTypeMirror() );
    }

    private Map<TypeMirror, Integer> getParameterTypes(ElementGem eleGem) {
        Map<TypeMirror, Integer> suppliedParameterTypes = new HashMap<>();
        if ( eleGem.booleans().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( boolean.class ).getTypeMirror(),
                                      eleGem.booleans().get().size() );
        }
        if ( eleGem.bytes().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( byte.class ).getTypeMirror(),
                                      eleGem.bytes().get().size() );
        }
        if ( eleGem.chars().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( char.class ).getTypeMirror(),
                                      eleGem.chars().get().size() );
        }
        if ( eleGem.classes().hasValue() ) {
            for ( TypeMirror mirror : eleGem.classes().get() ) {
                suppliedParameterTypes.put( mirror, eleGem.classes().get().size() );
            }
        }
        if ( eleGem.doubles().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( double.class ).getTypeMirror(),
                                      eleGem.doubles().get().size() );
        }
        if ( eleGem.floats().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( float.class ).getTypeMirror(),
                                      eleGem.floats().get().size() );
        }
        if ( eleGem.ints().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( int.class ).getTypeMirror(),
                                      eleGem.ints().get().size() );
        }
        if ( eleGem.longs().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( long.class ).getTypeMirror(),
                                      eleGem.longs().get().size() );
        }
        if ( eleGem.shorts().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( short.class ).getTypeMirror(),
                                      eleGem.shorts().get().size() );
        }
        if ( eleGem.strings().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( String.class ).getTypeMirror(),
                                      eleGem.strings().get().size() );
        }
        if ( eleGem.enums().hasValue() ) {
            for ( EnumElementGem enumElementGem : eleGem.enums().get() ) {
                suppliedParameterTypes.put( enumElementGem.enumClass().get(), eleGem.enums().get().size() );
            }
        }
        return suppliedParameterTypes;
    }

    private TypeMirror getAnnotationParameterType(Map<String, ExecutableElement> annotationParameters,
                                                  ElementGem element) {
        if ( element.name().hasValue() && annotationParameters.containsKey( element.name().get() ) ) {
            return annotationParameters.get( element.name().get() ).getReturnType();
        }
        else {
            return null;
        }
    }
}
