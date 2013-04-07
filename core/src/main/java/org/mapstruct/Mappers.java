/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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


public class Mappers {

    private final static String IMPLEMENTATION_SUFFIX = "Impl";

    /**
     * TODO: Check that
     * - clazz is an interface
     * - the implementation type implements clazz
     * - clazz is annotated with @Mapper
     *
     * TODO: Use privileged action
     */
    @SuppressWarnings("unchecked")
    public static <T> T getMapper(Class<T> clazz) {
        try {

//            ClassLoader classLoader = clazz.getClassLoader();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

            return (T) classLoader.loadClass( clazz.getName() + IMPLEMENTATION_SUFFIX ).newInstance();
        }
        catch ( Exception e ) {
            e.printStackTrace();
            throw new RuntimeException( e );
        }
    }
}
