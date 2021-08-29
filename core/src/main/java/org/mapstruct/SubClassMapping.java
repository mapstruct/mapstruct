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
 *    &#64;SubClassMapping (sourceClass = SourceSubClass.class,
 *                      targetClass = TargetSubClass.class)
 *    TargetParent mapParent(SourceParent parent);
 *
 *    TargetSubClass mapSubClass(SourceSubClass subInstant);
 * }
 * </code></pre>
 * Below follow examples of the implementation for the mapParent method.
 * <strong>Example 1:</strong> For parents that cannot be created. (e.g. abstract classes or interfaces)
 * <pre><code class='java'>
 * // generates
 * &#64;Override
 * public TargetParent mapParent(SourceParent parent) {
 *     if (parent instanceof SourceSubClass) {
 *         return mapSubClass( (SourceSubClass) parent );
 *     }
 *     else {
 *         throw new IllegalArgumentException("Not all subclasses are supported for this mapping. Missing for "
 *                    + parent.getClass());
 *     }
 * }
 * </code></pre>
 * <strong>Example 2:</strong> For parents that can be created. (e.g. normal classes or interfaces with
 * &#64;Mappper( uses = ObjectFactory.class ) )
 * <pre><code class='java'>
 * // generates
 * &#64;Override
 * public TargetParent mapParent(SourceParent parent) {
 *     TargetParent targetParent1;
 *     if (parent instanceof SourceSubClass) {
 *         targetParent1 = mapSubClass( (SourceSubClass) parent );
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
@Repeatable( value = SubClassMappings.class )
@Retention( RetentionPolicy.CLASS )
@Target( { ElementType.METHOD, ElementType.ANNOTATION_TYPE } )
public @interface SubClassMapping {
    /**
     * @return the source subclass to check for before using the default mapping as fallback.
     */
    Class<?> source();

    /**
     * @return the target subclass to map the sourceSubClass to.
     */
    Class<?> target();
}
