/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import org.mapstruct.ap.internal.model.GeneratedType;
import org.mapstruct.ap.internal.model.Mapper;
import org.mapstruct.ap.internal.writer.ModelWriter;

/**
 * A {@link ModelElementProcessor} which creates a Java source file representing
 * the given {@link Mapper} object, unless the given mapper type is erroneous.
 *
 * @author Gunnar Morling
 */
public class MapperRenderingProcessor implements ModelElementProcessor<Mapper, Mapper> {

    @Override
    public Mapper process(ProcessorContext context, TypeElement mapperTypeElement, Mapper mapper) {
        if ( !context.isErroneous() ) {
            writeToSourceFile( context.getFiler(), mapper, mapperTypeElement );
            return mapper;
        }

        return null;
    }

    private void writeToSourceFile(Filer filer, Mapper model, TypeElement originatingElement) {
        ModelWriter modelWriter = new ModelWriter();

        createSourceFile( model, modelWriter, filer, originatingElement );

        if ( model.getDecorator() != null ) {
            createSourceFile( model.getDecorator(), modelWriter, filer, originatingElement );
        }
    }

    private void createSourceFile(GeneratedType model, ModelWriter modelWriter, Filer filer,
                                  TypeElement originatingElement) {
        String fileName = "";
        if ( model.hasPackageName() ) {
            fileName += model.getPackageName() + ".";
        }
        fileName += model.getName();

        JavaFileObject sourceFile;
        try {
            sourceFile = filer.createSourceFile( fileName, originatingElement );
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }

        modelWriter.writeModel( sourceFile, model );
    }

    @Override
    public int getPriority() {
        return 9999;
    }
}
