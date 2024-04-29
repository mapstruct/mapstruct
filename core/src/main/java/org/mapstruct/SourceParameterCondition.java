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
 * This annotation marks a method as a <em>check method</em> to check if a source parameter needs to be mapped.
 * <p>
 * By default, source parameters are checked against {@code null}, unless they are primitives.
 * <p>
 * Check methods have to return {@code boolean}.
 * The following parameters are accepted for the presence check methods:
 *     <ul>
 *         <li>The mapping source parameter</li>
 *         <li>{@code @}{@link Context} parameter</li>
 *     </ul>
 *
 * <strong>Note:</strong> The usage of this annotation is <em>mandatory</em>
 * for a method to be considered as a source check method.
 *
 * <pre><code class='java'>
 * public class PresenceCheckUtils {
 *
 *   &#64;SourceParameterCondition
 *   public static boolean isDefined(Car car) {
 *      return car != null &#38;&#38; car.getId() != null;
 *   }
 * }
 *
 * &#64;Mapper(uses = PresenceCheckUtils.class)
 * public interface CarMapper {
 *
 *     CarDto map(Car car);
 * }
 * </code></pre>
 *
 * The following implementation of {@code CarMapper} will be generated:
 *
 * <pre><code class='java'>
 * public class CarMapperImpl implements CarMapper {
 *
 *     &#64;Override
 *     public CarDto map(Car car) {
 *         if ( !PresenceCheckUtils.isDefined( car ) ) {
 *             return null;
 *         }
 *
 *         CarDto carDto = new CarDto();
 *
 *         carDto.setId( car.getId() );
 *         // ...
 *
 *         return carDto;
 *     }
 * }
 * </code></pre>
 *
 * @author Filip Hrisafov
 * @since 1.6
 * @see Condition @Condition
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.CLASS)
@Condition(appliesTo = ConditionStrategy.SOURCE_PARAMETERS)
public @interface SourceParameterCondition {

}
