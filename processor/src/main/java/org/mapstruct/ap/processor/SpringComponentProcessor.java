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

import java.util.ListIterator;
import javax.lang.model.element.TypeElement;

import org.mapstruct.ap.MapperPrism;
import org.mapstruct.ap.model.Annotation;
import org.mapstruct.ap.model.Mapper;
import org.mapstruct.ap.model.MapperReference;
import org.mapstruct.ap.model.SpringMapperReference;
import org.mapstruct.ap.model.Type;
import org.mapstruct.ap.util.OptionsHelper;

/**
 * A {@link ModelElementProcessor} which converts the given {@link Mapper}
 * object into a Spring bean in case Spring is configured as the
 * target component model for this mapper.
 *
 * @author Gunnar Morling
 * @author Andreas Gudian
 */
public class SpringComponentProcessor implements ModelElementProcessor<Mapper, Mapper> {

    @Override
    public Mapper process(ProcessorContext context, TypeElement mapperTypeElement, Mapper mapper) {
        String componentModel = MapperPrism.getInstanceOn( mapperTypeElement ).componentModel();
        String effectiveComponentModel = OptionsHelper.getEffectiveComponentModel(
            context.getOptions(),
            componentModel
        );

        if ( !"spring".equalsIgnoreCase( effectiveComponentModel ) ) {
            return mapper;
        }

        mapper.addAnnotation( new Annotation( new Type( "org.springframework.stereotype", "Component" ) ) );

        ListIterator<MapperReference> iterator = mapper.getReferencedMappers().listIterator();
        while ( iterator.hasNext() ) {
            MapperReference reference = iterator.next();
            iterator.remove();
            iterator.add( new SpringMapperReference( reference.getMapperType() ) );
        }

        return mapper;
    }

    @Override
    public int getPriority() {
        return 1105;
    }
}
