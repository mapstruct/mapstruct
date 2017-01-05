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
package org.mapstruct.ap.internal.util.workarounds;

import java.lang.reflect.Method;
import java.net.URLClassLoader;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

/**
 * In Eclipse 4.6, the ClassLoader of the annotation processor does not provide access to the implementation types of
 * the APT-API anymore, so we need to create a new ClassLoader containing the processor class path URLs and having the
 * ClassLoader of the APT-API implementations as parent. The method invocation then consequently needs to be done via
 * reflection.
 *
 * @author Andreas Gudian
 */
class EclipseClassLoaderBridge {
    private static final String ECLIPSE_AS_MEMBER_OF_WORKAROUND =
        "org.mapstruct.ap.internal.util.workarounds.EclipseAsMemberOfWorkaround";

    private static ClassLoader classLoader;
    private static Method asMemberOf;

    private EclipseClassLoaderBridge() {
    }

    /**
     * Invokes {@link EclipseAsMemberOfWorkaround#asMemberOf(ProcessingEnvironment, DeclaredType, Element)} via
     * reflection using a special ClassLoader.
     */
    static TypeMirror invokeAsMemberOfWorkaround(ProcessingEnvironment env, DeclaredType containing, Element element)
        throws Exception {
        return (TypeMirror) getAsMemberOf( element.getClass().getClassLoader() ).invoke(
            null,
            env,
            containing,
            element );
    }

    private static ClassLoader getOrCreateClassLoader(ClassLoader parent) {
        if ( classLoader == null ) {
            classLoader = new URLClassLoader(
                ( (URLClassLoader) EclipseClassLoaderBridge.class.getClassLoader() ).getURLs(),
                parent );
        }

        return classLoader;
    }

    private static Method getAsMemberOf(ClassLoader platformClassLoader) throws Exception {
        if ( asMemberOf == null ) {
            Class<?> workaroundClass =
                getOrCreateClassLoader( platformClassLoader ).loadClass( ECLIPSE_AS_MEMBER_OF_WORKAROUND );

            Method found = workaroundClass.getDeclaredMethod(
                "asMemberOf",
                ProcessingEnvironment.class,
                DeclaredType.class,
                Element.class );

            found.setAccessible( true );

            asMemberOf = found;
        }

        return asMemberOf;
    }
}
