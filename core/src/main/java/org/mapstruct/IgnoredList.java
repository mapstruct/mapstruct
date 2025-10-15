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
 * Configures the ignored list for several bean attributes.
 * <p>
 * <strong>TIP: When using Java 8 or later, you can omit the {@code @IgnoredList}
 * wrapper annotation and directly specify several {@code @Ignored} annotations on one method.</strong>
 *
 * <p>These two examples are equal.
 * </p>
 * <pre><code class='java'>
 * // before Java 8
 * &#64;Mapper
 * public interface MyMapper {
 *     &#64;IgnoredList({
 *         &#64;Ignored(targets = { "firstProperty" } ),
 *         &#64;Ignored(targets = { "secondProperty" } )
 *     })
 *     HumanDto toHumanDto(Human human);
 * }
 * </code></pre>
 * <pre><code class='java'>
 * // Java 8 and later
 * &#64;Mapper
 * public interface MyMapper {
 *     &#64;Ignored(targets = { "firstProperty" } ),
 *     &#64;Ignored(targets = { "secondProperty" } )
 *     HumanDto toHumanDto(Human human);
 * }
 * </code></pre>
 *
 * @author Ivashin Aleksey
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE })
public @interface IgnoredList {

    /**
     * The configuration of the bean attributes.
     *
     * @return The configuration of the bean attributes.
     */
    Ignored[] value();
}
