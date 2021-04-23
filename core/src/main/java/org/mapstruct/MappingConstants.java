/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

/**
 * Contains all constants defined in the mapping process.
 *
 * @author Sjaak Derksen
 */
public final class MappingConstants {

    private MappingConstants() {
    }

    /**
     * In an {@link ValueMapping} this represents a {@code null} source or target.
     */
    public static final String NULL = "<NULL>";

    /**
     * In an {@link ValueMapping} this represents any source that is not already mapped by either a defined mapping or
     * by means of name based mapping.
     *
     * NOTE: The value is only applicable to {@link ValueMapping#source()} and not to {@link ValueMapping#target()}.
     */
    public static final String ANY_REMAINING = "<ANY_REMAINING>";

    /**
     * In an {@link ValueMapping} this represents any source that is not already mapped by a defined mapping.
     *
     * NOTE: The value is only applicable to {@link ValueMapping#source()} and not to {@link ValueMapping#target()}.
     *
     */
    public static final String ANY_UNMAPPED = "<ANY_UNMAPPED>";

    /**
     * In an {@link ValueMapping} this represents any target that will be mapped to an
     * {@link java.lang.IllegalArgumentException} which will be thrown at runtime.
     * <p>
     * NOTE: The value is only applicable to {@link ValueMapping#target()} and not to {@link ValueMapping#source()}.
     */
    public static final String THROW_EXCEPTION = "<THROW_EXCEPTION>";

    /**
     * In an {@link EnumMapping} this represent the enum transformation strategy that adds a suffix to the source enum.
     *
     * @since 1.4
     */
    public static final String SUFFIX_TRANSFORMATION = "suffix";

    /**
     * In an {@link EnumMapping} this represent the enum transformation strategy that strips a suffix from the source
     * enum.
     *
     * @since 1.4
     */
    public static final String STRIP_SUFFIX_TRANSFORMATION = "stripSuffix";

    /**
     * In an {@link EnumMapping} this represent the enum transformation strategy that adds a prefix to the source enum.
     *
     * @since 1.4
     */
    public static final String PREFIX_TRANSFORMATION = "prefix";

    /**
     * In an {@link EnumMapping} this represent the enum transformation strategy that strips a prefix from the source
     * enum.
     *
     * @since 1.4
     */
    public static final String STRIP_PREFIX_TRANSFORMATION = "stripPrefix";

    /**
    * Specifies the component model constants to which the generated mapper should adhere.
    * It can be used with the annotation {@link Mapper#componentModel()} or {@link MapperConfig#componentModel()}
    *
    * <p>
    * <strong>Example:</strong>
    * </p>
    * <pre><code class='java'>
    * // Spring component model
    * &#64;Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
    * </code></pre>
    *
    * @since 1.5.0
    */
    public static final class ComponentModel {

        private ComponentModel() {
        }

        /**
         * The mapper uses no component model, instances are typically retrieved
         * via {@link org.mapstruct.factory.Mappers#getMapper(java.lang.Class)}
         *
         */
        public static final String DEFAULT = "default";

        /**
         * The generated mapper is an application-scoped CDI bean and can be retrieved via @Inject
         */
        public static final String CDI = "cdi";

        /**
         * The generated mapper is a Spring bean and can be retrieved via @Autowired
         *
         */
        public static final String SPRING = "spring";

        /**
         * The generated mapper is annotated with @javax.inject.Named and @Singleton, and can be retrieved via @Inject
         *
         */
        public static final String JSR330 = "jsr330";

        /**
         * The generated mapper is annotated with @org.osgi.service.component.annotations.Component,
         * and can be retrieved via @org.osgi.service.component.annotations.Reference
         *
         */
        public static final String OSGI_DS = "osgi-ds";

    }

}
