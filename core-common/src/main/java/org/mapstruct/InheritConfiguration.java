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
 * Advises the code generator to apply the configuration (as given via {@link Mapping}, {@link IterableMapping} etc.)
 * from another mapping method (declared on the same mapper type) or prototype method (declared on a mapper config class
 * referenced via {@link Mapper#config()}) to the annotated method as well.
 * <p>
 * If no method can be identified unambiguously as configuration source (i.e. several candidate methods with matching
 * source and target type exist), the name of the method to inherit from must be specified via {@link #name()}.
 * <p>
 * A typical use case is annotating an update method so it inherits all mappings from a corresponding "standard" mapping
 * method:
 *
 * <pre>
 * <code>
 * &#64;Mappings({
 *     &#64;Mapping(target="make", source="brand"),
 *     &#64;Mapping(target="seatCount", source="numberOfSeats")
 * })
 * CarDto carToCarDto(Car car);
 *
 * &#64;InheritConfiguration
 * void updateCarDto(Car car, &#64;MappingTarget CarDto carDto);
 * </code>
 * </pre>
 *
 * @author Sjaak Derksen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface InheritConfiguration {

    /**
     * The name of the mapping method to inherit the mappings from. Needs only to be specified in case more than one
     * method with matching source and target type exists.
     *
     * @return The name of the mapping method to inherit the mappings from.
     */
    String name() default "";
}
