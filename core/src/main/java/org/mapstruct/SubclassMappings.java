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

import org.mapstruct.util.Experimental;

/**
 * Configures the SubclassMappings of several subclasses.
 * <p>
 * <strong>TIP: When using java 8 or later, you can omit the @SubclassMappings
 * Wrapper annotation and directly specify several @SubclassMapping annotations
 * on one method.</strong>
 * </p>
 * <p>These two examples are equal.
 * </p>
 * <pre><code class='java'>
 * // before java 8
 * &#64;Mapper
 * public interface MyMapper {
 *     &#64;SubclassMappings({
 *         &#64;SubclassMapping(source = FirstSub.class, target = FirstTargetSub.class),
 *         &#64;SubclassMapping(source = SecondSub.class, target = SecondTargetSub.class)
 *     })
 *     ParentTarget toParentTarget(Parent parent);
 * }
 * </code></pre>
 * <pre><code class='java'>
 * // java 8 and later
 * &#64;Mapper
 * public interface MyMapper {
 *     &#64;SubclassMapping(source = First.class, target = FirstTargetSub.class),
 *     &#64;SubclassMapping(source = SecondSub.class, target = SecondTargetSub.class)
 *     ParentTarget toParentTarget(Parent parent);
 * }
 * </code></pre>
 *
 * @author Ben Zegveld
 * @since 1.5
 */
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.CLASS)
@Experimental
public @interface SubclassMappings {

    /**
     * @return the subclassMappings to apply.
     */
    SubclassMapping[] value();

}
