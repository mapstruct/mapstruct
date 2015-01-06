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
 * Specifies a decorator to be applied to a generated mapper, which e.g. can be used to amend mappings performed by
 * generated mapping methods.
 * <p>
 * A typical decorator implementation will be an abstract class and only implement/override a subset of the methods of
 * the mapper type which it decorates. All methods not implemented or overridden by the decorator will be implemented by
 * the code generator.
 * <p>
 * If a constructor with a single parameter accepting the type of the decorated mapper is present, a delegate with
 * generated implementations of all the mapper methods will be passed to this constructor. A typical implementation will
 * store the passed delegate in a field of the decorator and make use of it in the decorator methods.
 * <p>
 * <b>NOTE:</b> The decorator feature is considered experimental and it may change in future releases. Currently
 * decorators are only supported for the default component model, not for the CDI and Spring models.
 *
 * @author Gunnar Morling
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface DecoratedWith {

    /**
     * The decorator type. Must extend or implement the mapper type to which it is applied.
     * <p>
     * The decorator type must either have a default constructor or a constructor with a single parameter accepting the
     * type of the decorated mapper.
     *
     * @return the decorator type
     */
    Class<?> value();
}
