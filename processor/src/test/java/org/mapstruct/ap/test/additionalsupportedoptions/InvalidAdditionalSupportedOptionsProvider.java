/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.additionalsupportedoptions;

import java.util.Collections;
import java.util.Set;

import org.mapstruct.ap.spi.AdditionalSupportedOptionsProvider;

public class InvalidAdditionalSupportedOptionsProvider implements AdditionalSupportedOptionsProvider {

    @Override
    public Set<String> getAdditionalSupportedOptions() {
        return Collections.singleton( "mapstruct.custom.test" );
    }

}
