/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.processor;

import org.mapstruct.ap.model.Annotation;
import org.mapstruct.ap.model.Mapper;
import org.mapstruct.ap.model.Type;

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
    protected Annotation getTypeAnnotation() {
        return new Annotation( new Type( "javax.inject", "Named" ) );
    }

    @Override
    protected Annotation getMapperReferenceAnnotation() {
        return new Annotation( new Type( "javax.inject", "Inject" ) );
    }
}
