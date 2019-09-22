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
 * Constructs a set of value (constant) mappings.
 * <p>
 * <strong>TIP: When using Java 8 or later, you can omit the @ValueMappings
 * wrapper annotation and directly specify several @ValueMapping annotations on one method.</strong>
 *
 * <p>These two examples are equal</p>
 * <pre><code class='java'>
 * // before Java 8
 * &#64;Mapper
 * public interface GenderMapper {
 *     &#64;ValueMappings({
 *         &#64;ValueMapping(source = "MALE", target = "M"),
 *         &#64;ValueMapping(source = "FEMALE", target = "F")
 *     })
 *     GenderDto mapToDto(Gender gender);
 * }
 * </code></pre>
 * <pre><code class='java'>
 * //Java 8 and later
 * &#64;Mapper
 * public interface GenderMapper {
 *     &#64;ValueMapping(source = "MALE", target = "M"),
 *     &#64;ValueMapping(source = "FEMALE", target = "F")
 *     GenderDto mapToDto(Gender gender);
 * }
 * </code></pre>
 *
 * @author Sjaak Derksen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface ValueMappings {

    ValueMapping[] value();

}
