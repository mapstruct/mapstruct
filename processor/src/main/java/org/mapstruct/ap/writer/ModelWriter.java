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
package org.mapstruct.ap.writer;

import java.io.BufferedWriter;
import java.util.HashMap;
import java.util.Map;
import javax.tools.JavaFileObject;

import freemarker.log.Logger;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import org.mapstruct.ap.writer.Writable.Context;

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

        CONFIGURATION = new Configuration();
        CONFIGURATION.setClassForTemplateLoading( ModelWriter.class, "/" );
        CONFIGURATION.setObjectWrapper( new DefaultObjectWrapper() );
        CONFIGURATION.setSharedVariable(
            "includeModel",
            new ModelIncludeDirective( CONFIGURATION )
        );
    }

    public void writeModel(JavaFileObject sourceFile, Writable model) {
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
     * {@link Context} implementation which provides access to the current
     * FreeMarker {@link Configuration}.
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
