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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.template.Configuration;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

import org.mapstruct.ap.internal.writer.ModelWriter.DefaultModelElementWriterContext;

/**
 * A {@link TemplateDirectiveModel} which allows to recursively write a graph of
 * {@link Writable}s, with each element using its own template. Elements are
 * imported into the parent template by using this directive like so:
 * {@code <@includeModel object=myProperty/>}.
 *
 * @author Gunnar Morling
 */
public class ModelIncludeDirective implements TemplateDirectiveModel {

    private final Configuration configuration;

    public ModelIncludeDirective(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars,
                        TemplateDirectiveBody body)
        throws TemplateException, IOException {

        Writable modelElement = getModelElement( params );
        DefaultModelElementWriterContext context = createContext( params );

        try {
            if ( modelElement != null ) {
                modelElement.write( context, env.getOut() );
            }
        }
        catch ( TemplateException te ) {
            throw te;
        }
        catch ( IOException ioe ) {
            throw ioe;
        }
        catch ( RuntimeException re ) {
            throw re;
        }
        catch ( Exception e ) {
            throw new RuntimeException( e );
        }
    }

    @SuppressWarnings("rawtypes")
    private Writable getModelElement(Map params) {
        if ( !params.containsKey( "object" ) ) {
            throw new IllegalArgumentException(
                "Object to be included must be passed to this directive via the 'object' parameter"
            );
        }

        BeanModel objectModel = (BeanModel) params.get( "object" );

        if ( objectModel == null ) {
            return null;
        }

        if ( !( objectModel.getWrappedObject() instanceof Writable ) ) {
            throw new IllegalArgumentException( "Given object isn't a Writable:" + objectModel.getWrappedObject() );
        }

        return (Writable) objectModel.getWrappedObject();
    }

    /**
     * Creates a writer context providing access to the FreeMarker
     * {@link Configuration} and a map with any additional parameters passed to
     * the directive.
     *
     * @param params The parameter map passed to this directive.
     *
     * @return A writer context.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private DefaultModelElementWriterContext createContext(Map params) {
        Map<String, Object> ext = new HashMap<String, Object>( params );
        ext.remove( "object" );

        Map<Class<?>, Object> values = new HashMap<Class<?>, Object>();
        values.put( Configuration.class, configuration );
        values.put( Map.class, ext );

        return new DefaultModelElementWriterContext( values );
    }
}
