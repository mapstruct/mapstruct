/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Configures the ignored of one bean attribute.
 *
 * <p>
 * The name all attributes of for ignored is to be specified via {@link #targets()}.
 * </p>
 *
 * <p>
 * <strong>Example 1:</strong> Implicitly mapping fields with the same name:
 * </p>
 *
 * <pre><code class='java'>
 * // We need ignored Human.name and Human.lastName
 * // we can use &#64;Ignored with parameters "name", "lastName" {@link #targets()}
 * &#64;Mapper
 * public interface HumanMapper {
 *    &#64;Ignored( targets = { "name", "lastName" } )
 *    HumanDto toHumanDto(Human human)
 * }
 * </code></pre>
 * <pre><code class='java'>
 * // generates:
 * &#64;Override
 * public HumanDto toHumanDto(Human human) {
 *    humanDto.setFullName( human.getFullName() );
 *    // ...
 * }
 * </code></pre>
 *
 * @author Ivashin Aleksey
 */
@Repeatable(IgnoredList.class)
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface Ignored {

    /**
     * Whether the specified properties should be ignored by the generated mapping method.
     * This can be useful when certain attributes should not be propagated from source to target or when properties in
     * the target object are populated using a decorator and thus would be reported as unmapped target property by
     * default.
     *
     * @return The target names of the configured properties that should be ignored
     */
    String[] targets();

    /**
     * The prefix that should be applied to all the properties specified via {@link #targets()}.
     *
     * @return The target prefix to be applied to the defined properties
     */
    String prefix() default "";

}
