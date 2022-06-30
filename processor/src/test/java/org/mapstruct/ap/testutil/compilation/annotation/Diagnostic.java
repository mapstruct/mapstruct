/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.compilation.annotation;

import javax.tools.Diagnostic.Kind;

/**
 * An expected diagnostic of a compilation.
 *
 * @author Gunnar Morling
 */
public @interface Diagnostic {

    /**
     * The type for which the diagnostic was created.
     *
     * @return The type for which the diagnostic was created.
     */
    Class<?> type() default void.class;

    /**
     * The expected kind of diagnostic.
     *
     * @return The expected kind of diagnostic.
     */
    Kind kind();

    /**
     * The expected line number of the diagnostic.
     *
     * @return The expected line number of the diagnostic.
     */
    long line() default -1;

    /**
     * In case compilers report diagnostics on different lines this can be used as the alternative expected line number
     * of the diagnostic.
     * <p>
     * This should be used as a last resort when the compilers report the diagnostic on a wrong line.
     *
     * @return The alternative line number of the diagnostic.
     */
    long alternativeLine() default -1;

    /**
     * A message matching the exact expected message of the diagnostic.
     *
     * @return A message matching the exact expected message of the
     *         diagnostic.
     */
    String message() default "";

    /**
     * A regular expression matching the expected message of the diagnostic.
     * Wild-cards matching any character (".*") will be added to the beginning
     * and end of the given expression when applying it.
     *
     * @return A regular expression matching the expected message of the
     *         diagnostic.
     */
    String messageRegExp() default ".*";
}
