/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Configures the mapping of one bean attribute or enum constant.
 * <p>
 * The name of the mapped attribute or constant is to be specified via {@link #target()}. For mapped bean attributes it
 * is assumed by default that the attribute has the same name in the source bean. Alternatively, one of
 * {@link #source()}, {@link #expression()} or {@link #constant()} can be specified to define the property source.
 * <p>
 * In addition, the attributes {@link #dateFormat()} and {@link #qualifiedBy()} may be used to further define the
 * mapping.
 *
 * @author Gunnar Morling
 */
@Repeatable(Mappings.class)
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
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
     *
     * @return A date format string as processable by {@link SimpleDateFormat}.
     */
    String dateFormat() default "";

    /**
     * A constant {@link String} based on which the specified target property is to be set. If the designated target
     * property is not of type {@code String}, the value will be converted by applying a matching conversion method or
     * built-in conversion.
     * <p>
     * This attribute can not be used together with {@link #source()}, {@link #defaultValue()} or {@link #expression()}.
     *
     * @return A constant {@code String} constant specifying the value for the designated target property
     */
    String constant() default "";

    /**
     * An expression {@link String} based on which the specified target property is to be set.
     * <p>
     * Currently, Java is the only supported "expression language" and expressions must be given in form of Java
     * expressions using the following format: {@code java(<EXPRESSION>)}. For instance the mapping
     * <p>
     *
     * <pre>
     * {@code @Mapping(
     *     target = "someProp",
     *     expression = "java(new TimeAndFormat( s.getTime(), s.getFormat() ))"
     * )}
     * </pre>
     * <p>
     * will cause the following target property assignment to be generated:
     * <p>
     * {@code targetBean.setSomeProp( new TimeAndFormat( s.getTime(), s.getFormat() ) )}.
     * <p>
     * Any types referenced in expressions must be given via their fully-qualified name. Alternatively, types can be
     * imported via {@link Mapper#imports()}.
     * <p>
     * This attribute can not be used together with {@link #source()}, {@link #defaultValue()} or {@link #constant()}.
     *
     * @return An expression specifying the value for the designated target property
     */
    String expression() default "";

    /**
     * Whether the property specified via {@link #target()} should be ignored by the generated mapping method or not.
     * This can be useful when certain attributes should not be propagated from source or target or when properties in
     * the target object are populated using a decorator and thus would be reported as unmapped target property by
     * default.
     *
     * @return {@code true} if the given property should be ignored, {@code false} otherwise
     */
    boolean ignore() default false;

    /**
     * A qualifier can be specified to aid the selection process of a suitable mapper. This is useful in case multiple
     * mapping methods (hand written or generated) qualify and thus would result in an 'Ambiguous mapping methods found'
     * error. A qualifier is a custom annotation and can be placed on a hand written mapper class or a method.
     *
     * @return the qualifiers
     */
    Class<? extends Annotation>[] qualifiedBy() default { };

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
     * In case the source property is {@code null}, the provided default {@link String} value is set. If the designated
     * target property is not of type {@code String}, the value will be converted by applying a matching conversion
     * method or built-in conversion.
     * <p>
     * This attribute can not be used together with {@link #constant()} or {@link #expression()}.
     *
     * @return Default value to set in case the source property is {@code null}.
     */
    String defaultValue() default "";
}
