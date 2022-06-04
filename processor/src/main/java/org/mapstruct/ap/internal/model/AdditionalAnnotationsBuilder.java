/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.gem.AnnotateWithGem;
import org.mapstruct.ap.internal.gem.AnnotateWithsGem;
import org.mapstruct.ap.internal.gem.ElementGem;
import org.mapstruct.ap.internal.model.annotation.AnnotationElement;
import org.mapstruct.ap.internal.model.annotation.AnnotationElement.AnnotationElementType;
import org.mapstruct.ap.internal.model.annotation.EnumAnnotationElementHolder;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.ElementUtils;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.RepeatableAnnotations;
import org.mapstruct.ap.spi.TypeHierarchyErroneousException;
import org.mapstruct.tools.gem.GemValue;

import static java.util.Collections.emptySet;
import static javax.lang.model.util.ElementFilter.methodsIn;

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

    public AdditionalAnnotationsBuilder(ElementUtils elementUtils, TypeFactory typeFactory,
                                        FormattingMessager messager) {
        super( elementUtils, ANNOTATE_WITH_FQN, ANNOTATE_WITHS_FQN );
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
        Type annotationType = typeFactory.getType( getTypeMirror( annotationGem.value() ) );
        List<ElementGem> eleGems = annotationGem.elements().get();
        if ( isValid( annotationType, eleGems, element ) ) {
            return Optional.of( new Annotation( annotationType, convertToProperties( eleGems ) ) );
        }
        return Optional.empty();
    }

    private List<AnnotationElement> convertToProperties(List<ElementGem> eleGems) {
        return eleGems.stream().map( gem -> convertToProperty( gem, typeFactory ) ).collect( Collectors.toList() );
    }

    private enum ConvertToProperty {
        BOOLEAN( (eleGem, typeFactory) -> new AnnotationElement(
                AnnotationElementType.BOOLEAN,
                eleGem.name().get(),
                eleGem.booleans().get(),
                emptySet() ),
            eleGem -> eleGem.booleans().hasValue() ),
        BYTE( (eleGem, typeFactory) -> new AnnotationElement(
                AnnotationElementType.BYTE,
                eleGem.name().get(),
                eleGem.bytes().get(),
                emptySet() ),
            eleGem -> eleGem.bytes().hasValue() ),
        CHARACTER( (eleGem, typeFactory) -> new AnnotationElement(
                AnnotationElementType.CHARACTER,
                eleGem.name().get(),
                eleGem.chars().get(),
                emptySet() ),
            eleGem -> eleGem.chars().hasValue() ),
        CLASSES( (eleGem, typeFactory) -> {
                List<Type> typeList =
                    eleGem.classes().get().stream().map( typeFactory::getType ).collect( Collectors.toList() );
                return new AnnotationElement(
                    AnnotationElementType.CLASS,
                    eleGem.name().get(),
                    typeList,
                    new HashSet<>( typeList ) );
            },
            eleGem -> eleGem.classes().hasValue() ),
        DOUBLE( (eleGem, typeFactory) -> new AnnotationElement(
                AnnotationElementType.DOUBLE,
                eleGem.name().get(),
                eleGem.doubles().get(),
                emptySet() ),
            eleGem -> eleGem.doubles().hasValue() ),
        ENUM( (eleGem, typeFactory) -> {
                List<EnumAnnotationElementHolder> values = new ArrayList<>();
                Set<Type> importTypes = new HashSet<>();
                for ( String enumName : eleGem.enums().get() ) {
                    Type type = typeFactory.getType( eleGem.enumClass().get() );
                    importTypes.add( type );
                    values.add( new EnumAnnotationElementHolder( type, enumName ) );
                }
                return new AnnotationElement( AnnotationElementType.ENUM, eleGem.name().get(), values, importTypes );
            },
            eleGem -> eleGem.enums().hasValue() ),
        FLOAT( (eleGem, typeFactory) -> new AnnotationElement(
                AnnotationElementType.FLOAT,
                eleGem.name().get(),
                eleGem.floats().get(),
                emptySet() ),
            eleGem -> eleGem.floats().hasValue() ),
        INT( (eleGem, typeFactory) -> new AnnotationElement(
                AnnotationElementType.INTEGER,
                eleGem.name().get(),
                eleGem.ints().get(),
                emptySet() ),
            eleGem -> eleGem.ints().hasValue() ),
        LONG( (eleGem, typeFactory) -> new AnnotationElement(
                AnnotationElementType.LONG,
                eleGem.name().get(),
                eleGem.longs().get(),
                emptySet() ),
            eleGem -> eleGem.longs().hasValue() ),
        SHORT( (eleGem, typeFactory) -> new AnnotationElement(
                AnnotationElementType.SHORT,
                eleGem.name().get(),
                eleGem.shorts().get(),
                emptySet() ),
            eleGem -> eleGem.shorts().hasValue() ),
        STRING( (eleGem, typeFactory) -> new AnnotationElement(
                AnnotationElementType.STRING,
                eleGem.name().get(),
                eleGem.strings().get(),
                emptySet() ),
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

        List<ExecutableElement> annotationParameters = methodsIn( annotationType.getTypeElement()
            .getEnclosedElements() );
        if ( !allRequiredParametersArePresent( annotationType, annotationParameters, eleGems, element ) ) {
            isValid = false;
        }
        if ( !allParametersAreKnownInAnnotation( annotationType, annotationParameters, eleGems, element ) ) {
            isValid = false;
        }
        if ( !allParametersAreOfCorrectType( annotationType, annotationParameters, eleGems, element ) ) {
            isValid = false;
        }
        if ( !enumConstructionIsCorrectlyUsed( eleGems, element ) ) {
            isValid = false;
        }
        return isValid;
    }

    private boolean enumConstructionIsCorrectlyUsed(List<ElementGem> eleGems, Element element) {
        boolean isValid = true;
        for ( ElementGem elementGem : eleGems ) {
            if ( elementGem.enums().hasValue() ) {
                if ( elementGem.enumClass().getValue() == null ) {
                    isValid = false;
                    messager
                            .printMessage(
                                element,
                                elementGem.mirror(),
                                elementGem.enums().getAnnotationValue(),
                                Message.ANNOTATE_WITH_ENUM_CLASS_NOT_DEFINED );
                }
                else {
                    Type type = typeFactory.getType( getTypeMirror( elementGem.enumClass() ) );
                    if ( type.isEnumType() ) {
                        List<String> enumConstants = type.getEnumConstants();
                        for ( String enumName : elementGem.enums().get() ) {
                            if ( !enumConstants.contains( enumName ) ) {
                                isValid = false;
                                messager
                                        .printMessage(
                                            element,
                                            elementGem.mirror(),
                                            elementGem.enums().getAnnotationValue(),
                                            Message.ANNOTATE_WITH_ENUM_VALUE_DOES_NOT_EXIST,
                                            type.describe(),
                                            enumName );
                            }
                        }
                    }
                }
            }
        }
        return isValid;
    }

    private boolean annotationIsAllowed(Type annotationType, Element element) {
        Target target = annotationType.getTypeElement().getAnnotation( Target.class );
        if ( target == null ) {
            return true;
        }

        Set<ElementType> annotationTargets = Stream.of( target.value() )
            // The eclipse compiler returns null for some values
            // Therefore, we filter out null values
            .filter( Objects::nonNull )
            .collect( Collectors.toCollection( () -> EnumSet.noneOf( ElementType.class ) ) );

        boolean isValid = true;
        if ( isTypeTarget( element ) && !annotationTargets.contains( ElementType.TYPE ) ) {
            isValid = false;
            messager.printMessage(
                        element,
                        Message.ANNOTATE_WITH_NOT_ALLOWED_ON_CLASS,
                        annotationType.describe()
            );
        }
        if ( isMethodTarget( element ) && !annotationTargets.contains( ElementType.METHOD ) ) {
            isValid = false;
            messager.printMessage(
                        element,
                        Message.ANNOTATE_WITH_NOT_ALLOWED_ON_METHODS,
                        annotationType.describe()
            );
        }
        return isValid;
    }

    private boolean isTypeTarget(Element element) {
        return element.getKind().isInterface() || element.getKind().isClass();
    }

    private boolean isMethodTarget(Element element) {
        return element.getKind() == ElementKind.METHOD;
    }

    private boolean allParametersAreKnownInAnnotation(Type annotationType, List<ExecutableElement> annotationParameters,
                                                      List<ElementGem> eleGems, Element element) {
        List<String> allowedAnnotationParameters = annotationParameters.stream()
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
                            eleGem.mirror(),
                            eleGem.name().getAnnotationValue(),
                            Message.ANNOTATE_WITH_UNKNOWN_PARAMETER,
                            eleGem.name().get(),
                            annotationType.describe()
                        );
            }
        }
        return isValid;
    }

    private boolean allRequiredParametersArePresent(Type annotationType, List<ExecutableElement> annotationParameters,
                                                    List<ElementGem> elements, Element element) {

        boolean valid = true;
        for ( ExecutableElement annotationParameter : annotationParameters ) {
            if ( annotationParameter.getDefaultValue() == null ) {
                // Mandatory parameter, must be present in the elements
                String parameterName = annotationParameter.getSimpleName().toString();
                boolean elementGemDefined = false;
                for ( ElementGem elementGem : elements ) {
                    if ( elementGem.isValid() && elementGem.name().get().equals( parameterName ) ) {
                        elementGemDefined = true;
                        break;
                    }
                }

                if ( !elementGemDefined ) {
                    valid = false;
                    messager
                        .printMessage(
                            element,
                            Message.ANNOTATE_WITH_MISSING_REQUIRED_PARAMETER,
                            parameterName,
                            annotationType.describe()
                        );
                }
            }
        }


        return valid;
    }

    private boolean allParametersAreOfCorrectType(Type annotationType, List<ExecutableElement> annotationParameters,
                                                  List<ElementGem> elements,
                                                  Element element) {
        Map<String, ExecutableElement> annotationParametersByName =
            annotationParameters.stream()
                          .collect( Collectors.toMap( ee -> ee.getSimpleName().toString(), Function.identity() ) );
        boolean isValid = true;
        for ( ElementGem eleGem : elements ) {
            Type annotationParameterType = getAnnotationParameterType( annotationParametersByName, eleGem );
            Type annotationParameterTypeSingular = getNonArrayType( annotationParameterType );
            if ( annotationParameterTypeSingular == null ) {
                continue;
            }
            Map<Type, Integer> elementTypes = getParameterTypes( eleGem );
            Set<ElementGem> reportedSizeError = new HashSet<>();
            for ( Type eleGemType : elementTypes.keySet() ) {
                if ( !sameTypeOrAssignableClass( annotationParameterTypeSingular, eleGemType ) ) {
                    isValid = false;
                    messager.printMessage(
                        element,
                        eleGem.mirror(),
                        eleGem.name().getAnnotationValue(),
                        Message.ANNOTATE_WITH_WRONG_PARAMETER,
                        eleGem.name().get(),
                        eleGemType.describe(),
                        annotationParameterType.describe(),
                        annotationType.describe()
                    );
                }
                else if ( !annotationParameterType.isArrayType()
                    && elementTypes.get( eleGemType ) > 1
                    && !reportedSizeError.contains( eleGem ) ) {
                    isValid = false;
                    messager.printMessage(
                        element,
                        eleGem.mirror(),
                        Message.ANNOTATE_WITH_PARAMETER_ARRAY_NOT_EXPECTED,
                        eleGem.name().get(),
                        annotationType.describe()
                    );
                    reportedSizeError.add( eleGem );
                }
            }
        }
        return isValid;
    }

    private Type getNonArrayType(Type annotationParameterType) {
        if ( annotationParameterType == null ) {
            return null;
        }
        if ( annotationParameterType.isArrayType() ) {
            return annotationParameterType.getComponentType();
        }

        return annotationParameterType;
    }

    private boolean sameTypeOrAssignableClass(Type annotationParameterType, Type eleGemType) {
        return annotationParameterType.equals( eleGemType )
            || eleGemType.isAssignableTo( getTypeBound( annotationParameterType ) );
    }

    private Type getTypeBound(Type annotationParameterType) {
        List<Type> typeParameters = annotationParameterType.getTypeParameters();
        if ( typeParameters.size() != 1 ) {
            return annotationParameterType;
        }
        return typeParameters.get( 0 ).getTypeBound();
    }

    private Map<Type, Integer> getParameterTypes(ElementGem eleGem) {
        Map<Type, Integer> suppliedParameterTypes = new HashMap<>();
        if ( eleGem.booleans().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( boolean.class ),
                                      eleGem.booleans().get().size() );
        }
        if ( eleGem.bytes().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( byte.class ),
                                      eleGem.bytes().get().size() );
        }
        if ( eleGem.chars().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( char.class ),
                                      eleGem.chars().get().size() );
        }
        if ( eleGem.classes().hasValue() ) {
            for ( TypeMirror mirror : eleGem.classes().get() ) {
                suppliedParameterTypes.put(
                    typeFactory.getType( typeMirrorFromAnnotation( mirror ) ),
                    eleGem.classes().get().size()
                );
            }
        }
        if ( eleGem.doubles().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( double.class ),
                                      eleGem.doubles().get().size() );
        }
        if ( eleGem.floats().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( float.class ),
                                      eleGem.floats().get().size() );
        }
        if ( eleGem.ints().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( int.class ),
                                      eleGem.ints().get().size() );
        }
        if ( eleGem.longs().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( long.class ),
                                      eleGem.longs().get().size() );
        }
        if ( eleGem.shorts().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( short.class ),
                                      eleGem.shorts().get().size() );
        }
        if ( eleGem.strings().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( String.class ),
                                      eleGem.strings().get().size() );
        }
        if ( eleGem.enums().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( getTypeMirror( eleGem.enumClass() ) ),
                                      eleGem.enums().get().size() );
        }
        return suppliedParameterTypes;
    }

    private Type getAnnotationParameterType(Map<String, ExecutableElement> annotationParameters,
                                                  ElementGem element) {
        if ( element.name().hasValue() && annotationParameters.containsKey( element.name().get() ) ) {
            return typeFactory.getType( annotationParameters.get( element.name().get() ).getReturnType() );
        }
        else {
            return null;
        }
    }

    private TypeMirror getTypeMirror(GemValue<TypeMirror> gemValue) {
        return typeMirrorFromAnnotation( gemValue.getValue() );
    }

    private TypeMirror typeMirrorFromAnnotation(TypeMirror typeMirror) {
        if ( typeMirror == null ) {
            // When a class used in an annotation is created by another annotation processor
            // then javac will not return correct TypeMirror with TypeKind#ERROR, but rather a string "<error>"
            // the gem tools would return a null TypeMirror in that case.
            // Therefore, throw TypeHierarchyErroneousException so we can postpone the generation of the mapper
            throw new TypeHierarchyErroneousException( typeMirror );
        }

        return typeMirror;
    }

}
