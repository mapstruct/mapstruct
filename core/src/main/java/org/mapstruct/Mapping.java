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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.mapstruct.control.MappingControl;

import static org.mapstruct.NullValueCheckStrategy.ON_IMPLICIT_CONVERSION;

/**
 * Configures the mapping of one bean attribute.
 * <p>
 * The name of the mapped attribute or constant is to be specified via {@link #target()}. For mapped bean attributes it
 * is assumed by default that the attribute has the same name in the source bean. Alternatively, one of
 * {@link #source()}, {@link #expression()} or {@link #constant()} can be specified to define the property source.
 * </p>
 * <p>
 * In addition, the attributes {@link #dateFormat()} and {@link #qualifiedBy()} may be used to further define the
 * mapping.
 * </p>
 *
 * <p>
 * <strong>Example 1:</strong> Implicitly mapping fields with the same name:
 * </p>
 * <pre><code class='java'>
 * // Both classes HumanDto and Human have property with name "fullName"
 * // properties with the same name will be mapped implicitly
 * &#64;Mapper
 * public interface HumanMapper {
 *    HumanDto toHumanDto(Human human)
 * }
 * </code></pre>
 * <pre><code class='java'>
 * // generates:
 * &#64;Override
 * public HumanDto toHumanDto(Human human) {
 *    humanDto.setFullName( human.getFullName() );
 *    // ...
 * }
 * </code></pre>
 *
 * <p><strong>Example 2:</strong> Mapping properties with different names</p>
 * <pre><code class='java'>
 * // We need map Human.companyName to HumanDto.company
 * // we can use &#64;Mapping with parameters {@link #source()} and {@link #target()}
 * &#64;Mapper
 * public interface HumanMapper {
 *    &#64;Mapping(source="companyName", target="company")
 *    HumanDto toHumanDto(Human human)
 * }
 * </code></pre>
 * <pre><code class='java'>
 * // generates:
 * &#64;Override
 * public HumanDto toHumanDto(Human human) {
 *     humanDto.setCompany( human.getCompanyName() );
 *      // ...
 * }
 * </code></pre>
 * <p>
 * <strong>Example 3:</strong> Mapping with expression
 * <b>IMPORTANT NOTE:</b> Now it works only for Java
 * </p>
 * <pre><code class='java'>
 * // We need map Human.name to HumanDto.countNameSymbols.
 * // we can use {@link #expression()} for it
 * &#64;Mapper
 * public interface HumanMapper {
 *    &#64;Mapping(target="countNameSymbols", expression="java(human.getName().length())")
 *    HumanDto toHumanDto(Human human)
 * }
 * </code></pre>
 * <pre><code class='java'>
 * // generates:
 *&#64;Override
 * public HumanDto toHumanDto(Human human) {
 *    humanDto.setCountNameSymbols( human.getName().length() );
 *    //...
 * }
 * </code></pre>
 * <p>
 * <strong>Example 4:</strong> Mapping to constant
 * </p>
 * <pre><code class='java'>
 * // We need map HumanDto.name to string constant "Unknown"
 * // we can use {@link #constant()} for it
 * &#64;Mapper
 * public interface HumanMapper {
 *    &#64;Mapping(target="name", constant="Unknown")
 *    HumanDto toHumanDto(Human human)
 * }
 * </code></pre>
 * <pre><code class='java'>
 * // generates
 * &#64;Override
 * public HumanDto toHumanDto(Human human) {
 *   humanDto.setName( "Unknown" );
 *   // ...
 * }
 * </code></pre>
 * <p>
 * <strong>Example 5:</strong> Mapping with default value
 * </p>
 * <pre><code class='java'>
 * // We need map Human.name to HumanDto.fullName, but if Human.name == null, then set value "Somebody"
 * // we can use {@link #defaultValue()} or {@link #defaultExpression()} for it
 * &#64;Mapper
 * public interface HumanMapper {
 *    &#64;Mapping(source="name", target="name", defaultValue="Somebody")
 *    HumanDto toHumanDto(Human human)
 * }
 * </code></pre>
 * <pre><code class='java'>
 * // generates
 * &#64;Override
 * public HumanDto toHumanDto(Human human) {
 *    if ( human.getName() != null ) {
 *       humanDto.setFullName( human.getName() );
 *    }
 *    else {
 *       humanDto.setFullName( "Somebody" );
 *    }
 *   // ...
 * }
 * </code></pre>
 *
 * @author Gunnar Morling
 */

