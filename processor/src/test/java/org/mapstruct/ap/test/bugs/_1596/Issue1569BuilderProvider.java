/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1596;

import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;

import org.mapstruct.ap.spi.BuilderInfo;
import org.mapstruct.ap.spi.BuilderProvider;
import org.mapstruct.ap.spi.ImmutablesBuilderProvider;

public class Issue1569BuilderProvider extends ImmutablesBuilderProvider implements BuilderProvider {

    @Override
    protected BuilderInfo findBuilderInfo(TypeElement typeElement) {
        Name name = typeElement.getQualifiedName();
        if ( name.toString().endsWith( ".Item" ) ) {
            BuilderInfo info = findBuilderInfoForImmutables( typeElement );
            if ( info != null ) {
                return info;
            }
        }

        return super.findBuilderInfo( typeElement );
    }

    protected BuilderInfo findBuilderInfoForImmutables(TypeElement typeElement) {
        TypeElement immutableElement = asImmutableElement( typeElement );
        if ( immutableElement != null ) {
            return super.findBuilderInfo( immutableElement );
        }
        return null;
    }
}
