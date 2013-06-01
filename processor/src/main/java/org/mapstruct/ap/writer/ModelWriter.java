/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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
import javax.tools.JavaFileObject;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

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

    private final String templateName;

    static {
        CONFIGURATION = new Configuration();
        CONFIGURATION.setClassForTemplateLoading( ModelWriter.class, "/" );
        CONFIGURATION.setObjectWrapper( new DefaultObjectWrapper() );
    }

    public ModelWriter(String templateName) {
        this.templateName = templateName;
    }

    public void writeModel(JavaFileObject sourceFile, Object model) {

        try {
            BufferedWriter writer = new BufferedWriter( sourceFile.openWriter() );

            Template template = CONFIGURATION.getTemplate( templateName );
            template.process( model, writer );
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
}