@Repeatable(Mappings.class)
@Retention(RetentionPolicy.CLASS)
@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE })
public @interface Mapping {

    /**
     * The target name of the configured property as defined by the JavaBeans specification. The same target property
     * must not be mapped more than once.
     * <p>
     * If used to map an enum constant, the name of the constant member is to be given. In this case, several values
     * from the source enum may be mapped to the same value of the target enum.
     *
     * @return The target name of the configured property or enum constant
     */
    String target();

    /**
     * The source to use for this mapping. This can either be:
     * <ol>
     * <li>The source name of the configured property as defined by the JavaBeans specification.
     * <p>
     * This may either be a simple property name (e.g. "address") or a dot-separated property path (e.g. "address.city"
     * or "address.city.name"). In case the annotated method has several source parameters, the property name must
     * qualified with the parameter name, e.g. "addressParam.city".</li>
     * <li>When no matching property is found, MapStruct looks for a matching parameter name instead.</li>
     * <li>When used to map an enum constant, the name of the constant member is to be given.</li>
     * </ol>
     * This attribute can not be used together with {@link #constant()} or {@link #expression()}.
     *
     * @return The source name of the configured property or enum constant.
     */
    String source() default "";

    /**
     * A format string as processable by {@link SimpleDateFormat} if the attribute is mapped from {@code String} to
     * {@link Date} or vice-versa. Will be ignored for all other attribute types and when mapping enum constants.
     * <p>
     * If the {@link #locale()} is also specified, the format will consider the specified locale when processing
     * the date. Otherwise, the system's default locale will be used.
     *
     * @return A date format string as processable by {@link SimpleDateFormat}.
     * @see #locale()
     */
    String dateFormat() default "";

    /**
     * A format string as processable by {@link DecimalFormat} if the annotated method maps from a
     *  {@link Number} to a {@link String} or vice-versa. Will be ignored for all other element types.
     * <p>
     * If the {@link #locale()} is also specified, the number format will be applied in the context of the given locale.
     * Otherwise, the system's default locale will be used to process the number format.
     *
     * @return A decimal format string as processable by {@link DecimalFormat}.
     * @see #locale()
     */
    String numberFormat() default "";

    /**
     * Specifies the locale to be used when processing {@link #dateFormat()} or {@link #numberFormat()}.
     * <p>
     * The locale should be a plain tag representing the language, such as "en" for English, "de" for German, etc.
     * <p>
     * If no locale is specified, the system's default locale will be used.
     *
     * @return A string representing the locale to be used when formatting dates or numbers.
     */
    String locale() default "";

    /**
     * A constant {@link String} based on which the specified target property is to be set.
     * <p>
     * When the designated target property is of type:
     * </p>
     * <ol>
     * <li>primitive or boxed (e.g. {@code java.lang.Long}).
     * <p>
     * MapStruct checks whether the primitive can be assigned as valid literal to the primitive or boxed type.
     * </p>
     * <ul>
     * <li>
     * If possible, MapStruct assigns as literal.
     * </li>
     * <li>
     * If not possible, MapStruct will try to apply a user defined mapping method.
     * </li>
     * </ul>
     * </li>
     * <li>other
     * <p>
     * MapStruct handles the constant as {@code String}. The value will be converted by applying a matching method,
     * type conversion method or built-in conversion.
     * <p>
     * </li>
     * </ol>
     * <p>
     * You can use {@link #qualifiedBy()} or {@link #qualifiedByName()} to force the use of a conversion method
     * even when one would not apply. (e.g. {@code String} to {@code String})
     * </p>
     * <p>
     * This attribute can not be used together with {@link #source()}, {@link #defaultValue()},
     * {@link #defaultExpression()} or {@link #expression()}.
     *
     * @return A constant {@code String} constant specifying the value for the designated target property
     */
    String constant() default "";

