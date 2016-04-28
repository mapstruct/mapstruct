/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.processor.mapperfactory;


import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.TypeElement;
import org.mapstruct.ap.internal.model.GeneratedType;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.model.mapperfactory.MapperFactory;
import org.mapstruct.ap.internal.model.mapperfactory.MapperFactoryMethod;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.prism.MapperFactoryPrism;
import org.mapstruct.ap.internal.processor.MapperElementProcessor;
import org.mapstruct.ap.internal.util.MapperConfiguration;

/**
 * A {@link MapperElementProcessor} which creates a {@link Mapper} from the given
 * list of {@link SourceMethod}s.
 *
 * @author Sjaak Derksen
 */
public class FactoryCreationProcessor implements FactoryElementProcessor<List<SourceMethod>, MapperFactory> {


    @Override
    public MapperFactory process(ProcessorContext context, FactoryGenerationInfo info, List<SourceMethod> sourceModel) {

        List<MapperFactoryMethod> mapperFactoryMethods = new ArrayList<MapperFactoryMethod>();
        for ( SourceMethod mapperFactoryMethod : sourceModel ) {

            // TODO: decorators? Other package
            // TODO: I'm doing this trick now on the 3rd place.. Refactor?
            TypeElement mapperElement = mapperFactoryMethod.getReturnType().getTypeElement();
            MapperConfiguration mapperConfig = MapperConfiguration.getInstanceOn( mapperElement );
            String implName = GeneratedType.getImplementationName( mapperConfig.implementationName(),
                mapperElement.getSimpleName().toString() );
            mapperFactoryMethods.add( new MapperFactoryMethod( mapperFactoryMethod, implName ) );
        }

        MapperFactoryPrism mapperFactoryPrism =
            MapperFactoryPrism.getInstanceOn( info.getFactoryElement() );

        return new MapperFactory.Builder()
            .element( info.getFactoryElement() )
            .factoryMethods( mapperFactoryMethods )
            .elementUtils( context.getElementUtils() )
            .typeFactory( context.getTypeFactory() )
            .versionInformation( context.getVersionInformation() )
            .options( context.getOptions() )
            .implNameProperty( mapperFactoryPrism.implementationName() )
            .implPackageProperty( mapperFactoryPrism.implementationPackage() )
            .build();
    }

    @Override
    public int getPriority() {
        return 1000;
    }


}
