/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Elements;

import org.mapstruct.ap.internal.version.VersionInformation;

public interface ElementUtils extends Elements {

     static ElementUtils create(ProcessingEnvironment processingEnvironment, VersionInformation info ) {
        if ( info.isEclipseJDTCompiler() ) {
            return new EclipseElementUtilsDecorator( processingEnvironment );
        }
        else {
            return new JavacElementUtilsDecorator( processingEnvironment );
        }
    }
}
