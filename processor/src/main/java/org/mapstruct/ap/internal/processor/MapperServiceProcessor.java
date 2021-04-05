/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor;

import java.io.IOException;
import javax.annotation.processing.Filer;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import org.mapstruct.ap.internal.gem.MappingConstantsGem;
import org.mapstruct.ap.internal.model.Decorator;
import org.mapstruct.ap.internal.model.GeneratedType;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.model.ServicesEntry;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.MapperOptions;
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
            String componentModel =
                MapperOptions.getInstanceOn( mapperTypeElement, context.getOptions() ).componentModel( );

            spiGenerationNeeded = MappingConstantsGem.ComponentModelGem.DEFAULT.equals( componentModel );
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
        ServicesEntry servicesEntry = getServicesEntry( model );

        createSourceFile( servicesEntry, modelWriter, filer );
    }

    private ServicesEntry getServicesEntry(Mapper mapper) {
        if ( mapper.getDecorator() != null ) {
            return getServicesEntry( mapper.getDecorator() );
        }

        return getServicesEntry( mapper.getMapperDefinitionType(), mapper );
    }

    private ServicesEntry getServicesEntry(Decorator decorator) {
        return getServicesEntry( decorator.getMapperType(), decorator );
    }

    private ServicesEntry getServicesEntry(Type mapperType, GeneratedType model) {
        String mapperName = mapperType.getName();
        String mapperPackageName = mapperType.getPackageName();

        return new ServicesEntry(mapperPackageName, mapperName,
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
