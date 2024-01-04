/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows the definition of Javadoc comments in the MapStruct <code>Mapper</code> generated class.
 *
 * <p>The annotation provides support for the usual Javadoc comments elements by defining analogous attributes.</p>
 *
 *
 * <p>Please, note that at least one of these attributes must be specified.</p>
 *
 * <p>
 * For instance, the following definition;
 * </p>
 * <pre><code class='java'>
 * &#64;Javadoc(
 *     value = "This is the description",
 *     authors = { "author1", "author2" },
 *     deprecated = "Use {&#64;link OtherMapper} instead",
 *     since = "0.1"
 * )
 * </code></pre>
 *
 * <p>
 * will generate:
 * </p>
 *
 * <pre><code class='java'>
 * /**
 * * This is the description
 * *
 * * &#64;author author1
 * * &#64;author author2
 * *
 * * &#64;deprecated Use {&#64;link OtherMapper} instead
 * * &#64;since 0.1
 * *&#47;
 * </code></pre>
 *
 * <p>
 * The entire Javadoc comment block can be passed directly:
 * </p>
 * <pre><code class='java'>
 * &#64;Javadoc("This is the description\n"
 *            + "\n"
 *            + "&#64;author author1\n"
 *            + "&#64;author author2\n"
 *            + "\n"
 *            + "&#64;deprecated Use {&#64;link OtherMapper} instead\n"
 *            + "&#64;since 0.1\n"
 * )
 * </code></pre>
 *
 * <pre><code class='java'>
 * // or using Text Blocks
 * &#64;Javadoc(
 *     """
 *     This is the description
 *
 *     &#64;author author1
 *     &#64;author author2
 *
 *     &#64;deprecated Use {&#64;link OtherMapper} instead
 *     &#64;since 0.1
 *     """
 * )
 * </code></pre>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Javadoc {
    /**
     * Main Javadoc comment text block.
     *
     * @return Main Javadoc comment text block.
     */
    String value() default "";

    /**
     * List of authors of the code that it is being documented.
     * <p>
     * It will generate a list of the Javadoc tool comment element <code>&#64;author</code>
     * with the different values and in the order provided.
     *
     * @return array of javadoc authors.
     */
    String[] authors() default { };

    /**
     * Specifies that the functionality that is being documented is deprecated.
     * <p>
     * Corresponds to the <code>&#64;deprecated</code> Javadoc tool comment element.
     *
     * @return Deprecation message about the documented functionality
     */
    String deprecated() default "";

    /**
     * Specifies the version since the functionality that is being documented is available.
     * <p>
     * Corresponds to the <code>&#64;since</code> Javadoc tool comment element.
     *
     * @return Version since the functionality is available
     */
    String since() default "";
}
