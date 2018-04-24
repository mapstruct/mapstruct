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
 * {@code spring}.<br>
 * This annotation must be placed at a class that is annotated with {@link Mapper @Mapper}.<br>
 *
 * <p>
 * The generated mapper will be a singleton-scoped Spring bean and can be retrieved via
 * {@code @org.springframework.beans.factory.annotation.Autowired}.<br>
 *
 * <p>
 * If a component model is used at an inherited {@link MapperConfig @MapperConfig} this annotation takes
 * precedence.<br>
 *
 * @author Christian Bandowski
 * @see Jsr330Mapper @Jsr330Mapper
 * @see CdiMapper @CdiMapper
 * @since 1.3
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface SpringMapper {
    /**
     * Defines the name of the Spring bean for the generated mapper or decorator implementation.
     *
     * @return Spring bean name
     */
    String name() default "";

    /**
     * Defines the name for the decorator that will be used within the
     * {@code org.springframework.beans.factory.annotation.Qualifier} annotation in case a decorator will be generated.
     *
     * @return Qualifier name for the generated delegate
     */
    String delegateQualifier() default "";

    /**
     * Defines the type of annotation that will be used to declare the generated mapper or decorator as a Spring
     * component.
     * <p>
     * Defaults to {@link SpringComponentType#COMPONENT COMPONENT}.
     *
     * @return Component type that will be used to decide which annotation will be generated
     */
    SpringComponentType componentType() default SpringComponentType.COMPONENT;

    /**
     * Type of the Spring component.
     *
     * @author Christian Bandowski
     * @since 1.3
     */
    enum SpringComponentType {
        /**
         * Uses {@code org.springframework.stereotype.Component} annotation.
         */
        COMPONENT,

        /**
         * Uses {@code org.springframework.stereotype.Service} annotation.
         */
        SERVICE,

        /**
         * Uses {@code org.springframework.stereotype.Repository} annotation.
         */
        REPOSITORY,

        /**
         * Uses {@code org.springframework.stereotype.Controller} annotation.
         */
        CONTROLLER
    }
}
