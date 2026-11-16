/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.additionalsupportedoptions;

// tag::documentation[]
import javax.lang.model.element.TypeElement;

import org.mapstruct.ap.spi.DefaultEnumMappingStrategy;
import org.mapstruct.ap.spi.MapStructProcessingEnvironment;

public class UnknownEnumMappingStrategy extends DefaultEnumMappingStrategy {

    private String defaultNullEnumConstant;

    @Override
    public void init(MapStructProcessingEnvironment processingEnvironment) {
        super.init( processingEnvironment );
        defaultNullEnumConstant = processingEnvironment.getOptions().get( "myorg.custom.defaultNullEnumConstant" );
    }

    @Override
    public String getDefaultNullEnumConstant(TypeElement enumType) {
        return defaultNullEnumConstant;
    }
}
// end::documentation[]
