/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
 * By default beans are created during the mapping process with the default constructor.
 * This method allows to mark a method as a factory method to create beans.
 * The type of the factory method is determined by its return type. A factory is used
 * to create a bean if their types match.
 * <p>
 * The factory method can retrieve the mapping sources by specifying
 * them as parameters, exactly like mapping and lifecycle methods do. This allows the factory
 * method to create target beans based on source beans.
 * The use of source parameters opens up a variety of possibilities. For example,
 * a factory method for entities can look up an
 * EntityManager to check whether the entity already exists and then return its
 * managed instance.
 *
 * <pre>
 * {@literal @}ApplicationScoped // CDI component model
 * public class ReferenceMapper {
 *
 *     {@literal @}PersistenceContext
 *     private EntityManager em;
 *
 *     {@literal @}ObjectFactory
 *     public SomeEntity resolve(SomeDto dto) {
 *         SomeEntity entity = em.find(SomeEntity.class, dto.getId());
 *         return entity != null ? entity : new SomeEntity();
 *     }
 * }
 * </pre>
 *
 * If no such parameters
 * are provided, the use of this annotation is optional.
 *
 * <p>
 * If there are two factory methods, both serving the same type, one with no parameters
 * and one taking sources as input, then the one with the source parameters is favored.
 * If multiple factory method take sources as input, them the one is chosen which allows a
 * valid assignment from mapping source parameters to those factory parameters. If
 * there are multiple such factories, an ambiguity exception is thrown.
 * </p>
 *
 * @author Remo Meier
 * @since 1.2
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface ObjectFactory {

}
