/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3370;

import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;

import org.mapstruct.ap.spi.BuilderInfo;
import org.mapstruct.ap.spi.BuilderProvider;
import org.mapstruct.ap.spi.ImmutablesBuilderProvider;

public class Issue3370BuilderProvider extends ImmutablesBuilderProvider implements BuilderProvider {

    @Override
    protected BuilderInfo findBuilderInfoForImmutables(TypeElement typeElement) {
        Name name = typeElement.getQualifiedName();
        if ( name.toString().endsWith( ".Item" ) ) {
            return super.findBuilderInfo( asImmutableElement( typeElement ) );
        }
        return super.findBuilderInfo( typeElement );
    }
}
