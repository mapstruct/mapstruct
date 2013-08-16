/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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

import org.mapstruct.factory.Mappers;

/**
 * Marks an interface as mapper interface and activates the generation of a
 * mapper implementation for that interface.
 *
 * @author Gunnar Morling
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Mapper {

    /**
     * The mapper types used by this mapper.
     *
     * @return The mapper types used by this mapper.
     */
    Class<?>[] uses() default { };

    /**
     * How unmapped properties of the target type of a mapping should be
     * reported.
     *
     * @return The reporting policy for unmapped target properties.
     */
    ReportingPolicy unmappedTargetPolicy() default ReportingPolicy.WARN;

    /**
     * Specifies the component model to which the generated mapper should
     * adhere. Supported values are
     * <ul>
     * <li> {@code default}: the mapper uses no component model, instances are
     * typically retrieved via {@link Mappers#getMapper(Class)}</li>
     * <li>
     * {@code cdi}: the generated mapper is an application-scoped CDI bean and
     * can be retrieved via {@code @Inject}</li>
     * <li>
     * {@code spring}: the generated mapper is a Spring bean and
     * can be retrieved via {@code @Autowired}</li>
     * <li>
     * {@code jsr330}: the generated mapper is annotated with {@code @Named} and
     * can be retrieved via {@code @Inject}</li>
     * </ul>
     *
     * @return The component model for the generated mapper.
     */
    String componentModel() default "default";
}
