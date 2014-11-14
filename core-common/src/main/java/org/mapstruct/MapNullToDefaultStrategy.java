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
 * Strategy for propagating the value of collection-typed properties from source to target.
 *
 * @author Sjaak Derksen
 */
public enum MapNullToDefaultStrategy {

    /**
     * A null source argument of a mapping method will be mapped to a null target result
     */
    MAP_NULL_TO_NULL,

    /**
     * A null source argument of a mapping method will be mapped to a default target result
     * <p>
     * <ol>
     * <li>For a bean mapping this means a target object will be returned. {@link Mapping#expression()} and
     * {@link Mapping#constant()} will be added to the target<\li>
     * <li>For an iterable mapping this means a {@link java.util.Collections#emptyList() } will be returned<\li>
     * <li>For an map mapping this means a {@link java.util.Collections#emptyMap() } will be returned<\li>
     * </ol>
     */
    MAP_NULL_TO_DEFAULT,

    /**
     * When given via {@link Mapper#mapNullToDefaultStrategy()}, causes the setting specified via
     * {@link MapperConfig#mapNullToDefaultStrategy()} to be applied, if present.
     * <p>
     * When given via {@link MapNullToDefault#mapNullToDefaultStrategy()}, causes the setting specified via
     * {@link Mapper#mapNullToDefaultStrategy()} to be applied, if present.
     * <p>
     * Otherwise causes
     * {@link #MAP_NULL_TO_NULL} to be applied.
     */
    DEFAULT;
}
