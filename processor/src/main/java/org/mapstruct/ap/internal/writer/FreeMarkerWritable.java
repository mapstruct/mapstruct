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

/**
 * A {@link Writable} which uses the FreeMarker template engine to generate the output.
 *
 * @author Gunnar Morling
 */
public abstract class FreeMarkerWritable implements Writable {

    @Override
    public void write(Context context, Writer writer) throws Exception {
        new FreeMarkerModelElementWriter().write( this, context, writer );
    }

    /**
     * Returns the name of the template to be used for a specific writable type. By default,
     * {@link #getTemplateNameForClass(Class)} is called with {@code getClass()}, but this can be customized by
     * overriding this method if required.
     *
     * @return the name of the template. Must not be {@code null}.
     */
    protected String getTemplateName() {
        return getTemplateNameForClass( getClass() );
    }

    /**
     * Returns the name of the template to be used for a specific writable type. By default, the package directory and
     * the class name of the given model element type, appended with the extension {@code *.ftl} is used as template
     * file name.
     *
     * @return the name of the template. Must not be {@code null}.
     */
    protected String getTemplateNameForClass(Class<?> clazz) {
        return clazz.getName().replace( '.', '/' ) + ".ftl";
    }
}
