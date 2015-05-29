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

import java.io.Writer;
import java.util.Map;

import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.SimpleMapModel;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import org.mapstruct.ap.internal.writer.Writable.Context;

/**
 * Delegate for writing given {@link Writable}s into a {@link Writer} using
 * FreeMarker templates. Any parameters passed to the
 * {@link ModelIncludeDirective} in addition to element itself can be accessed
 * from within the template using the {@code ext} pseudo-element.
 *
 * @author Gunnar Morling
 */
public class FreeMarkerModelElementWriter {

    public void write(FreeMarkerWritable writable, Context context, Writer writer) throws Exception {
        Configuration configuration = context.get( Configuration.class );
        Template template = configuration.getTemplate( writable.getTemplateName() );
        template.process(
            new ExternalParamsTemplateModel(
                new BeanModel( writable, BeansWrapper.getDefaultInstance() ),
                new SimpleMapModel( context.get( Map.class ), BeansWrapper.getDefaultInstance() )
            ),
            writer
        );
    }

    private static class ExternalParamsTemplateModel implements TemplateHashModel {

        private final BeanModel object;
        private final SimpleMapModel extParams;

        public ExternalParamsTemplateModel(BeanModel object, SimpleMapModel extParams) {
            this.object = object;
            this.extParams = extParams;
        }

        @Override
        public TemplateModel get(String key) throws TemplateModelException {
            if ( key.equals( "ext" ) ) {
                return extParams;
            }
            else {
                return object.get( key );
            }
        }

        @Override
        public boolean isEmpty() throws TemplateModelException {
            return object.isEmpty() && extParams.isEmpty();
        }
    }
}
