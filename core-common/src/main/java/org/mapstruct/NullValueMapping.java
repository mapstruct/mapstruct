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
 * Determines what kind to return in case of a null source argument.
 * <p>
 * For:
 * <ol>
 * <li>Bean Mapping: an 'empty' target bean, except for expressions and constants</li>
 * <li>Iterable Mapping: an 'empty' list</li>
 * <li>Map Mapping: an 'empty' map</li>
 * </ol>
 *
 * The user has a choice to use this annotation. When its used, it is used to either override a more global
 * setting, or in the most common case, to set the specific behavior to map null to default
 *
 * @author Sjaak Derksen
 */
@Target( { ElementType.METHOD } )
@Retention( RetentionPolicy.SOURCE )
public @interface NullValueMapping {

    NullValueMappingStrategy value() default NullValueMappingStrategy.RETURN_DEFAULT;
}
