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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mapstruct.ap.internal.model.Annotation;
import org.mapstruct.ap.internal.model.Mapper;

/**
 * A {@link ModelElementProcessor} which converts the given {@link Mapper}
 * object into a Spring bean in case Spring is configured as the
 * target component model for this mapper.
 *
 * @author Gunnar Morling
 * @author Andreas Gudian
 */
public class SpringComponentProcessor extends AnnotationBasedComponentModelProcessor {
    @Override
    protected String getComponentModelIdentifier() {
        return "spring";
    }

    @Override
    protected List<Annotation> getTypeAnnotations(Mapper mapper) {
        List<Annotation> typeAnnotations = new ArrayList<Annotation>();
        typeAnnotations.add( component() );

        if ( mapper.getDecorator() != null ) {
            typeAnnotations.add( qualifierDelegate() );
        }

        return typeAnnotations;
    }

    @Override
    protected List<Annotation> getDecoratorAnnotations() {
        return Arrays.asList(
            component(),
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
        return Arrays.asList(
            autowired(),
            qualifierDelegate()
        );
    }

    @Override
    protected boolean requiresGenerationOfDecoratorClass() {
        return true;
    }

    private Annotation autowired() {
        return new Annotation( getTypeFactory().getType( "org.springframework.beans.factory.annotation.Autowired" ) );
    }

    private Annotation qualifierDelegate() {
        return new Annotation(
            getTypeFactory().getType( "org.springframework.beans.factory.annotation.Qualifier" ),
            Collections.singletonList( "\"delegate\"" ) );
    }

    private Annotation primary() {
        return new Annotation( getTypeFactory().getType( "org.springframework.context.annotation.Primary" ) );
    }

    private Annotation component() {
        return new Annotation( getTypeFactory().getType( "org.springframework.stereotype.Component" ) );
    }
}
