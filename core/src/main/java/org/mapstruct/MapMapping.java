/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.mapstruct.control.MappingControl;

/**
 * Configures the mapping between two map types, e.g. Map&lt;String, String&gt; and Map&lt;Long, Date&gt;.
 *
 * <p>
 * <strong>Example</strong>:
 * </p>
 * <pre><code class='java'>
 * &#64;Mapper
 * public interface SimpleMapper {
 *       &#64;MapMapping(valueDateFormat = "dd.MM.yyyy")
 *       Map&lt;String, String&gt; longDateMapToStringStringMap(Map&lt;Long, Date&gt; source);
 * }
 * </code></pre>
 * <pre><code class='java'>
 * // generates
 * public class SimpleMapperImpl implements SimpleMapper {
 *      &#64;Override
 *      public Map&lt;String, String&gt; longDateMapToStringStringMap(Map&lt;Long, Date&gt; source) } {
 *          Map&lt;String, String&gt; map = new HashMap&lt;String, String&gt;(); }
 *          for ( java.util.Map.Entry&lt;Long, Date&gt; entry : source.entrySet() ) } {
 *              String key = new DecimalFormat( "" ).format( entry.getKey() );
 *              String value = new SimpleDateFormat( "dd.MM.yyyy" ).format( entry.getValue() );
 *              map.put( key, value );
 *          }
 *          // ...
 *      }
 * }
 * </code></pre>
 *
 * <p><strong>NOTE:</strong> at least one element needs to be specified</p>
 *
 * @author Gunnar Morling
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface MapMapping {

    /**
     * A format string as processable by {@link SimpleDateFormat} if the annotated method maps from a map with key type
     * {@code String} to an map with key type {@link Date} or vice-versa. Will be ignored for all other key types.
     * <p>
     * If the {@link #locale()} is specified, the format will consider the specified locale when processing the date.
     * Otherwise, the system's default locale will be used.
     *
     * @return A date format string as processable by {@link SimpleDateFormat}.
     * @see #locale()
     */
    String keyDateFormat() default "";

    /**
     * A format string as processable by {@link SimpleDateFormat} if the annotated method maps from a map with value
     * type {@code String} to an map with value type {@link Date} or vice-versa. Will be ignored for all other value
     * types.
     * <p>
     * If the {@link #locale()} is specified, the format will consider the specified locale when processing the date.
     * Otherwise, the system's default locale will be used.
     *
     * @return A date format string as processable by {@link SimpleDateFormat}.
     * @see #locale()
     */
    String valueDateFormat() default "";

    /**
     * A format string as processable by {@link DecimalFormat} if the annotated method maps from a
     *  {@link Number} to a {@link String} or vice-versa. Will be ignored for all other key types.
     * <p>
     * If the {@link #locale()} is specified, the number format will be applied in the context of the given locale.
     * Otherwise, the system's default locale will be used.
     *
     * @return A decimal format string as processable by {@link DecimalFormat}.
     * @see #locale()
     */
    String keyNumberFormat() default "";

    /**
     * A format string as processable by {@link DecimalFormat} if the annotated method maps from a
     *  {@link Number} to a {@link String} or vice-versa. Will be ignored for all other value types.
     * <p>
     * If the {@link #locale()} is specified, the number format will be applied in the context of the given locale.
     * Otherwise, the system's default locale will be used.
     *
     * @return A decimal format string as processable by {@link DecimalFormat}.
     * @see #locale()
     */
    String valueNumberFormat() default "";

    /**
     * Specifies the locale to be used when processing {@link SimpleDateFormat} or {@link DecimalFormat} for key or
     * value mappings in maps. The locale should be a plain tag representing the language, such as "en" for English,
     * "de" for German, etc.
     * <p>
     * If no locale is specified, the system's default locale will be used.
     *
     * @return A string representing the locale to be used when formatting dates or numbers in maps.
     */
    String locale() default "";

    /**
     * A key value qualifier can be specified to aid the selection process of a suitable mapper. This is useful in
     * case multiple mappers (hand written of internal) qualify and result in an 'Ambiguous mapping methods found'
     * error.
     *
     * A qualifier is a custom annotation and can be placed on either a hand written mapper class or a method.
     *
     * @return the qualifiers
     * @see Qualifier
     */
    Class<? extends Annotation>[] keyQualifiedBy() default { };

    /**
     * String-based form of qualifiers; When looking for a suitable mapping method to map this map mapping method's key
     * type, MapStruct will only consider those methods carrying directly or indirectly (i.e. on the class-level) a
     * {@link Named} annotation for each of the specified qualifier names.
     * <p>
     * Note that annotation-based qualifiers are generally preferable as they allow more easily to find references and
     * are safe for refactorings, but name-based qualifiers can be a less verbose alternative when requiring a large
     * number of qualifiers as no custom annotation types are needed.
     *
     * @return One or more qualifier name(s)
     * @see #keyQualifiedBy()
     * @see Named
     */
    String[] keyQualifiedByName() default { };


    /**
     * A value qualifier can be specified to aid the selection process of a suitable mapper for the values in the map.
     * This is useful in case multiple mappers (hand written of internal) qualify and result in an 'Ambiguous mapping
     * methods found' error.
     * <p>
     * A qualifier is a custom annotation and can be placed on either a hand written mapper class or a method.
     *
     * @return the qualifiers
     * @see Qualifier
     */
    Class<? extends Annotation>[] valueQualifiedBy() default { };

    /**
     * String-based form of qualifiers; When looking for a suitable mapping method to map this map mapping method's
     * value type, MapStruct will only consider those methods carrying directly or indirectly (i.e. on the class-level)
     * a {@link Named} annotation for each of the specified qualifier names.
     * <p>
     * Note that annotation-based qualifiers are generally preferable as they allow more easily to find references and
     * are safe for refactorings, but name-based qualifiers can be a less verbose alternative when requiring a large
     * number of qualifiers as no custom annotation types are needed.
     *
     * @return One or more qualifier name(s)
     * @see #valueQualifiedBy()
     * @see Named
     */
    String[] valueQualifiedByName() default { };

    /**
     * Specifies the type of the key to be used in the result of the mapping method in case multiple mapping
     * methods qualify.
     *
     *
     * @return the resultType to select
     */
    Class<?> keyTargetType() default void.class;

    /**
     * Specifies the type of the value to be used in the result of the mapping method in case multiple mapping
     * methods qualify.
     *
     *
     * @return the resultType to select
     */
    Class<?> valueTargetType() default void.class;


    /**
     * The strategy to be applied when {@code null} is passed as source value to this map mapping. If no
     * strategy is configured, the strategy given via {@link MapperConfig#nullValueMappingStrategy()} or
     * {@link Mapper#nullValueMappingStrategy()} will be applied, using {@link NullValueMappingStrategy#RETURN_NULL}
     * by default.
     *
     * @return The strategy to be applied when {@code null} is passed as source value to the methods of this mapping.
     */
    NullValueMappingStrategy nullValueMappingStrategy() default NullValueMappingStrategy.RETURN_NULL;

    /**
     * Allows detailed control over the key mapping process.
     *
     * @return the mapping control
     *
     * @since 1.4

     * @see org.mapstruct.control.DeepClone
     * @see org.mapstruct.control.NoComplexMapping
     * @see org.mapstruct.control.MappingControl
     */
    Class<? extends Annotation> keyMappingControl() default MappingControl.class;


    /**
     * Allows detailed control over the value mapping process.
     *
     * @return the mapping control
     *
     * @since 1.4
     *
     * @see org.mapstruct.control.DeepClone
     * @see org.mapstruct.control.NoComplexMapping
     * @see org.mapstruct.control.MappingControl
     */
    Class<? extends Annotation> valueMappingControl() default MappingControl.class;

}
