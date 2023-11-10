/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.util.Experimental;

/**
 * Configures the mapping to handle hierarchy of the source type.
 * <p>
 * The subclass to be mapped is to be specified via {@link #source()}.
 * The subclass to map to is to be specified via {@link #target()}.
 * </p>
 * <p>
 * This annotation can be combined with &#64;Mapping annotations.
 * </p>
 *
 * <pre><code class='java'>
 * &#64;Mapper
 * public interface MyMapper {
 *    &#64;SubclassMapping (target = TargetSubclass.class, source = SourceSubclass.class)
 *    TargetParent mapParent(SourceParent parent);
 *
 *    TargetSubclass mapSubclass(SourceSubclass subInstant);
 * }
 * </code></pre>
 * Below follow examples of the implementation for the mapParent method.
 * <strong>Example 1:</strong> For parents that cannot be created. (e.g. abstract classes or interfaces)
 * <pre><code class='java'>
 * // generates
 * &#64;Override
 * public TargetParent mapParent(SourceParent parent) {
 *     if (parent instanceof SourceSubclass) {
 *         return mapSubclass( (SourceSubclass) parent );
 *     }
 *     else {
 *         throw new IllegalArgumentException("Not all subclasses are supported for this mapping. Missing for "
 *                    + parent.getClass());
 *     }
 * }
 * </code></pre>
 * <strong>Example 2:</strong> For parents that can be created. (e.g. normal classes or interfaces with
 * &#64;Mapper( uses = ObjectFactory.class ) )
 * <pre><code class='java'>
 * // generates
 * &#64;Override
 * public TargetParent mapParent(SourceParent parent) {
 *     TargetParent targetParent1;
 *     if (parent instanceof SourceSubclass) {
 *         targetParent1 = mapSubclass( (SourceSubclass) parent );
 *     }
 *     else {
 *         targetParent1 = new TargetParent();
 *         // ...
 *     }
 * }
 * </code></pre>
 *
 * @author Ben Zegveld
 * @since 1.5
 */
@Repeatable(value = SubclassMappings.class)
@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Experimental
public @interface SubclassMapping {
    /**
     * @return the source subclass to check for before using the default mapping as fallback.
     */
    Class<?> source();

    /**
     * @return the target subclass to map the source to.
     */
    Class<?> target();

    /**
     * A qualifier can be specified to aid the selection process of a suitable mapper. This is useful in case multiple
     * mapping methods (hand written or generated) qualify and thus would result in an 'Ambiguous mapping methods found'
     * error. A qualifier is a custom annotation and can be placed on a hand written mapper class or a method.
     *
     * @return the qualifiers
     * @see Qualifier
     */
    Class<? extends Annotation>[] qualifiedBy() default {};

    /**
     * String-based form of qualifiers; When looking for a suitable mapping method for a given property, MapStruct will
     * only consider those methods carrying directly or indirectly (i.e. on the class-level) a {@link Named} annotation
     * for each of the specified qualifier names.
     * <p>
     * Note that annotation-based qualifiers are generally preferable as they allow more easily to find references and
     * are safe for refactorings, but name-based qualifiers can be a less verbose alternative when requiring a large
     * number of qualifiers as no custom annotation types are needed.
     *
     * @return One or more qualifier name(s)
     * @see #qualifiedBy()
     * @see Named
     */
    String[] qualifiedByName() default {};
}
