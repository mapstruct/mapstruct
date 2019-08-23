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
 * Advises the code generator to apply all the {@link Mapping}s from an inverse mapping method to the annotated method
 * as well. An inverse mapping method is a method which has the annotated method's source type as target type (return
 * type or indicated through a parameter annotated with {@link MappingTarget}) and the annotated method's target type as
 * source type.
 * <p>
 * Any mappings given on the annotated method itself are added to those mappings inherited from the inverse method. In
 * case of a conflict local mappings take precedence over inherited mappings.
 * <p>
 * If more than one matching inverse method exists, the name of the method to inherit the configuration from must be
 * specified via {@link #name()}
 *
 * <p>
 * <strong>Example</strong>
 * </p>
 * <pre>
 * &#64;Mapper
 * public interface HumanMapper {
 *      Human toHuman(HumanDto humanDto);
 *      &#64;InheritInverseConfiguration
 *      HumanDto toHumanDto(Human human);
 * }
 * </pre>
 * <pre>
 * // generates
 * public class HumanMapperImpl implements HumanMapper {
 *      &#64;Override
 *      public Human toHuman(HumanDto humanDto) {
 *          if ( humanDto == null ) {
 *              return null;
 *           }
 *          Human human = new Human();
 *          human.setName( humanDto.getName() );
 *          return human;
 *      }
 *      &#64;Override
 *      public HumanDto toHumanDto(Human human) {
 *          if ( human == null ) {
 *              return null;
 *          }
 *          HumanDto humanDto = new HumanDto();
 *          humanDto.setName( human.getName() );
 *          return humanDto;
 *      }
 * }
 * </pre>
 *
 * @author Sjaak Derksen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface InheritInverseConfiguration {

    /**
     * The name of the inverse mapping method to inherit the mappings from. Needs only to be specified in case more than
     * one inverse method with matching source and target type exists.
     *
     * @return The name of the inverse mapping method to inherit the mappings from.
     */
    String name() default "";
}
