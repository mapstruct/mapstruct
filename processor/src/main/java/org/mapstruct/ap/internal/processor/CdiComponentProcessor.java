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
 * object into an application-scoped CDI bean in case CDI is configured as the
 * target component model for this mapper.
 *
 * @author Gunnar Morling
 */
public class CdiComponentProcessor extends AnnotationBasedComponentModelProcessor {

    @Override
    protected String getComponentModelIdentifier() {
        return "cdi";
    }

    @Override
    protected List<Annotation> getTypeAnnotations(Mapper mapper) {
        return Collections.singletonList(
                new Annotation( getTypeFactory().getType( "javax.enterprise.context.ApplicationScoped" ) )
        );
    }

    @Override
    protected List<Annotation> getMapperReferenceAnnotations() {
        return Arrays.asList( new Annotation( getTypeFactory().getType( "javax.inject.Inject" ) ) );
    }

    @Override
    protected boolean requiresGenerationOfDecoratorClass() {
        return false;
    }
}
