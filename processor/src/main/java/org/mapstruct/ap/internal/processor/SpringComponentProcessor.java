/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;

import org.mapstruct.ap.internal.model.Annotation;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.prism.SpringComponentTypePrism;
import org.mapstruct.ap.internal.prism.SpringMapperPrism;

/**
 * A {@link ModelElementProcessor} which converts the given {@link Mapper}
 * object into a Spring bean in case Spring is configured as the
 * target component model for this mapper.
 *
 * @author Gunnar Morling
 * @author Andreas Gudian
 * @author Christian Bandowski
 */
public class SpringComponentProcessor extends AnnotationBasedComponentModelProcessor {

    private static final String CANONICAL_NAME_AUTOWIRED = "org.springframework.beans.factory.annotation.Autowired";
    private static final String CANONICAL_NAME_QUALIFIER = "org.springframework.beans.factory.annotation.Qualifier";
    private static final String CANONICAL_NAME_PRIMARY = "org.springframework.context.annotation.Primary";
    private static final String CANONICAL_NAME_SPRINGMAPPER = "org.mapstruct.SpringMapper";

    private static final String DEFAULT_VALUE_DELEGATE = "delegate";

    @Override
    protected String getComponentModelIdentifier() {
        return "spring";
    }

    @Override
    protected List<Annotation> getTypeAnnotations(Mapper mapper) {
        List<Annotation> typeAnnotations = new ArrayList<Annotation>();
        SpringMapperPrism config = findConfig( mapper );

        // defaults to @Component annotation without any name
        SpringComponentTypePrism componentType = SpringComponentTypePrism.COMPONENT;
        String name = null;

        if ( config != null ) {
            componentType = SpringComponentTypePrism.valueOf( config.componentType() );
            if ( mapper.getDecorator() == null ) {
                // the name must be used for the decorator, thus only set this here in case there is no decorator
                name = config.name();
            }
        }

        typeAnnotations.add( componentTypeAnnotation( componentType, name ) );

        if ( mapper.getDecorator() != null ) {
            typeAnnotations.add( qualifierDelegate( config == null ? null : config.delegateQualifier() ) );
        }

        return typeAnnotations;
    }

    @Override
    protected List<Annotation> getDecoratorAnnotations(Mapper mapper) {
        SpringMapperPrism config = findConfig( mapper );

        SpringComponentTypePrism componentType = SpringComponentTypePrism.COMPONENT;
        String name = null;

        if ( config != null ) {
            name = config.name();
            componentType = SpringComponentTypePrism.valueOf( config.componentType() );
        }

        return Arrays.asList(
            componentTypeAnnotation( componentType, name ),
            primary()
        );
    }

    @Override
    protected List<Annotation> getMapperReferenceAnnotations() {
        return Collections.singletonList(
            autowired()
        );
    }

    @Override
    protected List<Annotation> getDelegatorReferenceAnnotations(Mapper mapper) {
        SpringMapperPrism config = findConfig( mapper );

        return Arrays.asList(
            autowired(),
            qualifierDelegate( config == null ? null : config.delegateQualifier() )
        );
    }

    @Override
    protected boolean requiresGenerationOfDecoratorClass() {
        return true;
    }

    private Annotation autowired() {
        return new Annotation( getTypeFactory().getType( CANONICAL_NAME_AUTOWIRED ) );
    }

    private Annotation qualifierDelegate(String value) {
        if ( value == null || value.isEmpty() ) {
            value = DEFAULT_VALUE_DELEGATE;
        }

        return new Annotation(
            getTypeFactory().getType( CANONICAL_NAME_QUALIFIER ),
            Collections.singletonList( '"' + value + '"' )
        );
    }

    private Annotation primary() {
        return new Annotation( getTypeFactory().getType( CANONICAL_NAME_PRIMARY ) );
    }

    private Annotation componentTypeAnnotation(SpringComponentTypePrism componentType, String value) {
        List<String> properties = Collections.emptyList();
        if ( value != null && !value.isEmpty() ) {
            properties = Collections.singletonList( '"' + value + '"' );
        }

        return new Annotation( componentType( componentType ), properties );
    }

    private Type componentType(SpringComponentTypePrism componentType) {
        if ( componentType == null ) {
            componentType = SpringComponentTypePrism.COMPONENT;
        }

        return getTypeFactory().getType( componentType.getCanonicalName() );
    }

    private SpringMapperPrism findConfig(Mapper mapper) {
        for ( AnnotationMirror am : mapper.getMapperAnnotations() ) {
            CharSequence annTypeName = ( (TypeElement) am.getAnnotationType().asElement() ).getQualifiedName();
            if ( CANONICAL_NAME_SPRINGMAPPER.contentEquals( annTypeName ) ) {
                return SpringMapperPrism.getInstance( am );
            }
        }

        return null;
    }
}
