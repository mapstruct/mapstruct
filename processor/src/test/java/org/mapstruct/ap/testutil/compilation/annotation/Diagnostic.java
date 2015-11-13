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
package org.mapstruct.ap.testutil.compilation.annotation;

import javax.tools.Diagnostic.Kind;

/**
 * An expected diagnostic of a compilation.
 *
 * @author Gunnar Morling
 */
public @interface Diagnostic {

    /**
     * The type for which the diagnostic was created.
     *
     * @return The type for which the diagnostic was created.
     */
    Class<?> type() default void.class;

    /**
     * The expected kind of diagnostic.
     *
     * @return The expected kind of diagnostic.
     */
    Kind kind();

    /**
     * The expected line number of the diagnostic.
     *
     * @return The expected line number of the diagnostic.
     */
    long line() default -1;

    /**
     * A regular expression matching the expected message of the diagnostic.
     * Wild-cards matching any character (".*") will be added to the beginning
     * and end of the given expression when applying it.
     *
     * @return A regular expression matching the expected message of the
     *         diagnostic.
     */
    String messageRegExp() default ".*";
}
