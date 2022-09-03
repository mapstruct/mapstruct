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
 * This annotation marks a method as a <em>presence check method</em> to check check for presence in beans.
 * <p>
 * By default bean properties are checked against {@code null} or using a presence check method in the source bean.
 * If a presence check method is available then it will be used instead.
 * <p>
 * Presence check methods have to return {@code boolean}.
 * The following parameters are accepted for the presence check methods:
 *     <ul>
 *         <li>The parameter with the value of the source property.
 *         e.g. the value given by calling {@code getName()} for the name property of the source bean</li>
 *         <li>The mapping source parameter</li>
 *         <li>{@code @}{@link Context} parameter</li>
 *         <li>{@code @}{@link TargetPropertyName} parameter</li>
 *     </ul>
 *
 * <strong>Note:</strong> The usage of this annotation is <em>mandatory</em>
 * for a method to be considered as a presence check method.
 *
 * <pre><code>
 * public class PresenceCheckUtils {
 *
 *   &#64;Condition
 *   public static boolean isNotEmpty(String value) {
 *      return value != null &#38;&#38; !value.isEmpty();
 *   }
 * }
 *
 * &#64;Mapper(uses = PresenceCheckUtils.class)
 * public interface MovieMapper {
 *
 *     MovieDto map(Movie movie);
 * }
 * </code></pre>
 *
 * The following implementation of {@code MovieMapper} will be generated:
 *
 * <pre>
 * <code>
 * public class MovieMapperImpl implements MovieMapper {
 *
 *     &#64;Override
 *     public MovieDto map(Movie movie) {
 *         if ( movie == null ) {
 *             return null;
 *         }
 *
 *         MovieDto movieDto = new MovieDto();
 *
 *         if ( PresenceCheckUtils.isNotEmpty( movie.getTitle() ) ) {
 *             movieDto.setTitle( movie.getTitle() );
 *         }
 *
 *         return movieDto;
 *     }
 * }
 * </code>
 * </pre>
 *
 * @author Filip Hrisafov
 * @since 1.5
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.CLASS)
public @interface Condition {

}
