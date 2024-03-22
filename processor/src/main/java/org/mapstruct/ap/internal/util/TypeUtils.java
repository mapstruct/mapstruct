/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import androidx.room.compiler.processing.XProcessingEnv;
import org.mapstruct.ap.internal.version.VersionInformation;

public interface TypeUtils extends Types {

     static TypeUtils create(XProcessingEnv processingEnvironment, VersionInformation info ) {
        if ( info.isEclipseJDTCompiler() ) {
            return new EclipseTypeUtilsDecorator( processingEnvironment );
        }
        else {
            return new JavacTypeUtilsDecorator( processingEnvironment );
        }
    }

    boolean isSubtypeErased(TypeMirror t1, TypeMirror t2);
}
