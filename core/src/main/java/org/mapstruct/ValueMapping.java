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
 * Configures the mapping of source constant value to target constant value.
 * <p>
 * Supported mappings are
 * <ol>
 * <li>Enumeration to Enumeration</li>
 * </ol>
 * <b>Example 1:</b>
 *
 * <pre><code class='java'>
 * public enum OrderType { RETAIL, B2B, C2C, EXTRA, STANDARD, NORMAL }
 *
 * public enum ExternalOrderType { RETAIL, B2B, SPECIAL, DEFAULT }
 *
 * &#64;ValueMapping(target = "SPECIAL", source = "EXTRA"),
 * &#64;ValueMapping(target = "DEFAULT", source = "STANDARD"),
 * &#64;ValueMapping(target = "DEFAULT", source = "NORMAL")
 * ExternalOrderType orderTypeToExternalOrderType(OrderType orderType);
 * </code>
 * Mapping result:
 * +---------------------+----------------------------+
 * | OrderType           | ExternalOrderType          |
 * +---------------------+----------------------------+
 * | null                | null                       |
 * | OrderType.EXTRA     | ExternalOrderType.SPECIAL  |
 * | OrderType.STANDARD  | ExternalOrderType.DEFAULT  |
 * | OrderType.NORMAL    | ExternalOrderType.DEFAULT  |
 * | OrderType.RETAIL    | ExternalOrderType.RETAIL   |
 * | OrderType.B2B       | ExternalOrderType.B2B      |
 * +---------------------+----------------------------+
 * </pre>
 *
 * <b>Example 2:</b>
 *
 * <pre><code class='java'>
 * &#64;ValueMapping( source = MappingConstants.NULL, target = "DEFAULT" ),
 * &#64;ValueMapping( source = "STANDARD", target = MappingConstants.NULL ),
 * &#64;ValueMapping( source = MappingConstants.ANY_REMAINING, target = "SPECIAL" )
 * ExternalOrderType orderTypeToExternalOrderType(OrderType orderType);
 * </code>
 * Mapping result:
 * +---------------------+----------------------------+
 * | OrderType           | ExternalOrderType          |
 * +---------------------+----------------------------+
 * | null                | ExternalOrderType.DEFAULT  |
 * | OrderType.STANDARD  | null                       |
 * | OrderType.RETAIL    | ExternalOrderType.RETAIL   |
 * | OrderType.B2B       | ExternalOrderType.B2B      |
 * | OrderType.NORMAL    | ExternalOrderType.SPECIAL  |
 * | OrderType.EXTRA     | ExternalOrderType.SPECIAL  |
 * +---------------------+----------------------------+
 * </pre>
 *
 * <b>Example 3:</b>
 *
 * MapStruct will <B>WARN</B> on incomplete mappings. However, if for some reason no match is found, an
 * {@link java.lang.IllegalStateException} will be thrown. This compile-time error can be avoided by
 * using {@link MappingConstants#THROW_EXCEPTION} for {@link ValueMapping#target()}. It will result an
 * {@link java.lang.IllegalArgumentException} at runtime.
 * <pre><code class='java'>
 * &#64;ValueMapping( source = "STANDARD", target = "DEFAULT" ),
 * &#64;ValueMapping( source = "C2C", target = MappingConstants.THROW_EXCEPTION )
 * ExternalOrderType orderTypeToExternalOrderType(OrderType orderType);
 * </code>
 * Mapping result:
 * {@link java.lang.IllegalArgumentException} with the error message:
 * Unexpected enum constant: C2C
 * </pre>
 *
 * @author Sjaak Derksen
 */
@Repeatable(ValueMappings.class)
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface ValueMapping {
    /**
     * The source value constant to use for this mapping.
     *
     * <p>
     * <b>Valid values:</b>
     * <ol>
     * <li>enum constant name</li>
     * <li>{@link MappingConstants#NULL}</li>
     * <li>{@link MappingConstants#ANY_REMAINING}</li>
     * <li>{@link MappingConstants#ANY_UNMAPPED}</li>
     * </ol>
     * <p>
     * <b>NOTE:</b>When using &lt;ANY_REMAINING&gt;, MapStruct will perform the normal name based mapping, in which
     * source is mapped to target based on enum identifier equality. Using &lt;ANY_UNMAPPED&gt; will not apply name
     * based mapping.
     *
     * @return The source value.
     */
    String source();

    /**
     * The target value constant to use for this mapping.
     *
     * <p>
     * <b>Valid values:</b>
     * <ol>
     * <li>enum constant name</li>
     * <li>{@link MappingConstants#NULL}</li>
     * <li>{@link MappingConstants#THROW_EXCEPTION}</li>
     * </ol>
     *
     * @return The target value.
     */
    String target();

}
