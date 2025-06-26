/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.element.TypeElement;

import org.mapstruct.ap.internal.gem.InjectionStrategyGem;
import org.mapstruct.ap.internal.model.AnnotatedConstructor;
import org.mapstruct.ap.internal.model.AnnotatedSetter;
import org.mapstruct.ap.internal.model.Annotation;
import org.mapstruct.ap.internal.model.AnnotationMapperReference;
import org.mapstruct.ap.internal.model.Decorator;
import org.mapstruct.ap.internal.model.Field;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.model.MapperReference;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.MapperOptions;

/**
 * An {@link ModelElementProcessor} which converts the given {@link Mapper} object into an annotation based component
 * model in case a matching model is selected as target component model for this mapper.
 *
 * @author Gunnar Morling
 * @author Andreas Gudian
 * @author Kevin Grüneberg
 */
public abstract class AnnotationBasedComponentModelProcessor implements ModelElementProcessor<Mapper, Mapper> {

    private TypeFactory typeFactory;

    @Override
    public Mapper process(ProcessorContext context, TypeElement mapperTypeElement, Mapper mapper) {
        this.typeFactory = context.getTypeFactory();

        MapperOptions mapperAnnotation = MapperOptions.getInstanceOn( mapperTypeElement, context.getOptions() );

        String componentModel = mapperAnnotation.componentModel();
        InjectionStrategyGem injectionStrategy = mapperAnnotation.getInjectionStrategy();

        if ( !getComponentModelIdentifier().equalsIgnoreCase( componentModel ) ) {
            return mapper;
        }

        for ( Annotation typeAnnotation : getTypeAnnotations( mapper ) ) {
            mapper.addAnnotation( typeAnnotation );
        }

        if ( !requiresGenerationOfDecoratorClass() ) {
            mapper.removeDecorator();
        }
        else if ( mapper.getDecorator() != null ) {
            adjustDecorator( mapper, injectionStrategy );
        }

        List<Annotation> annotations = getMapperReferenceAnnotations();
        ListIterator<Field> iterator = mapper.getFields().listIterator();

        while ( iterator.hasNext() ) {

            Field reference = iterator.next();
            if ( reference instanceof  MapperReference ) {
                iterator.remove();
                iterator.add( replacementMapperReference( reference, annotations, injectionStrategy ) );
            }
        }

        if ( injectionStrategy == InjectionStrategyGem.CONSTRUCTOR ) {
            buildConstructors( mapper );
        }
        else if ( injectionStrategy == InjectionStrategyGem.SETTER ) {
            buildSetters( mapper );
        }

        return mapper;
    }

    protected void adjustDecorator(Mapper mapper, InjectionStrategyGem injectionStrategy) {
        Decorator decorator = mapper.getDecorator();

        for ( Annotation typeAnnotation : getDecoratorAnnotations( decorator ) ) {
            decorator.addAnnotation( typeAnnotation );
        }

        decorator.removeConstructor();

        List<Annotation> annotations = getDelegatorReferenceAnnotations( mapper );
        List<Field> replacement = new ArrayList<>();
        if ( !decorator.getMethods().isEmpty() ) {
            for ( Field field : decorator.getFields() ) {
                replacement.add( replacementMapperReference( field, annotations, injectionStrategy ) );
            }
        }
        decorator.setFields( replacement );
    }

    private List<MapperReference> toMapperReferences(List<Field> fields) {
        List<MapperReference> mapperReferences = new ArrayList<>();
        for ( Field field : fields ) {
            if ( field instanceof  MapperReference ) {
                mapperReferences.add( (MapperReference) field );
            }
        }
        return mapperReferences;
    }

    private void buildSetters(Mapper mapper) {
        List<MapperReference> mapperReferences = toMapperReferences( mapper.getFields() );
        for ( MapperReference mapperReference : mapperReferences ) {
            if ( mapperReference.isUsed() ) {
                AnnotatedSetter setter = new AnnotatedSetter(
                    mapperReference,
                    getMapperReferenceAnnotations(),
                    Collections.emptyList()
                );
                mapper.getMethods().add( setter );
            }
        }

        Decorator decorator = mapper.getDecorator();
        if ( decorator != null ) {
            List<Annotation> mapperReferenceAnnotations = getMapperReferenceAnnotations();
            Set<Type> mapperReferenceAnnotationsTypes = mapperReferenceAnnotations
                .stream()
                .map( Annotation::getType )
                .collect( Collectors.toSet() );
            for ( Field field : decorator.getFields() ) {
                if ( field instanceof AnnotationMapperReference ) {

                    List<Annotation> fieldAnnotations = ( (AnnotationMapperReference) field ).getAnnotations();

                    List<Annotation> qualifiers = extractMissingAnnotations(
                        fieldAnnotations,
                        mapperReferenceAnnotationsTypes
                    );

                    decorator.getMethods().add( new AnnotatedSetter( field, mapperReferenceAnnotations, qualifiers ) );
                }
            }
        }
    }

    private void buildConstructors(Mapper mapper) {
        if ( !toMapperReferences( mapper.getFields() ).isEmpty() ) {
            AnnotatedConstructor annotatedConstructor = buildAnnotatedConstructorForMapper( mapper );

            if ( !annotatedConstructor.getMapperReferences().isEmpty() ) {
                mapper.setConstructor( annotatedConstructor );
            }
        }

        Decorator decorator = mapper.getDecorator();

        if ( decorator != null ) {
            AnnotatedConstructor decoratorConstructor = buildAnnotatedConstructorForDecorator( decorator );
            if ( !decoratorConstructor.getMapperReferences().isEmpty() ) {
                decorator.setConstructor( decoratorConstructor );
            }
        }
    }

