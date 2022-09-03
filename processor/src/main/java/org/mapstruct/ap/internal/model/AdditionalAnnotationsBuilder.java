/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.gem.AnnotateWithGem;
import org.mapstruct.ap.internal.gem.AnnotateWithsGem;
import org.mapstruct.ap.internal.gem.DeprecatedGem;
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
import org.mapstruct.ap.internal.util.Strings;
import org.mapstruct.ap.spi.TypeHierarchyErroneousException;
import org.mapstruct.tools.gem.GemValue;

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
        buildAnnotation( gem, source ).ifPresent( t -> addAndValidateMapping( mappings, source, gem, t ) );
    }

    @Override
    protected void addInstances(AnnotateWithsGem gem, Element source, Set<Annotation> mappings) {
        for ( AnnotateWithGem annotateWithGem : gem.value().get() ) {
            buildAnnotation(
                annotateWithGem,
                source ).ifPresent( t -> addAndValidateMapping( mappings, source, annotateWithGem, t ) );
        }
    }

    @Override
    public Set<Annotation> getProcessedAnnotations(Element source) {
        Set<Annotation> processedAnnotations = super.getProcessedAnnotations( source );
        return addDeprecatedAnnotation( source, processedAnnotations );
    }

    private Set<Annotation> addDeprecatedAnnotation(Element source, Set<Annotation> annotations) {
        DeprecatedGem deprecatedGem = DeprecatedGem.instanceOn( source );
        if ( deprecatedGem == null ) {
            return annotations;
        }
        Type deprecatedType = typeFactory.getType( Deprecated.class );
        if ( annotations.stream().anyMatch( annotation -> annotation.getType().equals( deprecatedType ) ) ) {
            messager.printMessage(
                    source,
                    deprecatedGem.mirror(),
                    Message.ANNOTATE_WITH_DUPLICATE,
                    deprecatedType.describe() );
            return annotations;
        }
        List<AnnotationElement> annotationElements = new ArrayList<>();
        if ( deprecatedGem.since().hasValue() ) {
            annotationElements.add( new AnnotationElement(
                AnnotationElementType.STRING,
                "since",
                Collections.singletonList( deprecatedGem.since().getValue() )
            ) );
        }
        if ( deprecatedGem.forRemoval().hasValue() ) {
            annotationElements.add( new AnnotationElement(
                AnnotationElementType.BOOLEAN,
                "forRemoval",
                Collections.singletonList( deprecatedGem.forRemoval().getValue() )
            ) );
        }
        annotations.add( new Annotation(deprecatedType, annotationElements ) );
        return annotations;
    }

    private void addAndValidateMapping(Set<Annotation> mappings, Element source, AnnotateWithGem gem, Annotation anno) {
        if ( anno.getType().getTypeElement().getAnnotation( Repeatable.class ) == null ) {
            if ( mappings.stream().anyMatch( existing -> existing.getType().equals( anno.getType() ) ) ) {
                messager.printMessage(
                            source,
                            gem.mirror(),
                            Message.ANNOTATE_WITH_ANNOTATION_IS_NOT_REPEATABLE,
                            anno.getType().describe() );
                return;
            }
        }
        if ( mappings.stream().anyMatch( existing -> {
            return existing.getType().equals( anno.getType() )
                && existing.getProperties().equals( anno.getProperties() );
        } ) ) {
            messager.printMessage(
                        source,
                        gem.mirror(),
                        Message.ANNOTATE_WITH_DUPLICATE,
                        anno.getType().describe() );
            return;
        }
        mappings.add( anno );
    }

    private Optional<Annotation> buildAnnotation(AnnotateWithGem annotationGem, Element element) {
        Type annotationType = typeFactory.getType( getTypeMirror( annotationGem.value() ) );
        List<ElementGem> eleGems = annotationGem.elements().get();
        if ( isValid( annotationType, eleGems, element, annotationGem.mirror() ) ) {
            return Optional.of( new Annotation( annotationType, convertToProperties( eleGems ) ) );
        }
        return Optional.empty();
    }

    private List<AnnotationElement> convertToProperties(List<ElementGem> eleGems) {
        return eleGems.stream().map( gem -> convertToProperty( gem, typeFactory ) ).collect( Collectors.toList() );
    }

    private enum ConvertToProperty {
        BOOLEAN(
            AnnotationElementType.BOOLEAN,
            (eleGem, typeFactory) -> eleGem.booleans().get(),
            eleGem -> eleGem.booleans().hasValue()
        ),
        BYTE(
            AnnotationElementType.BYTE,
            (eleGem, typeFactory) -> eleGem.bytes().get(),
            eleGem -> eleGem.bytes().hasValue()
        ),
        CHARACTER(
            AnnotationElementType.CHARACTER,
            (eleGem, typeFactory) -> eleGem.chars().get(),
            eleGem -> eleGem.chars().hasValue()
        ),
        CLASSES(
            AnnotationElementType.CLASS,
            (eleGem, typeFactory) -> {
                return eleGem.classes().get().stream().map( typeFactory::getType ).collect( Collectors.toList() );
            },
            eleGem -> eleGem.classes().hasValue()
        ),
        DOUBLE(
            AnnotationElementType.DOUBLE,
            (eleGem, typeFactory) -> eleGem.doubles().get(),
            eleGem -> eleGem.doubles().hasValue()
        ),
        ENUM(
            AnnotationElementType.ENUM,
            (eleGem, typeFactory) -> {
                List<EnumAnnotationElementHolder> values = new ArrayList<>();
                for ( String enumName : eleGem.enums().get() ) {
                    Type type = typeFactory.getType( eleGem.enumClass().get() );
                    values.add( new EnumAnnotationElementHolder( type, enumName ) );
                }
                return values;
            },
            eleGem -> eleGem.enums().hasValue() && eleGem.enumClass().hasValue()
        ),
        FLOAT(
            AnnotationElementType.FLOAT,
            (eleGem, typeFactory) -> eleGem.floats().get(),
            eleGem -> eleGem.floats().hasValue()
        ),
        INT(
            AnnotationElementType.INTEGER,
            (eleGem, typeFactory) -> eleGem.ints().get(),
            eleGem -> eleGem.ints().hasValue()
        ),
        LONG(
            AnnotationElementType.LONG,
            (eleGem, typeFactory) -> eleGem.longs().get(),
            eleGem -> eleGem.longs().hasValue()
        ),
        SHORT(
            AnnotationElementType.SHORT,
            (eleGem, typeFactory) -> eleGem.shorts().get(),
            eleGem -> eleGem.shorts().hasValue()
        ),
        STRING(
            AnnotationElementType.STRING,
            (eleGem, typeFactory) -> eleGem.strings().get(),
            eleGem -> eleGem.strings().hasValue()
        );

        private final AnnotationElementType type;
        private final BiFunction<ElementGem, TypeFactory, List<? extends Object>> factory;
        private final Predicate<ElementGem> usabilityChecker;

        ConvertToProperty(AnnotationElementType type,
                          BiFunction<ElementGem, TypeFactory, List<? extends Object>> factory,
                          Predicate<ElementGem> usabilityChecker) {
            this.type = type;
            this.factory = factory;
            this.usabilityChecker = usabilityChecker;
        }

        AnnotationElement toProperty(ElementGem eleGem, TypeFactory typeFactory) {
            return new AnnotationElement(
                type,
                eleGem.name().get(),
                factory.apply( eleGem, typeFactory )
            );
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

    private boolean isValid(Type annotationType, List<ElementGem> eleGems, Element element,
                            AnnotationMirror annotationMirror) {
        boolean isValid = true;
        if ( !annotationIsAllowed( annotationType, element, annotationMirror ) ) {
            isValid = false;
        }

        List<ExecutableElement> annotationElements = methodsIn( annotationType.getTypeElement()
            .getEnclosedElements() );
        if ( !allRequiredElementsArePresent( annotationType, annotationElements, eleGems, element,
                                             annotationMirror ) ) {
            isValid = false;
        }
        if ( !allElementsAreKnownInAnnotation( annotationType, annotationElements, eleGems, element ) ) {
            isValid = false;
        }
        if ( !allElementsAreOfCorrectType( annotationType, annotationElements, eleGems, element ) ) {
            isValid = false;
        }
        if ( !enumConstructionIsCorrectlyUsed( eleGems, element ) ) {
            isValid = false;
        }
        if ( !allElementsAreUnique( eleGems, element ) ) {
            isValid = false;
        }
        return isValid;
    }

    private boolean allElementsAreUnique(List<ElementGem> eleGems, Element element) {
        boolean isValid = true;
        List<String> checkedElements = new ArrayList<>();
        for ( ElementGem elementGem : eleGems ) {
            String elementName = elementGem.name().get();
            if ( checkedElements.contains( elementName ) ) {
                isValid = false;
                messager
                        .printMessage(
                            element,
                            elementGem.mirror(),
                            Message.ANNOTATE_WITH_DUPLICATE_PARAMETER,
                            elementName );
            }
            else {
                checkedElements.add( elementName );
            }
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
            else if ( elementGem.enumClass().getValue() != null ) {
                isValid = false;
                messager.printMessage( element, elementGem.mirror(), Message.ANNOTATE_WITH_ENUMS_NOT_DEFINED );
            }
        }
        return isValid;
    }

    private boolean annotationIsAllowed(Type annotationType, Element element, AnnotationMirror annotationMirror) {
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
                        annotationMirror,
                        Message.ANNOTATE_WITH_NOT_ALLOWED_ON_CLASS,
                        annotationType.describe()
            );
        }
        if ( isMethodTarget( element ) && !annotationTargets.contains( ElementType.METHOD ) ) {
            isValid = false;
            messager.printMessage(
                        element,
                        annotationMirror,
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

    private boolean allElementsAreKnownInAnnotation(Type annotationType, List<ExecutableElement> annotationParameters,
                                                      List<ElementGem> eleGems, Element element) {
        Set<String> allowedAnnotationParameters = annotationParameters.stream()
                                                                 .map( ee -> ee.getSimpleName().toString() )
                                                                 .collect( Collectors.toSet() );
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
                            annotationType.describe(),
                            Strings.getMostSimilarWord( eleGem.name().get(), allowedAnnotationParameters )
                        );
            }
        }
        return isValid;
    }

    private boolean allRequiredElementsArePresent(Type annotationType, List<ExecutableElement> annotationParameters,
                                                  List<ElementGem> elements, Element element,
                                                  AnnotationMirror annotationMirror) {

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
                            annotationMirror,
                            Message.ANNOTATE_WITH_MISSING_REQUIRED_PARAMETER,
                            parameterName,
                            annotationType.describe()
                        );
                }
            }
        }


        return valid;
    }

    private boolean allElementsAreOfCorrectType(Type annotationType, List<ExecutableElement> annotationParameters,
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
            if ( hasTooManyDifferentTypes( eleGem ) ) {
                isValid = false;
                messager.printMessage(
                    element,
                    eleGem.mirror(),
                    eleGem.name().getAnnotationValue(),
                    Message.ANNOTATE_WITH_TOO_MANY_VALUE_TYPES,
                    eleGem.name().get(),
                    annotationParameterType.describe(),
                    annotationType.describe()
                );
            }
            else {
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
        }
        return isValid;
    }

    private boolean hasTooManyDifferentTypes( ElementGem eleGem ) {
        return Arrays.stream( ConvertToProperty.values() )
                     .filter( anotationElement -> anotationElement.isUsable( eleGem ) )
                     .count() > 1;
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
        if ( eleGem.enums().hasValue() && eleGem.enumClass().hasValue() ) {
            suppliedParameterTypes.put(
                                      typeFactory.getType( getTypeMirror( eleGem.enumClass() ) ),
                                      eleGem.enums().get().size() );
        }
        return suppliedParameterTypes;
    }

    private Type getAnnotationParameterType(Map<String, ExecutableElement> annotationParameters,
                                                  ElementGem element) {
        if ( annotationParameters.containsKey( element.name().get() ) ) {
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