    /**
     * An expression {@link String} based on which the specified target property is to be set.
     * <p>
     * Currently, Java is the only supported "expression language" and expressions must be given in form of Java
     * expressions using the following format: {@code java(<EXPRESSION>)}. For instance the mapping:
     * <pre><code>
     * &#64;Mapping(
     *     target = "someProp",
     *     expression = "java(new TimeAndFormat( s.getTime(), s.getFormat() ))"
     * )
     * </code></pre>
     * <p>
     * will cause the following target property assignment to be generated:
     * <p>
     * {@code targetBean.setSomeProp( new TimeAndFormat( s.getTime(), s.getFormat() ) )}.
     * <p>
     * Any types referenced in expressions must be given via their fully-qualified name. Alternatively, types can be
     * imported via {@link Mapper#imports()}.
     * <p>
     * This attribute can not be used together with {@link #source()}, {@link #defaultValue()},
     * {@link #defaultExpression()}, {@link #qualifiedBy()}, {@link #qualifiedByName()} or {@link #constant()}.
     *
     * @return An expression specifying the value for the designated target property
     */
    String expression() default "";

    /**
     * A defaultExpression {@link String} based on which the specified target property is to be set
     * if and only if the specified source property is null.
     * <p>
     * Currently, Java is the only supported "expression language" and expressions must be given in form of Java
     * expressions using the following format: {@code java(<EXPRESSION>)}. For instance the mapping:
     * <pre><code>
     * &#64;Mapping(
     *     target = "someProp",
     *     defaultExpression = "java(new TimeAndFormat( s.getTime(), s.getFormat() ))"
     * )
     * </code></pre>
     * <p>
     * will cause the following target property assignment to be generated:
     * <p>
     * {@code targetBean.setSomeProp( new TimeAndFormat( s.getTime(), s.getFormat() ) )}.
     * <p>
     * Any types referenced in expressions must be given via their fully-qualified name. Alternatively, types can be
     * imported via {@link Mapper#imports()}.
     * <p>
     * This attribute can not be used together with {@link #expression()}, {@link #defaultValue()}
     * or {@link #constant()}.
     *
     * @return An expression specifying a defaultValue for the designated target property if the designated source
     * property is null
     *
     * @since 1.3
     */
    String defaultExpression() default "";

    /**
     * Whether the property specified via {@link #target()} should be ignored by the generated mapping method or not.
     * This can be useful when certain attributes should not be propagated from source to target or when properties in
     * the target object are populated using a decorator and thus would be reported as unmapped target property by
     * default.
     * <p>
     * If you have multiple properties to ignore,
     * you can use the {@link Ignored} annotation instead and group them all at once.
     *
     * @return {@code true} if the given property should be ignored, {@code false} otherwise
     */
    boolean ignore() default false;

    /**
     * A qualifier can be specified to aid the selection process of a suitable mapper. This is useful in case multiple
     * mapping methods (hand written or generated) qualify and thus would result in an 'Ambiguous mapping methods found'
     * error. A qualifier is a custom annotation and can be placed on a hand written mapper class or a method.
     * <p>
     * Note that {@link #defaultValue()} usage will also be converted using this qualifier.
     *
     * @return the qualifiers
     * @see Qualifier
     */
    Class<? extends Annotation>[] qualifiedBy() default { };

    /**
     * String-based form of qualifiers; When looking for a suitable mapping method for a given property, MapStruct will
     * only consider those methods carrying directly or indirectly (i.e. on the class-level) a {@link Named} annotation
     * for each of the specified qualifier names.
     * <p>
     * Note that annotation-based qualifiers are generally preferable as they allow more easily to find references and
     * are safe for refactorings, but name-based qualifiers can be a less verbose alternative when requiring a large
     * number of qualifiers as no custom annotation types are needed.
     * <p>
     * Note that {@link #defaultValue()} usage will also be converted using this qualifier.
     *
     * @return One or more qualifier name(s)
     * @see #qualifiedBy()
     * @see Named
     */
    String[] qualifiedByName() default { };

    /**
     * A qualifier can be specified to aid the selection process of a suitable presence check method.
     * This is useful in case multiple presence check methods qualify and thus would result in an
     * 'Ambiguous presence check methods found' error.
     * A qualifier is a custom annotation and can be placed on a hand written mapper class or a method.
     * This is similar to the {@link #qualifiedBy()}, but it is only applied for {@link Condition} methods.
     *
     * @return the qualifiers
     * @see Qualifier
     * @see #qualifiedBy()
     * @since 1.5
     */
    Class<? extends Annotation>[] conditionQualifiedBy() default { };

