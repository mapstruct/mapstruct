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
 * Configures the mappings of several bean attributes.
 * <p>
 * <strong>TIP: When using Java 8 or later, you can omit the @Mappings
 * wrapper annotation and directly specify several @Mapping annotations on one method.</strong>
 *
 * <p>These two examples are equal.
 * </p>
 * <pre><code class='java'>
 * // before Java 8
 * &#64;Mapper
 * public interface MyMapper {
 *     &#64;Mappings({
 *         &#64;Mapping(source = "first", target = "firstProperty"),
 *         &#64;Mapping(source = "second", target = "secondProperty")
 *     })
 *     HumanDto toHumanDto(Human human);
 * }
 * </code></pre>
 * <pre><code class='java'>
 * // Java 8 and later
 * &#64;Mapper
 * public interface MyMapper {
 *     &#64;Mapping(source = "first", target = "firstProperty"),
 *     &#64;Mapping(source = "second", target = "secondProperty")
 *     HumanDto toHumanDto(Human human);
 * }
 * </code></pre>
 *
 * @author Gunnar Morling
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface Mappings {

    /**
     * The configuration of the bean attributes.
     *
     * @return The configuration of the bean attributes.
     */
    Mapping[] value();
}
