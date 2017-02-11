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
package org.mapstruct.ap.internal.util;

/**
 * This is a helper interface until we migrate to Java 8. It allows us to abstract our code easier.
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 *
 * @author Filip Hrisafov
 */
public interface Extractor<T, R> {

    /**
     * Extract a value from the passed parameter.
     *
     * @param t the value that we need to extract from
     *
     * @return the result from the extraction
     */
    R apply(T t);
}
