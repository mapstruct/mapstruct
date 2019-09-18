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
 * Declares a parameter of a custom mapping method to be populated with the target type of the mapping.
 * <p>
 * Not more than one parameter can be declared as {@code TargetType} and that parameter needs to be of type
 * {@link Class} (may be parameterized), or a super-type of it.
 *
 * <p>
 * <strong>Example:</strong>
 * </p>
 * <pre><code class='java'>
 * public class EntityFactory {
 *    public &lt;T extends BaseEntity&gt; T createEntity(@TargetType Class&lt;T&gt; entityClass) {
 *         return // ... custom factory logic
 *    }
 * }
 * &#64;Mapper(uses = EntityFactory.class)
 * public interface CarMapper {
 *     CarEntity carDtoToCar(CarDto dto);
 * }
 * </code></pre>
 * <pre><code class='java'>
 * // generates
 * public class CarMapperImpl implements CarMapper {
 *     private final EntityFactory entityFactory = new EntityFactory();
 *     &#64;Override
 *     public CarEntity carDtoToCar(CarDto dto) {
 *         if ( dto == null ) {
 *             return null;
 *         }
 *         CarEntity carEntity = entityFactory.createEntity( CarEntity.class );
 *         return carEntity;
 *     }
 * }
 * </code></pre>
 *
 * @author Andreas Gudian
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.CLASS)
public @interface TargetType {
}
