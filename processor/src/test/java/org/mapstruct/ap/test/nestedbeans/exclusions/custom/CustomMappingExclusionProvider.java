/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.exclusions.custom;

// tag::documentation[]

import java.util.regex.Pattern;

import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;

import org.mapstruct.ap.spi.MappingExclusionProvider;

// end::documentation[]
/**
 * @author Filip Hrisafov
 */
// tag::documentation[]
public class CustomMappingExclusionProvider implements MappingExclusionProvider {
    private static final Pattern JAVA_JAVAX_PACKAGE = Pattern.compile( "^javax?\\..*" );

    @Override
    public boolean isExcluded(TypeElement typeElement) {
        // end::documentation[]
        //For some reason the eclipse compiler does not work when you try to do NestedTarget.class
        // tag::documentation[]
        Name name = typeElement.getQualifiedName();
        return name.length() != 0 && ( JAVA_JAVAX_PACKAGE.matcher( name ).matches() ||
            name.toString().equals( "org.mapstruct.ap.test.nestedbeans.exclusions.custom.Target.NestedTarget" ) );
    }
}
// end::documentation[]
