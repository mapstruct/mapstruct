/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import org.mapstruct.ap.internal.model.AbstractClassConstructor;
import org.mapstruct.ap.internal.model.AnnotatedConstructor;
import org.mapstruct.ap.internal.model.Annotation;
import org.mapstruct.ap.internal.model.Constructor;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.model.MapperReference;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;

/**
 * An {@link ModelElementProcessor} which processes abstract classes and creates a constructor compatible constructor
 * for the declared {@link TypeElement}
 *
 * @author Marvin Froeder
 */
public class AbstractClassConstructorProcessor implements ModelElementProcessor<Mapper, Mapper> {

    class SimpleMapperReference extends MapperReference {

        SimpleMapperReference(Type type, String variableName) {
            super( type, variableName );
        }

    }

    @Override
    public int getPriority() {
        return 2000;
    }

    @Override
    public Mapper process(ProcessorContext context, TypeElement mapperTypeElement, Mapper mapper) {
        if ( !mapperTypeElement.getModifiers().contains( Modifier.ABSTRACT ) ) {
            return mapper;
        }

        List<ExecutableElement> constructors = ElementFilter.constructorsIn( mapperTypeElement.getEnclosedElements() );
        if ( constructors.isEmpty() ) {
            return mapper;
        }

        List<MapperReference> superclassParameters = mapperReferences( context, mapperTypeElement );

        List<MapperReference> extraParameters = new ArrayList<>();
        List<Annotation> annotations = new ArrayList<>();

        Constructor constructor = mapper.getConstructor();
        if ( constructor instanceof AnnotatedConstructor ) {
            AnnotatedConstructor annotatedConstructor = (AnnotatedConstructor) constructor;
            annotations = annotatedConstructor.getAnnotations();
            extraParameters.addAll( annotatedConstructor.getMapperReferences() );
        }

        if ( !superclassParameters.isEmpty() ) {
            mapper.setConstructor( new AbstractClassConstructor( mapper.getName(), superclassParameters,
                extraParameters, annotations ) );
        }

        return mapper;
    }

    private List<MapperReference> mapperReferences(ProcessorContext context, TypeElement mapperTypeElement) {
        List<ExecutableElement> constructors = ElementFilter.constructorsIn( mapperTypeElement.getEnclosedElements() );

        if ( constructors.size() != 1 ) {
            throw new RuntimeException();
        }

        List<MapperReference> mapperReferences = constructors.get( 0 ).getParameters().stream()
                        .map( element -> toMapperReference( context.getTypeFactory(), element ) )
                        .collect( Collectors.toList() );

        return mapperReferences;
    }

    private SimpleMapperReference toMapperReference(TypeFactory typeFactory, VariableElement element) {
        return new SimpleMapperReference( typeFactory.getType( element.asType() ), element.getSimpleName().toString() );
    }

}
