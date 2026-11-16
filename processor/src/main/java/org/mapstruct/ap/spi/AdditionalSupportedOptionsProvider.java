/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import java.util.Set;

/**
 * Provider for any additional supported options required for custom SPI implementations.
 * The resolved values are retrieved from {@link MapStructProcessingEnvironment#getOptions()}.
 */
public interface AdditionalSupportedOptionsProvider {

    /**
     * Returns the supported options required for custom SPI implementations.
     *
     * @return the additional supported options.
     */
    Set<String> getAdditionalSupportedOptions();

}
