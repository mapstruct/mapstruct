/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import org.mapstruct.util.Experimental;

/**
 * A service provider interface for transforming name based value mappings.
 *
 * @author Filip Hrisafov
 * @since 1.4
 */
@Experimental("This SPI can have its signature changed in subsequent releases")
public interface EnumTransformationStrategy {

    /**
     * Initializes the enum transformation strategy with the MapStruct processing environment.
     *
     * @param processingEnvironment environment for facilities
     */
    default void init(MapStructProcessingEnvironment processingEnvironment) {

    }

    /**
     * The name of the strategy.
     *
     * @return the name of the strategy, never {@code null}
     */
    String getStrategyName();

    /**
     * Transform the given value by using the given {@code configuration}.
     *
     * @param value the value that should be transformed
     * @param configuration the configuration that should be used for the transformation
     *
     * @return the transformed value after applying the configuration
     */
    String transform(String value, String configuration);
}