    /**
     * String-based form of qualifiers for condition / presence check methods;
     * When looking for a suitable presence check method for a given property, MapStruct will
     * only consider those methods carrying directly or indirectly (i.e. on the class-level) a {@link Named} annotation
     * for each of the specified qualifier names.
     *
     * This is similar like {@link #qualifiedByName()} but it is only applied for {@link Condition} methods.
     * <p>
     *   Note that annotation-based qualifiers are generally preferable as they allow more easily to find references and
     *   are safe for refactorings, but name-based qualifiers can be a less verbose alternative when requiring a large
     *   number of qualifiers as no custom annotation types are needed.
     * </p>
     *
     *
     * @return One or more qualifier name(s)
     * @see #conditionQualifiedBy()
     * @see #qualifiedByName()
     * @see Named
     * @since 1.5
     */
    String[] conditionQualifiedByName() default { };

    /**
     * A conditionExpression {@link String} based on which the specified property is to be checked
     * whether it is present or not.
     * <p>
     * Currently, Java is the only supported "expression language" and expressions must be given in form of Java
     * expressions using the following format: {@code java(<EXPRESSION>)}. For instance the mapping:
     * <pre><code>
     * &#64;Mapping(
     *     target = "someProp",
     *     conditionExpression = "java(s.getAge() &#60; 18)"
     * )
     * </code></pre>
     * <p>
     * will cause the following target property assignment to be generated:
     * <pre><code>
     *     if (s.getAge() &#60; 18) {
     *         targetBean.setSomeProp( s.getSomeProp() );
     *     }
     * </code></pre>
     * <p>
     * Any types referenced in expressions must be given via their fully-qualified name. Alternatively, types can be
     * imported via {@link Mapper#imports()}.
     * <p>
     * This attribute can not be used together with {@link #expression()} or {@link #constant()}.
     *
     * @return An expression specifying a condition check for the designated property
     *
     * @since 1.5
     */
    String conditionExpression() default "";

    /**
     * Specifies the result type of the mapping method to be used in case multiple mapping methods qualify.
     *
     * @return the resultType to select
     */
    Class<?> resultType() default void.class;

    /**
     * One or more properties of the result type on which the mapped property depends. The generated method
     * implementation will invoke the setters of the result type ordered so that the given dependency relationship(s)
     * are satisfied. Useful in case one property setter depends on the state of another property of the result type.
     * <p>
     * An error will be raised in case a cycle in the dependency relationships is detected.
     *
     * @return the dependencies of the mapped property
     */
    String[] dependsOn() default { };

    /**
     * In case the source property is {@code null}, the provided default {@link String} value is set.
     * <p>
     * When the designated target property is of type:
     * </p>
     * <ol>
     * <li>primitive or boxed (e.g. {@code java.lang.Long}).
     * <p>
     * MapStruct checks whether the primitive can be assigned as valid literal to the primitive or boxed type.
     * </p>
     * <ul>
     * <li>
     * If possible, MapStruct assigns as literal.
     * </li>
     * <li>
     * If not possible, MapStruct will try to apply a user defined mapping method.
     * </li>
     * </ul>
     * <p>
     * </li>
     * <li>other
     * <p>
     * MapStruct handles the constant as {@code String}. The value will be converted by applying a matching method,
     * type conversion method or built-in conversion.
     * <p>
     * </li>
     * </ol>
     * <p>
     * This attribute can not be used together with {@link #constant()}, {@link #expression()}
     * or {@link #defaultExpression()}.
     *
     * @return Default value to set in case the source property is {@code null}.
     */
    String defaultValue() default "";

    /**
     * Determines when to include a null check on the source property value of a bean mapping.
     *
     * Can be overridden by the one on {@link MapperConfig}, {@link Mapper} or {@link BeanMapping}.
     *
     * @since 1.3
     *
     * @return strategy how to do null checking
     */
    NullValueCheckStrategy nullValueCheckStrategy() default ON_IMPLICIT_CONVERSION;

    /**
     * The strategy to be applied when the source property is {@code null} or not present. If no strategy is configured,
     * the strategy given via {@link MapperConfig#nullValuePropertyMappingStrategy()},
     * {@link BeanMapping#nullValuePropertyMappingStrategy()} or
     * {@link Mapper#nullValuePropertyMappingStrategy()} will be applied.
     *
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
     * Allows detailed control over the mapping process.
     *
     * @return the mapping control
     *
     * @since 1.4
     *
     * @see org.mapstruct.control.DeepClone
     * @see org.mapstruct.control.NoComplexMapping
     * @see org.mapstruct.control.MappingControl
     */
    Class<? extends Annotation> mappingControl() default MappingControl.class;

}
