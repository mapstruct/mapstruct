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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.mapstruct.ap.internal.model.Annotation;
import org.mapstruct.ap.internal.model.Mapper;

/**
 * A {@link ModelElementProcessor} which converts the given {@link Mapper}
 * object into a JSR 330 style bean in case "jsr330" is configured as the
 * target component model for this mapper.
 *
 * @author Gunnar Morling
 * @author Andreas Gudian
 */
public class Jsr330ComponentProcessor extends AnnotationBasedComponentModelProcessor {
    @Override
    protected String getComponentModelIdentifier() {
        return "jsr330";
    }

    @Override
    protected List<Annotation> getTypeAnnotations(Mapper mapper) {
        if ( mapper.getDecorator() == null ) {
            return Collections.singletonList( named() );
        }
        else {
            return Collections.singletonList( namedDelegate( mapper ) );
        }
    }

    @Override
    protected List<Annotation> getDecoratorAnnotations() {
        return Collections.singletonList( named() );
    }

    @Override
    protected List<Annotation> getDelegatorReferenceAnnotations(Mapper mapper) {
        return Arrays.asList( inject(), namedDelegate( mapper ) );
    }

    @Override
    protected List<Annotation> getMapperReferenceAnnotations() {
        return Collections.singletonList( inject() );
    }

    @Override
    protected boolean requiresGenerationOfDecoratorClass() {
        return true;
    }

    private Annotation named() {
        return new Annotation( getTypeFactory().getType( "javax.inject.Named" ) );
    }

    private Annotation namedDelegate(Mapper mapper) {
        return new Annotation(
            getTypeFactory().getType( "javax.inject.Named" ),
            Collections.singletonList( '"' + mapper.getPackageName() + "." + mapper.getName() + '"' )
        );
    }

    private Annotation inject() {
        return new Annotation( getTypeFactory().getType( "javax.inject.Inject" ) );
    }
}
