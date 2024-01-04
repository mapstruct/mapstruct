/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.testutil.runner;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A parallel capable class loader for which additional URLs can be added after construction, and that will force
 * classes from org.mapstruct.at.test.** packages to be loaded with this instance
 *
 * @author Andreas Gudian
 */
final class ModifiableURLClassLoader extends URLClassLoader {

    private static final String ORG_MAPSTRUCT_AP_TEST = "org.mapstruct.ap.test.";

    private static final FilteringParentClassLoader DEFAULT_PARENT_CLASS_LOADER = new FilteringParentClassLoader(
        ORG_MAPSTRUCT_AP_TEST );

    static {
        tryRegisterAsParallelCapable();
    }

    private final ConcurrentMap<URL, URL> addedURLs = new ConcurrentHashMap<>();

    ModifiableURLClassLoader(ClassLoader parent) {
        super( new URL[] { }, parent );
    }

    ModifiableURLClassLoader() {
        this( DEFAULT_PARENT_CLASS_LOADER );
    }

    @Override
    protected void addURL(URL url) {
        if ( addedURLs.putIfAbsent( url, url ) == null ) {
            super.addURL( url );
        }
    }

    ModifiableURLClassLoader withOriginsOf(Collection<Class<?>> classes) {
        for ( Class<?> c : classes ) {
            withOriginOf( c );
        }

        return this;
    }

    ModifiableURLClassLoader withOriginOf(Class<?> clazz) {
        String classFileName = clazz.getName().replace( ".", "/" ) + ".class";
        URL classResource = clazz.getClassLoader().getResource( classFileName );
        String fullyQualifiedUrl = classResource.toExternalForm();
        String basePath = fullyQualifiedUrl.substring( 0, fullyQualifiedUrl.length() - classFileName.length() );

        return withURL( basePath );
    }

    ModifiableURLClassLoader withPaths(Collection<String> baseUrls) {
        for ( String url : baseUrls ) {
            withPath( url );
        }

        return this;
    }

    ModifiableURLClassLoader withURL(String baseUrl) {
        try {
            addURL( new URL( baseUrl ) );
        }
        catch ( MalformedURLException e ) {
            throw new RuntimeException( e );
        }

        return this;
    }

    ModifiableURLClassLoader withPath(String path) {
        try {
            addURL( new File( path ).toURI().toURL() );
        }
        catch ( MalformedURLException e ) {
            throw new RuntimeException( e );
        }

        return this;
    }

    private static void tryRegisterAsParallelCapable() {
        try {
            ClassLoader.class.getMethod( "registerAsParallelCapable" ).invoke( null );
        }
        catch ( NoSuchMethodException | SecurityException | IllegalAccessException
            | IllegalArgumentException | InvocationTargetException e ) {
            return; // ignore
        }
    }
}
