/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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

/**
 * This annotation marks a method as a <em>factory method</em> to create beans.
 * <p>
 * By default beans are created during the mapping process with the default constructor. If a factory method with a
 * return type that is assignable to the required object type is present, then the factory method is used instead.
 * <p>
 * Factory methods can be defined without parameters, with an {@code @}{@link TargetType} parameter, a {@code @}
 * {@link Context} parameter, or with the mapping source parameter. If any of those parameters are defined, then
 * the mapping method that is supposed to use the factory method needs to be declared with an assignable result type,
 * assignable context parameter, and/or assignable source types.
 * <p>
 * <strong>Note:</strong> the usage of this annotation is <em>optional</em> if no source parameters are part of the
 * signature, i.e. it is declared without parameters or only with {@code @}{@link TargetType} and/or {@code @}
 * {@link Context}.
 * <p>
 * <strong>Example:</strong> Using a factory method for entities to check whether the entity already exists in the
 * EntityManager and then returns the managed instance:
 *
 * <pre>
 * <code>
 * &#64;ApplicationScoped // CDI component model
 * public class ReferenceMapper {
 *
 *     &#64;PersistenceContext
 *     private EntityManager em;
 *
 *     &#64;ObjectFactory
 *     public &lt;T extends AbstractEntity&gt; T resolve(AbstractDto sourceDto, &#64;TargetType Class&lt;T&gt; type) {
 *         T entity = em.find( type, sourceDto.getId() );
 *         return entity != null ? entity : type.newInstance();
 *     }
 * }
 * </code>
 * </pre>
 * <p>
 * If there are two factory methods, both serving the same type, one with no parameters and one taking sources as input,
 * then the one with the source parameters is favored. If there are multiple such factories, an ambiguity error is
 * shown.
 *
 * @author Remo Meier
 * @since 1.2
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface ObjectFactory {

}
