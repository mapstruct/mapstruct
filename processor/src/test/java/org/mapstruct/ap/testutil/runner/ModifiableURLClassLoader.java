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
package org.mapstruct.ap.testutil.runner;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A parallel capable class loader for which additional URLs can be added after construction, and that will force
 * classes from org.mapstruct.at.test.** packages to be loaded with this instance
 *
 * @author Andreas Gudian
 */
public class ModifiableURLClassLoader extends URLClassLoader {

    private static final String ORG_MAPSTRUCT_AP_TEST = "org.mapstruct.ap.test.";

    private static final FilteringParentClassLoader PARENT_CLASS_LOADER = new FilteringParentClassLoader(
        ORG_MAPSTRUCT_AP_TEST );

    static {
        tryRegisterAsParallelCapable();
    }

    private final ConcurrentMap<URL, URL> addedURLs = new ConcurrentHashMap<URL, URL>();

    public ModifiableURLClassLoader() {
        super( new URL[] { }, PARENT_CLASS_LOADER );
    }

    @Override
    protected void addURL(URL url) {
        if ( addedURLs.putIfAbsent( url, url ) == null ) {
            super.addURL( url );
        }
    }

    public void addURL(String basePath) {
        try {
            addURL( new URL( basePath ) );
        }
        catch ( MalformedURLException e ) {
            throw new RuntimeException( e );
        }
    }

    public void addOutputDir(String outputDir) {
        try {
            addURL( new File( outputDir ).toURI().toURL() );
        }
        catch ( MalformedURLException e ) {
            throw new RuntimeException( e );
        }
    }

    private static void tryRegisterAsParallelCapable() {
        try {
            ClassLoader.class.getMethod( "registerAsParallelCapable" ).invoke( null );
        }
        catch ( NoSuchMethodException e ) {
            return; // ignore
        }
        catch ( SecurityException e ) {
            return; // ignore
        }
        catch ( IllegalAccessException e ) {
            return; // ignore
        }
        catch ( IllegalArgumentException e ) {
            return; // ignore
        }
        catch ( InvocationTargetException e ) {
            return; // ignore
        }
    }

    private static final class FilteringParentClassLoader extends ClassLoader {
        private String excludedPackage;

        public FilteringParentClassLoader(String excludedPackage) {
            this.excludedPackage = excludedPackage;
        }

        @Override
        protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            if ( name.startsWith( excludedPackage ) ) {
                return null;
            }

            return super.loadClass( name, resolve );
        }
    }
}
