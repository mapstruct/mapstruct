/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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

/**
 * Strategy for dealing with {@code null} values passed to mapping methods.
 *
 * @author Sjaak Derksen
 */
public enum NullValueMappingStrategy {

    /**
     * If {@code null} is passed to a mapping method, {@code null} will be returned. That's the default behavior if no
     * alternative strategy is configured globally, for given mapper or method.
     */
    RETURN_NULL,

    /**
     * If {@code null} is passed to a mapping method, a default value will be returned. The value depends on the kind of
     * the annotated method:
     * <ul>
     * <li>For bean mapping method the target type will be instantiated and returned. {@link Mapping#expression()} and
     * {@link Mapping#constant()} will be added to the target</li>
     * <li>For iterable mapping methods an immutable empty collection will be returned.</li>
     * <li>For map mapping methods an immutable empty map will be returned.</li>
     * </ul>
     */
    RETURN_DEFAULT,

    /**
     * When given via {@link Mapper#nullValueMappingStrategy()}, causes the setting specified via
     * {@link MapperConfig#nullValueMappingStrategy()} to be applied, if present.
     * <p>
     * When given via {@link NullValueMapping#value()}, causes the setting specified via
     * {@link Mapper#nullValueMappingStrategy() ()} to be applied, if present.
     * <p>
     * Otherwise causes {@link #RETURN_NULL} to be applied.
     */
    DEFAULT;
}
