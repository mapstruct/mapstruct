package org.mapstruct;

public enum BuilderProvider {
    /**
     * https://projectlombok.org/features/Builder
     */
    LOMBOK,

    /**
     * https://github.com/google/auto/tree/master/value
     */
    AUTOVALUE,
    /**
     * https://immutables.github.io/
     */
    IMMUTABLES,

    /**
     * This approach will look on the classpath for the existence of {@link #LOMBOK}, {@link #AUTOVALUE}, or
     * {@link #IMMUTABLES}
     */
    AUTODETECT,

    /**
     * The default provider looks for an SPI implementation - if none is found, Mapstruct will use a convention-based
     * default implementation.
     */
    DEFAULT;
}
