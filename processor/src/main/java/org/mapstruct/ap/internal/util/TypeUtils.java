/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.version.VersionInformation;

public interface TypeUtils extends Types {

     static TypeUtils create(ProcessingEnvironment processingEnvironment, VersionInformation info ) {
        if ( info.isEclipseJDTCompiler() ) {
            return new EclipseTypeUtilsDecorator( processingEnvironment );
        }
        else {
            return new JavacTypeUtilsDecorator( processingEnvironment );
        }
    }
}
