/**
 * Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 * and/or other contributors as indicated by the @authors tag. See the
 * copyright.txt file in the distribution for a full listing of all
 * contributors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mapstruct.ap.internal.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import javax.lang.model.element.TypeElement;

import org.mapstruct.ap.internal.model.Annotation;
import org.mapstruct.ap.internal.model.AnnotationMapperReference;
import org.mapstruct.ap.internal.model.Decorator;
import org.mapstruct.ap.internal.model.Field;
import org.mapstruct.ap.internal.model.AnnotatedConstructor;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.model.MapperReference;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.MapperConfiguration;

/**
 * An {@link ModelElementProcessor} which converts the given {@link Mapper}
 * object into an annotation based component model in case a matching model is selected as
 * target component model for this mapper.
 *
 * @author Gunnar Morling
 * @author Andreas Gudian
 */
public abstract class AnnotationBasedComponentModelProcessor implements ModelElementProcessor<Mapper, Mapper> {

    private TypeFactory typeFactory;

    @Override
    public Mapper process(ProcessorContext context, TypeElement mapperTypeElement, Mapper mapper) {
        this.typeFactory = context.getTypeFactory();

        String componentModel = MapperConfiguration.getInstanceOn(mapperTypeElement)
            .componentModel(context.getOptions());

        if (!getComponentModelIdentifier().equalsIgnoreCase(componentModel)) {
            return mapper;
        }

        for (Annotation typeAnnotation : getTypeAnnotations(mapper)) {
            mapper.addAnnotation(typeAnnotation);
        }

        if (!requiresGenerationOfDecoratorClass()) {
            mapper.removeDecorator();
        }
        else if (mapper.getDecorator() != null) {
            adjustDecorator(mapper);
        }

        List<Annotation> annotations = getMapperReferenceAnnotations();
        List<MapperReference> referencedMappers = mapper.getReferencedMappers();

        ListIterator<MapperReference> iterator = mapper.getReferencedMappers().listIterator();
        while (iterator.hasNext()) {
            MapperReference reference = iterator.next();
            iterator.remove();
            iterator.add(replacementMapperReference(reference, annotations));
        }

        Decorator decorator = mapper.getDecorator();

        if (!referencedMappers.isEmpty() || decorator != null) {
            AnnotatedConstructor annotatedConstructor = buildFieldConstructor(mapper);

            if (decorator != null) {
                decorator.setConstructor(annotatedConstructor);
            }
            else {
                mapper.setConstructor(annotatedConstructor);
            }
        }

        return mapper;
    }

    protected void adjustDecorator(Mapper mapper) {
        Decorator decorator = mapper.getDecorator();

        for (Annotation typeAnnotation : getDecoratorAnnotations()) {
            decorator.addAnnotation(typeAnnotation);
        }

        decorator.removeConstructor();

        List<Annotation> annotations = getDelegatorReferenceAnnotations(mapper);
        List<Field> replacement = new ArrayList<Field>();
        if (!decorator.getMethods().isEmpty()) {
            for (Field field : decorator.getFields()) {
                replacement.add(replacementMapperReference(field, annotations));
            }
        }
        decorator.setFields(replacement);
    }

    private AnnotatedConstructor buildFieldConstructor(Mapper mapper) {
        List<Annotation> mapperReferenceAnnotations = getMapperReferenceAnnotations();
        List<MapperReference> referencedMappers = mapper.getReferencedMappers();
        Decorator decorator = mapper.getDecorator();

        List<AnnotationMapperReference> mapperReferencesForConstructor = new ArrayList<AnnotationMapperReference>();
        for (MapperReference mapperReference : referencedMappers) {
            mapperReferencesForConstructor.add((AnnotationMapperReference) mapperReference);
        }

        if (decorator != null) {
            for (Field field : decorator.getFields()) {
                if (field instanceof AnnotationMapperReference) {
                    AnnotationMapperReference mapperReference = (AnnotationMapperReference) field;
                    mapperReferencesForConstructor.add(mapperReference);
                }
            }
        }

        removeReferenceAnnotations(mapperReferencesForConstructor, mapperReferenceAnnotations);

        String constructorName = decorator == null ? mapper.getName() : decorator.getName();

        return new AnnotatedConstructor(constructorName, mapperReferencesForConstructor,
            mapperReferenceAnnotations, publicEmptyConstructorWhenConstructorInjectionIsUsed());
    }

    private void removeReferenceAnnotations(List<AnnotationMapperReference> mapperReferences,
        List<Annotation> mapperReferenceAnnotations) {
        ListIterator<AnnotationMapperReference> mapperReferenceIterator = mapperReferences.listIterator();

        Set<Type> mapperReferenceAnnotationsTypes = new HashSet<Type>();
        for (Annotation annotation : mapperReferenceAnnotations) {
            mapperReferenceAnnotationsTypes.add(annotation.getType());
        }

        while (mapperReferenceIterator.hasNext()) {
            MapperReference mapperReference = mapperReferenceIterator.next();
            AnnotationMapperReference annotationMapperReference = (AnnotationMapperReference) mapperReference;
            mapperReferenceIterator.remove();

            List<Annotation> qualifiers = new ArrayList<Annotation>();
            for (Annotation annotation : annotationMapperReference.getAnnotations()) {
                if (!mapperReferenceAnnotationsTypes.contains(annotation.getType())) {
                    qualifiers.add(annotation);
                }
            }

            AnnotationMapperReference annotationMapperReference1 = new AnnotationMapperReference(
                annotationMapperReference.getType(),
                annotationMapperReference.getVariableName(),
                qualifiers,
                annotationMapperReference.isUsed()
            );

            mapperReferenceIterator.add(annotationMapperReference1);
        }
    }

    protected boolean publicEmptyConstructorWhenConstructorInjectionIsUsed() {
        return true;
    }

    protected List<Annotation> getDelegatorReferenceAnnotations(Mapper mapper) {
        return Collections.emptyList();
    }

    /**
     * @param originalReference
     *     the reference to be replaced
     * @param annotations
     *     the list of annotations
     * @return the mapper reference replacing the original one
     */
    protected MapperReference replacementMapperReference(Field originalReference, List<Annotation> annotations) {
        return new AnnotationMapperReference(
            originalReference.getType(),
            originalReference.getVariableName(),
            annotations,
            originalReference.isUsed()
        );
    }

    /**
     * @return the component model identifier
     */
    protected abstract String getComponentModelIdentifier();

    /**
     * @param mapper
     *     the mapper
     * @return the annotation(s) to be added at the mapper type implementation
     */
    protected abstract List<Annotation> getTypeAnnotations(Mapper mapper);

    /**
     * @return the annotation(s) to be added at the decorator of the mapper
     */
    protected List<Annotation> getDecoratorAnnotations() {
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
