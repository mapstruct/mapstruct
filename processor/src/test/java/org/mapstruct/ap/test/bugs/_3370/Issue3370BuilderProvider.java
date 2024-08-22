/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3370;

import org.mapstruct.ap.spi.BuilderInfo;
import org.mapstruct.ap.spi.BuilderProvider;
import org.mapstruct.ap.spi.ImmutablesBuilderProvider;

import javax.lang.model.element.TypeElement;
import java.util.Set;

public class Issue3370BuilderProvider extends ImmutablesBuilderProvider implements BuilderProvider {


    /**
     * Calls super class with immutable name if builder is generated
     * for Item for the first time processing of Item class
     * Calls super class directly with type element otherwise
     */
    @Override
    protected BuilderInfo findBuilderInfo( TypeElement typeElement, Set<TypeElement> processedTypeElements ) {
        if ( processedTypeElements.contains( super.asImmutableElement( typeElement ) ) ||
                !typeElement.getQualifiedName().toString().endsWith( ( ".Item" ) ) ) {
            return super.findBuilderInfo( typeElement, processedTypeElements );
        }
        return super.findBuilderInfo( super.asImmutableElement( typeElement ), processedTypeElements );
    }

}
