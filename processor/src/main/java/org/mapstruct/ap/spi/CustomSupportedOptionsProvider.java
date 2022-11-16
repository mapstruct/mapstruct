/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import java.util.Set;

/**
 * Provider for any supported options required for custom SPI implementations.
 * The resolved values are retrieved from {@link MapStructProcessingEnvironment#getCustomOptions()}.
 */
public interface CustomSupportedOptionsProvider {

    /**
     * Returns the supported options required for custom SPI implementations.
     *
     * @return the custom supported options.
     */
    Set<String> getCustomSupportedOptions();

}
