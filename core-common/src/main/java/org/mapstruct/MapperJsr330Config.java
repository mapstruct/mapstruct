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
 * Allows extra configuration for the generated mapper or decorator in case the {@link Mapper#componentModel()} was
 * set to {@code jsr330}.
 *
 * @author Christian Bandowski
 * @since 1.3
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface MapperJsr330Config {
    /**
     * Defines the value of the {@code javax.inject.Named} annotation used at the generated mapper or decorator
     * implementation.
     *
     * @return {@code javax.inject.Named} annotation value for generated mapper or decorator implementation
     */
    String value() default "";

}
