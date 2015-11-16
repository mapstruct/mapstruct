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
package org.mapstruct.ap.internal.processor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.Manifest;

import javax.annotation.processing.ProcessingEnvironment;

import org.mapstruct.ap.internal.version.VersionInformation;

/**
 * Provides information about the processor version and the processor context implementation version.
 * <p>
 * Separated into an interface and this implementation to avoid cyclic dependencies between the processor package and
 * the model package.
 *
 * @author Andreas Gudian
 */
public class DefaultVersionInformation implements VersionInformation {
    private static final String JAVAC_PE_CLASS = "com.sun.tools.javac.processing.JavacProcessingEnvironment";
    private static final String COMPILER_NAME_JAVAC = "javac";

    private static final String JDT_IDE_PE_CLASS =
        "org.eclipse.jdt.internal.apt.pluggable.core.dispatch.IdeBuildProcessingEnvImpl";
    private static final String JDT_BATCH_PE_CLASS =
        "org.eclipse.jdt.internal.compiler.apt.dispatch.BatchProcessingEnvImpl";
    private static final String COMPILER_NAME_ECLIPSE_JDT = "Eclipse JDT";

    private static final String MAP_STRUCT_VERSION = initMapStructVersion();

    private final String runtimeVersion;
    private final String runtimeVendor;
    private final String compiler;
    private final boolean eclipseJDT;
    private final boolean javac;

    DefaultVersionInformation(String runtimeVersion, String runtimeVendor, String compiler) {
        this.runtimeVersion = runtimeVersion;
        this.runtimeVendor = runtimeVendor;
        this.compiler = compiler;
        this.eclipseJDT = compiler.startsWith( COMPILER_NAME_ECLIPSE_JDT );
        this.javac = compiler.startsWith( COMPILER_NAME_JAVAC );
    }

    @Override
    public String getRuntimeVersion() {
        return this.runtimeVersion;
    }

    @Override
    public String getRuntimeVendor() {
        return this.runtimeVendor;
    }

    @Override
    public String getMapStructVersion() {
        return MAP_STRUCT_VERSION;
    }

    @Override
    public String getCompiler() {
        return this.compiler;
    }

    @Override
    public boolean isEclipseJDTCompiler() {
        return eclipseJDT;
    }

    @Override
    public boolean isJavacCompiler() {
        return javac;
    }

    static DefaultVersionInformation fromProcessingEnvironment(ProcessingEnvironment processingEnv) {
        String runtimeVersion = System.getProperty( "java.version" );
        String runtimeVendor = System.getProperty( "java.vendor" );

        String compiler = getCompiler( processingEnv );

        return new DefaultVersionInformation( runtimeVersion, runtimeVendor, compiler );
    }

    private static String getCompiler(ProcessingEnvironment processingEnv) {
        String className = processingEnv.getClass().getName();

        if ( className.equals( JAVAC_PE_CLASS ) ) {
            return COMPILER_NAME_JAVAC;
        }

        if ( className.equals( JDT_IDE_PE_CLASS ) ) {
            // the processing environment for the IDE integrated APT is in a different bundle than the APT classes
            return COMPILER_NAME_ECLIPSE_JDT + " (IDE) "
                + getLibraryName( processingEnv.getTypeUtils().getClass(), true );
        }

        if ( className.equals( JDT_BATCH_PE_CLASS ) ) {
            return COMPILER_NAME_ECLIPSE_JDT + " (Batch) " + getLibraryName( processingEnv.getClass(), true );
        }

        return processingEnv.getClass().getSimpleName() + " from " + getLibraryName( processingEnv.getClass(), false );
    }

    private static String getLibraryName(Class<?> clazz, boolean preferVersionOnly) {
        String classFileName = asClassFileName( clazz.getName() );
        URL resource = clazz.getClassLoader().getResource( classFileName );

        Manifest manifest = openManifest( classFileName, resource );
        if ( preferVersionOnly && manifest != null ) {
            String version = manifest.getMainAttributes().getValue( "Bundle-Version" );

            if ( version != null ) {
                return version;
            }
        }

        if ( "jar".equals( resource.getProtocol() ) ) {
            return extractJarFileName( resource.getFile() );
        }
        else if ( "jrt".equals( resource.getProtocol() ) ) {
            return extractJrtModuleName( resource );
        }
        else if ( "bundleresource".equals( resource.getProtocol() ) && manifest != null ) {
            return extractBundleName( manifest );
        }

        return resource.toExternalForm();
    }

    private static Manifest openManifest(String classFileName, URL resource) {
        try {
            URL manifestUrl = createManifestUrl( classFileName, resource );
            return new Manifest( manifestUrl.openStream() );
        }
        catch ( IOException e ) {
            return null;
        }
    }

    private static String extractBundleName(Manifest manifest) {
        String version = manifest.getMainAttributes().getValue( "Bundle-Version" );
        String symbolicName = manifest.getMainAttributes().getValue( "Bundle-SymbolicName" );
        int semicolon = symbolicName.indexOf( ';' );
        if ( semicolon > 0 ) {
            symbolicName = symbolicName.substring( 0, semicolon );
        }

        return symbolicName + "_" + version;
    }

    private static String extractJrtModuleName(URL resource) {
        // JDK 9 style, e.g. jrt:/jdk.compiler/com/sun/tools/javac/processing/JavacProcessingEnvironment.class
        int moduleNameSeparator = resource.getFile().indexOf( '/', 1 );
        if ( moduleNameSeparator > 1 ) {
            return resource.getFile().substring( 1, moduleNameSeparator );
        }
        return resource.toExternalForm();
    }

    private static URL createManifestUrl(String classFileName, URL resource) throws MalformedURLException {
        String classUrlString = resource.toExternalForm();
        String baseFileUrl = classUrlString.substring( 0, classUrlString.length() - classFileName.length() );
        return new URL( baseFileUrl + "META-INF/MANIFEST.MF" );
    }

    private static String asClassFileName(String className) {
        return className.replace( '.', '/' ) + ".class";
    }

    private static String extractJarFileName(String file) {
        int excl = file.indexOf( '!' );

        if ( excl > 0 ) {
            String fullFileName = file.substring( 0, excl );
            // it's an URL, so it's not the system path separator char:
            int lastPathSep = fullFileName.lastIndexOf( '/' );
            if ( lastPathSep > 0 && lastPathSep < fullFileName.length() ) {
                return fullFileName.substring( lastPathSep + 1 );
            }
        }

        return file;
    }

    private static String initMapStructVersion() {
        String classFileName = asClassFileName( DefaultVersionInformation.class.getName() );
        URL resource = DefaultVersionInformation.class.getClassLoader().getResource( classFileName );
        Manifest manifest = openManifest( classFileName, resource );

        if ( null != manifest ) {
            String version = manifest.getMainAttributes().getValue( "Implementation-Version" );
            if ( version != null ) {
                return version;
            }
        }
        return "";
    }
}
