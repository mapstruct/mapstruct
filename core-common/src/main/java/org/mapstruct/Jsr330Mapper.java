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
 * Allows extra configuration for the generated mapper or decorator and sets the component model of the mapper to
 * {@code jsr330}.<br>
 * This annotation must be placed at a class that is annotated with {@link Mapper @Mapper}.<br>
 *
 * <p>
 * The generated mapper will be annotated with {@code @javax.inject.Named} and can be retrieved via
 * {@code @javax.inject.Inject}.<br>
 *
 * <p>
 * If a component model is used at an inherited {@link MapperConfig @MapperConfig} this annotation takes
 * precedence.<br>
 *
 * @author Christian Bandowski
 * @see SpringMapper @SpringMapper
 * @see CdiMapper @CdiMapper
 * @since 1.3
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Jsr330Mapper {
    /**
     * Defines the value of the {@code javax.inject.Named} annotation used at the generated mapper or decorator
     * implementation.
     *
     * @return {@code javax.inject.Named} annotation value for generated mapper or decorator implementation
     */
    String name() default "";

}
