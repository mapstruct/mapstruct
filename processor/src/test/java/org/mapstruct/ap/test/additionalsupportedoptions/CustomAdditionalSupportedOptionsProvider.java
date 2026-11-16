/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.additionalsupportedoptions;

// tag::documentation[]
import java.util.Collections;
import java.util.Set;

import org.mapstruct.ap.spi.AdditionalSupportedOptionsProvider;

public class CustomAdditionalSupportedOptionsProvider implements AdditionalSupportedOptionsProvider {

    @Override
    public Set<String> getAdditionalSupportedOptions() {
        return Collections.singleton( "myorg.custom.defaultNullEnumConstant" );
    }

}
// end::documentation[]
