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
package org.mapstruct.ap.internal.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.mapstruct.ap.spi.AstModifyingAnnotationProcessor;

/**
 * Keeps contextual data in the scope of the entire annotation processor ("application scope").
 *
 * @author Gunnar Morling
 */
public class AnnotationProcessorContext {

    private List<AstModifyingAnnotationProcessor> astModifyingAnnotationProcessors;

    public AnnotationProcessorContext() {
        astModifyingAnnotationProcessors = java.util.Collections.unmodifiableList(
                findAstModifyingAnnotationProcessors() );
    }

    private static List<AstModifyingAnnotationProcessor> findAstModifyingAnnotationProcessors() {
        List<AstModifyingAnnotationProcessor> processors = new ArrayList<AstModifyingAnnotationProcessor>();

        ServiceLoader<AstModifyingAnnotationProcessor> loader = ServiceLoader.load(
                AstModifyingAnnotationProcessor.class, AnnotationProcessorContext.class.getClassLoader()
        );

        for ( Iterator<AstModifyingAnnotationProcessor> it = loader.iterator(); it.hasNext(); ) {
            processors.add( it.next() );
        }

        return processors;
    }

    public List<AstModifyingAnnotationProcessor> getAstModifyingAnnotationProcessors() {
        return astModifyingAnnotationProcessors;
    }
}
