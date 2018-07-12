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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.TypeElement;

import org.mapstruct.ap.internal.model.Annotation;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.prism.Jsr330MapperPrism;

/**
 * A {@link ModelElementProcessor} which converts the given {@link Mapper}
 * object into a JSR 330 style bean in case "jsr330" is configured as the
 * target component model for this mapper.
 *
 * @author Gunnar Morling
 * @author Andreas Gudian
 * @author Christian Bandowski
 */
public class Jsr330ComponentProcessor extends AnnotationBasedComponentModelProcessor {

    private static final String CANONICAL_NAME_NAMED = "javax.inject.Named";
    private static final String CANONICAL_NAME_INJECT = "javax.inject.Inject";
    private static final String CANONICAL_NAME_SINGLETON = "javax.inject.Singleton";
    private static final String CANONICAL_NAME_JSR330MAPPER = "org.mapstruct.Jsr330Mapper";

    @Override
    protected String getComponentModelIdentifier() {
        return "jsr330";
    }

    @Override
    protected List<Annotation> getTypeAnnotations(Mapper mapper) {
        Jsr330MapperPrism config = findConfig( mapper );

        if ( mapper.getDecorator() == null ) {
            return Arrays.asList( singleton(), named( config == null ? null : config.name() ) );
        }
        else {
            return Arrays.asList( singleton(), namedDelegate( mapper ) );
        }
    }

    @Override
    protected List<Annotation> getDecoratorAnnotations(Mapper mapper) {
        Jsr330MapperPrism config = findConfig( mapper );

        return Arrays.asList(
            singleton(),
            named( config == null ? null : config.name() )
        );
    }

    @Override
    protected List<Annotation> getDelegatorReferenceAnnotations(Mapper mapper) {
        return Arrays.asList(
            inject(),
            namedDelegate( mapper )
        );
    }

    @Override
    protected List<Annotation> getMapperReferenceAnnotations() {
        return Collections.singletonList( inject() );
    }

    @Override
    protected boolean requiresGenerationOfDecoratorClass() {
        return true;
    }

    private Annotation singleton() {
        return new Annotation( getTypeFactory().getType( CANONICAL_NAME_SINGLETON ) );
    }

    private Annotation named(String value) {
        List<String> properties = Collections.emptyList();
        if ( value != null && !value.isEmpty() ) {
            properties = Collections.singletonList( '"' + value + '"' );
        }

        return new Annotation(
            getTypeFactory().getType( CANONICAL_NAME_NAMED ),
            properties
        );
    }

    private Annotation namedDelegate(Mapper mapper) {
        return named( mapper.getPackageName() + "." + mapper.getName() );
    }

    private Annotation inject() {
        return new Annotation( getTypeFactory().getType( CANONICAL_NAME_INJECT ) );
    }

    private Jsr330MapperPrism findConfig(Mapper mapper) {
        for ( AnnotationMirror am : mapper.getMapperAnnotations() ) {
            CharSequence annTypeName = ( (TypeElement) am.getAnnotationType().asElement() ).getQualifiedName();
            if ( CANONICAL_NAME_JSR330MAPPER.contentEquals( annTypeName ) ) {
                return Jsr330MapperPrism.getInstance( am );
            }
        }

        return null;
    }
}
