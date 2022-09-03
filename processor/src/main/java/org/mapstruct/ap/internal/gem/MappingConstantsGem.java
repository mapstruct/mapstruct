/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.gem;

/**
 * Gem for the enum {@link org.mapstruct.MappingConstants}
 *
 * @author Sjaak Derksen
 */
public final class MappingConstantsGem {

    private MappingConstantsGem() {
    }

    public static final String NULL = "<NULL>";

    public static final String ANY_REMAINING = "<ANY_REMAINING>";

    public static final String ANY_UNMAPPED = "<ANY_UNMAPPED>";

    public static final String THROW_EXCEPTION = "<THROW_EXCEPTION>";

    public static final String SUFFIX_TRANSFORMATION = "suffix";

    public static final String STRIP_SUFFIX_TRANSFORMATION = "stripSuffix";

    public static final String PREFIX_TRANSFORMATION = "prefix";

    public static final String STRIP_PREFIX_TRANSFORMATION = "stripPrefix";

    public static final String CASE_TRANSFORMATION = "case";

    /**
     * Gem for the class {@link org.mapstruct.MappingConstants.ComponentModel}
     *
     */
    public final class ComponentModelGem {

        private ComponentModelGem() {
        }

        public static final String DEFAULT = "default";

        public static final String CDI = "cdi";

        public static final String SPRING = "spring";

        public static final String JSR330 = "jsr330";

        public static final String JAKARTA = "jakarta";

        public static final String JAKARTA_CDI = "jakarta-cdi";
     }

}
