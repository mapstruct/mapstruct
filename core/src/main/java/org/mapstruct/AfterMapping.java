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
 * Marks a method to be invoked at the end of a generated mapping method, right before the last {@code return} statement
 * of the mapping method. The method can be implemented in an abstract mapper class, be declared in a type (class or
 * interface) referenced in {@link Mapper#uses()}, or in a type used as {@code @}{@link Context} parameter in order to
 * be used in a mapping method.
 * <p>
 * The method invocation is only generated if the return type of the method (if non-{@code void}) is assignable to the
 * return type of the mapping method and all parameters can be <em>assigned</em> by the available source, target or
 * context parameters of the mapping method:
 * <ul>
 * <li>A parameter annotated with {@code @}{@link MappingTarget} is populated with the target instance of the mapping.
 * </li>
 * <li>A parameter annotated with {@code @}{@link TargetType} is populated with the target type of the mapping.</li>
 * <li>Parameters annotated with {@code @}{@link Context} are populated with the context parameters of the mapping
 * method.</li>
 * <li>Any other parameter is populated with a source parameter of the mapping.</li>
 * </ul>
 * <p>
 * For non-{@code void} methods, the return value of the method invocation is returned as the result of the mapping
 * method if it is not {@code null}.
 * <p>
 * All <em>after-mapping</em> methods that can be applied to a mapping method will be used. {@code @}{@link Qualifier} /
 * {@code @}{@link Named} can be used to filter the methods to use.
 * <p>
 * The order of the method invocation is determined by their location of definition:
 * <ol>
 * <li>Methods declared on {@code @}{@link Context} parameters, ordered by the parameter order.</li>
 * <li>Methods implemented in the mapper itself.</li>
 * <li>Methods from types referenced in {@link Mapper#uses()}, in the order of the type declaration in the annotation.
 * </li>
 * <li>Methods declared in one type are used after methods declared in their super-type</li>
 * </ol>
 * <em>Important:</em> the order of methods declared within one type can not be guaranteed, as it depends on the
 * compiler and the processing environment implementation.
 * <p>
 * Example:
 *
 * <pre>
 * <code>
 * &#64;AfterMapping
 * public void calledWithoutArgs() {
 *     // ...
 * }
 *
 * &#64;AfterMapping
 * public void calledWithSourceAndTargetType(SourceEntity anySource, &#64;TargetType Class&lt;?&gt; targetType) {
 *     // ...
 * }
 *
 * &#64;AfterMapping
 * public void calledWithSourceAndTarget(Object anySource, &#64;MappingTarget TargetDto target) {
 *     // ...
 * }
 *
 * public abstract TargetDto toTargetDto(SourceEntity source);
 *
 * // generates:
 *
 * public TargetDto toTargetDto(SourceEntity source) {
 *     if ( source == null ) {
 *         return null;
 *     }
 *
 *     TargetDto targetDto = new TargetDto();
 *
 *     // actual mapping code
 *
 *     calledWithoutArgs();
 *     calledWithSourceAndTargetType( source, TargetDto.class );
 *     calledWithSourceAndTarget( source, targetDto );
 *
 *     return targetDto;
 * }
 * </code>
 * </pre>
 *
 * @author Andreas Gudian
 * @see BeforeMapping
 * @see Context
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface AfterMapping {

}
