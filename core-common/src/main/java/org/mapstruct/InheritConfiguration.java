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

/**
 * Advises the code generator to apply all the {@link Mapping}s from a mapping method to the annotated method
 * as well. The method should have the same source type and target type.
 * <p>
 * A typical use case is annotating an update method (a method with the {@link MappingTarget} in which an existing
 * target is updated) in order to copy all defined mappings.
 * <p>
 * If more than one matching method exists, the name of the method to inherit the configuration from must be
 * specified via {@link #name()}
 *
 * @author Sjaak Derksen
 */
@Target( ElementType.METHOD )
@Retention( RetentionPolicy.SOURCE )
public @interface InheritConfiguration {

    /**
     * The name of the mapping method to inherit the mappings from. Needs only to be specified in case more than
     * one method with matching source and target type exists.
     *
     * @return The name of the mapping method to inherit the mappings from.
     */
    String name() default "";
}
