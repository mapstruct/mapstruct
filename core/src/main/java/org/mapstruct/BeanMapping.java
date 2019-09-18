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

import static org.mapstruct.NullValueCheckStrategy.ON_IMPLICIT_CONVERSION;

/**
 * Configures the mapping between two bean types.
 * <p>
 * Either {@link #resultType()}, {@link #qualifiedBy()} or {@link #nullValueMappingStrategy()} must be specified.
 * </p>
 * <p><strong>Example:</strong> Determining the result type</p>
 * <pre><code class='java'>
 * // When result types have an inheritance relation, selecting either mapping method {@link Mapping} or factory method
 * // {@link BeanMapping} can be become ambiguous. Parameter  {@link BeanMapping#resultType()} can be used.
 * public class FruitFactory {
 *     public Apple createApple() {
 *         return new Apple();
 *     }
 *     public Orange createOrange() {
 *         return new Orange();
 *     }
 * }
 * &#64;Mapper(uses = FruitFactory.class)
 * public interface FruitMapper {
 *     &#64;BeanMapping(resultType = Apple.class)
 *     Fruit toFruit(FruitDto fruitDto);
 * }
 * </code></pre>
 * <pre><code class='java'>
 * // generates
 * public class FruitMapperImpl implements FruitMapper {
 *      &#64;Override
 *      public Fruit toFruit(FruitDto fruitDto) {
 *          Apple fruit = fruitFactory.createApple();
 *          // ...
 *      }
 * }
 * </code></pre>
 *
 * @author Sjaak Derksen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface BeanMapping {

    /**
     * Specifies the result type of the factory method to be used in case several factory methods qualify.
     *
     * @return the resultType to select
     */
    Class<?> resultType() default void.class;

    /**
     * A qualifier can be specified to aid the selection process of a suitable factory method or filtering applicable
     * {@code @}{@link BeforeMapping} / {@code @}{@link AfterMapping} methods. This is useful in case multiple factory
     * method (hand written of internal) qualify and result in an 'Ambiguous factory methods' error.
     * <p>
     * A qualifier is a custom annotation and can be placed on either a hand written mapper class or a method.
     *
     * @return the qualifiers
     * @see Qualifier
     */
    Class<? extends Annotation>[] qualifiedBy() default {};

    /**
     * Similar to {@link #qualifiedBy()}, but used in combination with {@code @}{@link Named} in case no custom
     * qualifier annotation is defined.
     *
     * @return the qualifiers
     * @see Named
     */
    String[] qualifiedByName() default { };

    /**
     * The strategy to be applied when {@code null} is passed as source bean argument value to this bean mapping. If no
     * strategy is configured, the strategy given via {@link MapperConfig#nullValueMappingStrategy()} or
     * {@link Mapper#nullValueMappingStrategy()} will be applied, using {@link NullValueMappingStrategy#RETURN_NULL}
     * by default.
     *
     * @return The strategy to be applied when {@code null} is passed as source value to the methods of this mapping.
     */
    NullValueMappingStrategy nullValueMappingStrategy() default NullValueMappingStrategy.RETURN_NULL;

    /**
     * The strategy to be applied when a source bean property is {@code null} or not present. If no strategy is
     * configured, the strategy given via {@link MapperConfig#nullValuePropertyMappingStrategy()} or
     * {@link Mapper#nullValuePropertyMappingStrategy()} will be applied,
     * {@link NullValuePropertyMappingStrategy#SET_TO_NULL} will be used by default.
     *
     * @since 1.3
     *
     * @return The strategy to be applied when {@code null} is passed as source property value or the source property
     * is not present.
     */
    NullValuePropertyMappingStrategy nullValuePropertyMappingStrategy()
        default NullValuePropertyMappingStrategy.SET_TO_NULL;

    /**
     * Determines when to include a null check on the source property value of a bean mapping.
     *
     * Can be overridden by the one on {@link MapperConfig}, {@link Mapper}  or {@link Mapping}.
     *
     * @return strategy how to do null checking
     */
    NullValueCheckStrategy nullValueCheckStrategy() default ON_IMPLICIT_CONVERSION;

    /**
     * Default ignore all mappings. All mappings have to be defined manually. No automatic mapping will take place. No
     * warning will be issued on missing target properties.
     *
     * @return The ignore strategy (default false).
     *
     * @since 1.3
     */
    boolean ignoreByDefault() default false;

    /**
     * Unmapped source properties to be ignored. This could be used when {@link ReportingPolicy#WARN}
     * or {@link ReportingPolicy#ERROR} is used for {@link Mapper#unmappedSourcePolicy()} or
     * {@link MapperConfig#unmappedSourcePolicy()}. Listed properties will be ignored when composing the unmapped
     * source properties report.
     * <p>
     * <b>NOTE</b>: This does not support ignoring nested source properties
     *
     * @return The source properties that should be ignored when performing a report
     *
     * @since 1.3
     */
    String[] ignoreUnmappedSourceProperties() default {};

    /**
     * The information that should be used for the builder mappings. This can be used to define custom build methods
     * for the builder strategy that one uses.
     *
     * If no builder is defined the builder given via {@link MapperConfig#builder()} or {@link Mapper#builder()}
     * will be applied.
     * <p>
     * NOTE: In case no builder is defined here, in {@link Mapper} or {@link MapperConfig} and there is a single
     * build method, then that method would be used.
     * <p>
     * If the builder is defined and there is a single method that does not match the name of the finisher than
     * a compile error will occurs
     *
     * @return the builder information for the method level
     *
     * @since 1.3
     */
    Builder builder() default @Builder;
}
