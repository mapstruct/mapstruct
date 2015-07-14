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
package org.mapstruct.ap.internal.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.tools.FileObject;

import org.mapstruct.ap.internal.writer.Writable.Context;

import freemarker.cache.StrongCacheStorage;
import freemarker.cache.TemplateLoader;
import freemarker.log.Logger;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

/**
 * Writes Java source files based on given mapper models, using a FreeMarker
 * template.
 *
 * @author Gunnar Morling
 */
public class ModelWriter {

    /**
     * FreeMarker configuration. As per the documentation, thread-safe if not
     * altered after original initialization
     */
    private static final Configuration CONFIGURATION;

    static {
        try {
            Logger.selectLoggerLibrary( Logger.LIBRARY_NONE );
        }
        catch ( ClassNotFoundException e ) {
            throw new RuntimeException( e );
        }

        CONFIGURATION = new Configuration( Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS );
        CONFIGURATION.setTemplateLoader( new SimpleClasspathLoader() );
        CONFIGURATION.setObjectWrapper( new DefaultObjectWrapper( Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS ) );
        CONFIGURATION.setSharedVariable(
            "includeModel",
            new ModelIncludeDirective( CONFIGURATION )
        );
        // do not refresh/gc the cached templates, as we never change them at runtime
        CONFIGURATION.setCacheStorage( new StrongCacheStorage() );
        CONFIGURATION.setTemplateUpdateDelay( Integer.MAX_VALUE );
        CONFIGURATION.setLocalizedLookup( false );
    }

    public void writeModel(FileObject sourceFile, Writable model) {
        try {
            BufferedWriter writer = new BufferedWriter( new IndentationCorrectingWriter( sourceFile.openWriter() ) );

            Map<Class<?>, Object> values = new HashMap<Class<?>, Object>();
            values.put( Configuration.class, CONFIGURATION );

            model.write( new DefaultModelElementWriterContext( values ), writer );

            writer.flush();
            writer.close();
        }
        catch ( RuntimeException e ) {
            throw e;
        }
        catch ( Exception e ) {
            throw new RuntimeException( e );
        }
    }

    /**
     * Simplified template loader that avoids reading modification timestamps and disables the jar-file caching.
     *
     * @author Andreas Gudian
     */
    private static final class SimpleClasspathLoader implements TemplateLoader {
        @Override
        public Reader getReader(Object name, String encoding) throws IOException {
            URL url = getClass().getClassLoader().getResource( String.valueOf( name ) );

            URLConnection connection = url.openConnection();

            // don't use jar-file caching, as it caused occasionally closed input streams [at least under JDK 1.8.0_25]
            connection.setUseCaches( false );

            InputStream is = connection.getInputStream();

            return new InputStreamReader( is, Charset.forName( "UTF-8" ) );
        }

        @Override
        public long getLastModified(Object templateSource) {
            return 0;
        }

        @Override
        public Object findTemplateSource(String name) throws IOException {
            return name;
        }

        @Override
        public void closeTemplateSource(Object templateSource) throws IOException {
        }
    }

    /**
     * {@link Context} implementation which provides access to the current FreeMarker {@link Configuration}.
     *
     * @author Gunnar Morling
     */
    static class DefaultModelElementWriterContext implements Context {

        private final Map<Class<?>, Object> values;

        DefaultModelElementWriterContext(Map<Class<?>, Object> values) {
            this.values = new HashMap<Class<?>, Object>( values );
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> T get(Class<T> type) {
            return (T) values.get( type );
        }
    }
}
