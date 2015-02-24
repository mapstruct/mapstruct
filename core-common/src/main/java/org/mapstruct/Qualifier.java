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
 * Declares an annotation type to be a qualifier. Qualifier annotations allow unambiguously identify a suitable mapping
 * method in case several methods qualify to map a bean property, iterable element etc.
 * <p>
 * For more info see:
 * <ul>
 * <li>{@link Mapping#qualifiedBy() }</li>
 * <li>{@link IterableMapping#qualifiedBy() }</li>
 * <li>{@link MapMapping#keyQualifiedBy() }</li>
 * <li>{@link MapMapping#valueQualifiedBy() }</li>
 * </ul>
 * Example:
 * <pre>
 * &#64;Qualifier
 * &#64;Target(ElementType.METHOD)
 * &#64;Retention(RetentionPolicy.SOURCE)
 * public &#64;interface EnglishToGerman {}
 * </pre>
 *
 * @author Sjaak Derksen
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Qualifier {
}
