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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation ensures that all forward mappings are copied as a base for the reverse mapping.
 *
 * MapStruct will check if there is a source / target mapper matching the profile. That is:
 * <ol>
 * <li>the source parameter should match the return type (or the parameter indicated as {@link MappingTarget}</li>
 * <li>the target parameter should match the source parameter</li>
 * </ol>
 *
 * @author Sjaak Derksen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface ReverseMappingMethod {

    /**
     * The name of the 'forward' mapping method that should be taken as the base.
     *
     * @return
     */
    String configuredBy() default "";

}
