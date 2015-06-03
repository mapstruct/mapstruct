/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.internal.processor;

import java.util.List;
import java.util.ListIterator;

import javax.lang.model.element.TypeElement;

import org.mapstruct.ap.internal.model.Annotation;
import org.mapstruct.ap.internal.model.AnnotationMapperReference;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.model.MapperReference;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.option.OptionsHelper;
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

        String componentModel = MapperConfiguration.getInstanceOn( mapperTypeElement ).componentModel();
        String effectiveComponentModel = OptionsHelper.getEffectiveComponentModel(
            context.getOptions(),
            componentModel
        );

        if ( !getComponentModelIdentifier().equalsIgnoreCase( effectiveComponentModel ) ) {
            return mapper;
        }

        for ( Annotation typeAnnotation : getTypeAnnotations( mapper ) ) {
            mapper.addAnnotation( typeAnnotation );
        }

        if ( !requiresGenerationOfDecoratorClass() ) {
            mapper.removeDecorator();
        }

        ListIterator<MapperReference> iterator = mapper.getReferencedMappers().listIterator();
        while ( iterator.hasNext() ) {
            MapperReference reference = iterator.next();
            iterator.remove();
            iterator.add( replacementMapperReference( reference ) );
        }

        return mapper;
    }

    /**
     * @param originalReference the reference to be replaced
     *
     * @return the mapper reference replacing the original one
     */
    protected MapperReference replacementMapperReference(MapperReference originalReference) {
        return new AnnotationMapperReference(
            originalReference.getType(),
            originalReference.getVariableName(),
            getMapperReferenceAnnotation(),
            originalReference.isUsed()
        );
    }

    /**
     * @return the component model identifier
     */
    protected abstract String getComponentModelIdentifier();

    /**
     * @return the annotation(s) to be added at the mapper type implementation
     */
    protected abstract List<Annotation> getTypeAnnotations(Mapper mapper);

    /**
     * @return the annotation of the field for the mapper reference
     */
    protected abstract Annotation getMapperReferenceAnnotation();

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