    private AnnotatedConstructor buildAnnotatedConstructorForMapper(Mapper mapper) {
        List<MapperReference> mapperReferences = toMapperReferences( mapper.getFields() );
        List<AnnotationMapperReference> mapperReferencesForConstructor =
            new ArrayList<>( mapperReferences.size() );

        for ( MapperReference mapperReference : mapperReferences ) {
            if ( mapperReference.isUsed() ) {
                mapperReferencesForConstructor.add( (AnnotationMapperReference) mapperReference );
            }
        }

        List<Annotation> mapperReferenceAnnotations = getMapperReferenceAnnotations();

        removeDuplicateAnnotations( mapperReferencesForConstructor, mapperReferenceAnnotations );

        return AnnotatedConstructor.forComponentModels(
            mapper.getName(),
            mapperReferencesForConstructor,
            mapperReferenceAnnotations,
            mapper.getConstructor(),
            additionalPublicEmptyConstructor()
        );
    }

    private AnnotatedConstructor buildAnnotatedConstructorForDecorator(Decorator decorator) {
        List<AnnotationMapperReference> mapperReferencesForConstructor =
            new ArrayList<>( decorator.getFields().size() );

        for ( Field field : decorator.getFields() ) {
            if ( field instanceof AnnotationMapperReference ) {
                mapperReferencesForConstructor.add( (AnnotationMapperReference) field );
            }
        }

        List<Annotation> mapperReferenceAnnotations = getMapperReferenceAnnotations();

        removeDuplicateAnnotations( mapperReferencesForConstructor, mapperReferenceAnnotations );

        return AnnotatedConstructor.forComponentModels(
            decorator.getName(),
            mapperReferencesForConstructor,
            mapperReferenceAnnotations,
            decorator.getConstructor(),
            additionalPublicEmptyConstructor()
        );
    }

    /**
     * Removes duplicate constructor parameter annotations. If an annotation is already present on the constructor, it
     * does not have be defined on the constructor parameter, too. For example, for CDI, the javax.inject.Inject
     * annotation is on the constructor and does not need to be on the constructor parameters.
     *
     * @param annotationMapperReferences annotations to annotate the constructor parameter with
     * @param mapperReferenceAnnotations annotations to annotate the constructor with
     */
    private void removeDuplicateAnnotations(List<AnnotationMapperReference> annotationMapperReferences,
                                            List<Annotation> mapperReferenceAnnotations) {
        ListIterator<AnnotationMapperReference> mapperReferenceIterator = annotationMapperReferences.listIterator();

        Set<Type> mapperReferenceAnnotationsTypes = new HashSet<>();
        for ( Annotation annotation : mapperReferenceAnnotations ) {
            mapperReferenceAnnotationsTypes.add( annotation.getType() );
        }

        while ( mapperReferenceIterator.hasNext() ) {
            AnnotationMapperReference annotationMapperReference = mapperReferenceIterator.next();
            mapperReferenceIterator.remove();

            List<Annotation> qualifiers = extractMissingAnnotations(
                annotationMapperReference.getAnnotations(),
                mapperReferenceAnnotationsTypes
            );

            mapperReferenceIterator.add( annotationMapperReference.withNewAnnotations( qualifiers ) );
        }
    }

    /**
     * Extract all annotations from {@code annotations} that do not have a type in {@code annotationTypes}.
     *
     * @param annotations     the annotations from which we need to extract information
     * @param annotationTypes the annotation types to ignore
     * @return the annotations that are not in the {@code annotationTypes}
     */
    private List<Annotation> extractMissingAnnotations(List<Annotation> annotations,
                                                       Set<Type> annotationTypes) {
        List<Annotation> qualifiers = new ArrayList<>();
        for ( Annotation annotation : annotations ) {
            if ( !annotationTypes.contains( annotation.getType() ) ) {
                qualifiers.add( annotation );
            }
        }
        return qualifiers;
    }

    protected boolean additionalPublicEmptyConstructor() {
        return false;
    }

    protected List<Annotation> getDelegatorReferenceAnnotations(Mapper mapper) {
        return Collections.emptyList();
    }

    /**
     * @param originalReference the reference to be replaced
     * @param annotations the list of annotations
     * @param injectionStrategy strategy for injection
     * @return the mapper reference replacing the original one
     */
    protected Field replacementMapperReference(Field originalReference, List<Annotation> annotations,
                                                         InjectionStrategyGem injectionStrategy) {
        boolean finalField =
            injectionStrategy == InjectionStrategyGem.CONSTRUCTOR && !additionalPublicEmptyConstructor();

        boolean includeAnnotationsOnField = injectionStrategy == InjectionStrategyGem.FIELD;

        return new AnnotationMapperReference(
            originalReference.getType(),
            originalReference.getVariableName(),
            annotations,
            originalReference.isUsed(),
            finalField,
            includeAnnotationsOnField );
    }

    /**
     * @return the component model identifier
     */
    protected abstract String getComponentModelIdentifier();

    /**
     * @param mapper the mapper
     * @return the annotation(s) to be added at the mapper type implementation
     */
    protected abstract List<Annotation> getTypeAnnotations(Mapper mapper);

    /**
     * @return the annotation(s) to be added at the decorator of the mapper
     */
    protected List<Annotation> getDecoratorAnnotations(Decorator decorator) {
        return Collections.emptyList();
    }

    /**
     * @return the annotation of the field for the mapper reference
     */
    protected abstract List<Annotation> getMapperReferenceAnnotations();

    /**
     * @return if a decorator (sub-)class needs to be generated or not
     */
    protected abstract boolean requiresGenerationOfDecoratorClass();

    @Override
    public int getPriority() {
        return 1100;
    }

    protected TypeFactory getTypeFactory() {
        return typeFactory;
    }
}
