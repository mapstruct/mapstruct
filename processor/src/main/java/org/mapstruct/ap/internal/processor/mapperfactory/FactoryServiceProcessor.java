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

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import org.mapstruct.ap.internal.model.ServicesEntry;
import org.mapstruct.ap.internal.model.mapperfactory.MapperFactory;
import org.mapstruct.ap.internal.writer.ModelWriter;

/**
 * A {@link FactoryElementProcessor} which creates files in the {@code META-INF/services}
 * hierarchy for classes with custom implementation class or package name.
 *

 * @author Sjaak Derksen.
 */
public class FactoryServiceProcessor  implements FactoryElementProcessor<MapperFactory, Void> {

    @Override
    public Void process(ProcessorContext context, FactoryGenerationInfo factoryInfo, MapperFactory sourceModel) {

        writeToSourceFile( context.getFiler(), sourceModel );
        return null;
    }

    @Override
    public int getPriority() {
        return 10000;
    }

    private void writeToSourceFile(Filer filer, MapperFactory model) {
        ModelWriter modelWriter = new ModelWriter();
        ServicesEntry servicesEntry = new ServicesEntry( model.getInterfacePackage(), model.getInterfaceName(),
            model.getPackageName(), model.getName() );

        createSourceFile( servicesEntry, modelWriter, filer );
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
