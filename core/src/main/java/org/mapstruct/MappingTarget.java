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
 * Declares a parameter of a mapping method to be the target of the mapping.
 * <p>
 * Not more than one parameter can be declared as {@code MappingTarget}.
 * <p>
 * <b>NOTE:</b> The parameter passed as a mapping target <b>must</b> not be {@code null}.
 *
 * <p>
 * <strong>Example 1:</strong> Update exist bean without return value
 * </p>
 * <pre><code class='java'>
 * &#64;Mapper
 * public interface HumanMapper {
 *     void updateHuman(HumanDto humanDto, @MappingTarget Human human);
 * }
 * </code></pre>
 * <pre><code class='java'>
 * // generates
 * &#64;Override
 * public void updateHuman(HumanDto humanDto, Human human) {
 *     human.setName( humanDto.getName() );
 *     // ...
 * }
 * </code></pre>
 * <p>
 * <strong>Example 2:</strong> Update exist bean and return it
 * </p>
 * <pre><code class='java'>
 * &#64;Mapper
 * public interface HumanMapper {
 *     Human updateHuman(HumanDto humanDto, @MappingTarget Human human);
 * }
 * </code></pre>
 * // generates:
 * <pre><code class='java'>
 * &#64;Override
 * public Human updateHuman(HumanDto humanDto, Human human) {
 *     // ...
 *     human.setName( humanDto.getName() );
 *     return human;
 * }
 *</code></pre>
 *
 *
 * @author Andreas Gudian
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.CLASS)
public @interface MappingTarget {
}
