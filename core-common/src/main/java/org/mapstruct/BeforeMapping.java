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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.util.Experimental;

/**
 * Marks a method to be invoked at the beginning of a generated mapping method. The method can be implemented in an
 * abstract mapper class or be declared in a type (class or interface) referenced in {@link Mapper#uses()} in order to
 * be used in a mapping method.
 * <p>
 * Only methods with return type {@code void} may be annotated with this annotation.
 * <p>
 * If the method has parameters, the method invocation is only generated if all parameters can be <em>assigned</em> by
 * the source or target parameters of the mapping method:
 * <ul>
 * <li>A parameter annotated with {@code @}{@link MappingTarget} is populated with the target instance of the mapping.
 * </li>
 * <li>A parameter annotated with {@code @}{@link TargetType} is populated with the target type of the mapping.</li>
 * <li>Any other parameter is populated with a source parameter of the mapping, whereas each source parameter is used
 * once at most.</li>
 * </ul>
 * If a <em>before-mapping</em> method does not contain a {@code @}{@link MappingTarget} parameter, it is invoked
 * directly at the beginning of the applicable mapping method. If it contains a {@code @}{@link MappingTarget}
 * parameter, the method is invoked after the target parameter has been initialized in the mapping method.
 * <p>
 * All <em>before-mapping</em> methods that can be applied to a mapping method will be used. Their order is determined
 * by their location of definition:
 * <ul>
 * <li>The order of methods within one type can not be guaranteed, as it depends on the compiler and the processing
 * environment implementation.</li>
 * <li>Methods declared in one type are used after methods declared in their super-type.</li>
 * <li>Methods implemented in the mapper itself are used before methods from types referenced in {@link Mapper#uses()}.
 * </li>
 * <li>Types referenced in {@link Mapper#uses()} are searched for <em>after-mapping</em> methods in the order specified
 * in the annotation.</li>
 * </ul>
 * <p>
 * Example:
 *
 * <pre>
 * <code>
 * &#64;BeforeMapping
 * public void calledWithoutArgs() {
 *     // ...
 * }
 *
 * &#64;BeforeMapping
 * public void calledWithSourceAndTargetType(SourceEntity anySource, &#64;TargetType Class&lt;?&gt; targetType) {
 *     // ...
 * }
 *
 * &#64;BeforeMapping
 * public void calledWithSourceAndTarget(Object anySource, &#64;MappingTarget TargetDto target) {
 *     // ...
 * }
 *
 * public abstract TargetDto toTargetDto(SourceEntity source);
 *
 * // generates:
 *
 * public TargetDto toTargetDto(SourceEntity source) {
 *     calledWithoutArgs();
 *     calledWithSourceAndTargetType( source, TargetDto.class );
 *
 *     if ( source == null ) {
 *         return null;
 *     }
 *
 *     TargetDto targetDto = new TargetDto();
 *
 *     calledWithSourceAndTarget( source, targetDto );
 *
 *     // actual mapping code
 *
 *     return targetDto;
 * }
 * </code>
 * </pre>
 *
 * @author Andreas Gudian
 * @see AfterMapping
 */
@Experimental
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface BeforeMapping {

}
