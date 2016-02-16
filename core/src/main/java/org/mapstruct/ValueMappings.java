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
 * Constructs a set of value (constant) mappings.
 *
 * Each mapping entry is provided by a mapping in {@link #value()}. There are three scenarios to consider:
 * <ol>
 * <li>There is a non null source and there's a match.</li>
 * <li>The source provided is null.</li>
 * <li>There is no match.</li>
 * </ol>
 *
 * {@link ValueMappingType} will be used to distinguish between those scenarios. Each source must be unique and the
 * latter 2 scenarios may only appear once in the provided values {@link #value() }
 *
 * @author Sjaak Derksen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
public @interface ValueMappings {

    ValueMapping[] value();

}
