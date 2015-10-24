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

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import org.mapstruct.ap.internal.model.GeneratedType;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.model.ServicesEntry;
import org.mapstruct.ap.internal.option.OptionsHelper;
import org.mapstruct.ap.internal.util.MapperConfiguration;
import org.mapstruct.ap.internal.writer.ModelWriter;

/**
 * A {@link ModelElementProcessor} which creates files in the {@code META-INF/services}
 * hierarchy for classes with custom implementation class or package name.
 *
 * Service files will only be generated for mappers with the default component model
 * unless force using the {@code mapstruct.alwaysGenerateServicesFile} option.
 *
 * @author Christophe Labouisse on 12/07/2015.
 */
public class MapperServiceProcessor  implements ModelElementProcessor<Mapper, Void> {
    @Override
    public Void process(ProcessorContext context, TypeElement mapperTypeElement, Mapper mapper) {
        boolean spiGenerationNeeded;

        if ( context.getOptions().isAlwaysGenerateSpi() ) {
            spiGenerationNeeded = true;
        }
        else {
            String componentModel = MapperConfiguration.getInstanceOn( mapperTypeElement ).componentModel();
            String effectiveComponentModel = OptionsHelper.getEffectiveComponentModel(
                    context.getOptions(),
                    componentModel
            );

            spiGenerationNeeded = "default".equals( effectiveComponentModel );
        }

        if ( !context.isErroneous() && spiGenerationNeeded && mapper.hasCustomImplementation() ) {
            writeToSourceFile( context.getFiler(), mapper );
        }
        return null;
    }

    @Override
    public int getPriority() {
        return 10000;
    }

    private void writeToSourceFile(Filer filer, Mapper model) {
        ModelWriter modelWriter = new ModelWriter();
        ServicesEntry servicesEntry = getServicesEntry( model.getDecorator() == null ? model : model.getDecorator() );

        createSourceFile( servicesEntry, modelWriter, filer );
    }

    private ServicesEntry getServicesEntry(GeneratedType model) {
        String mapperName = model.getInterfaceName() != null ? model.getInterfaceName() : model.getSuperClassName();

        return new ServicesEntry(model.getInterfacePackage(), mapperName,
                                 model.getPackageName(), model.getName());
    }

    private void createSourceFile(ServicesEntry model, ModelWriter modelWriter, Filer filer) {
        String fileName = model.getPackageName() + "." + model.getName();

        FileObject sourceFile;
        try {
            sourceFile = filer.createResource( StandardLocation.CLASS_OUTPUT, "", "META-INF/services/" + fileName );
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }

        modelWriter.writeModel( sourceFile, model );
    }
}
