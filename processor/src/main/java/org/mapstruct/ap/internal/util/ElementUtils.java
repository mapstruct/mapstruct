/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import java.util.List;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

import androidx.room.compiler.processing.XProcessingEnv;
import org.mapstruct.ap.internal.version.VersionInformation;

public interface ElementUtils extends Elements {

     static ElementUtils create(XProcessingEnv processingEnvironment, VersionInformation info,
                                TypeElement mapperElement) {
        if ( info.isEclipseJDTCompiler() ) {
            return new EclipseElementUtilsDecorator( processingEnvironment, mapperElement );
        }
        else {
            return new JavacElementUtilsDecorator( processingEnvironment, mapperElement );
        }
    }

    /**
     * Finds all executable elements within the given type element, including executable elements defined in super
     * classes and implemented interfaces. Methods defined in {@link java.lang.Object},
     * implementations of {@link java.lang.Object#equals(Object)} and private methods are ignored
     *
     * @param element the element to inspect
     * @return the executable elements usable in the type
     */
     List<ExecutableElement> getAllEnclosedExecutableElements(TypeElement element);

    /**
     * Finds all variable elements within the given type element, including variable
     * elements defined in super classes and implemented interfaces and including the fields in the .
     *
     * @param element      the element to inspect
     * @return the executable elements usable in the type
     */
     List<VariableElement> getAllEnclosedFields(TypeElement element);
}
