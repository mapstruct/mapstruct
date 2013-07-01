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
package org.mapstruct.ap.processor;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.model.Options;

/**
 * Default implementation of the processor context.
 *
 * @author Gunnar Morling
 */
public class DefaultModelElementProcessorContext implements ModelElementProcessor.ProcessorContext {

    private final ProcessingEnvironment processingEnvironment;
    private final DelegatingMessager messager;
    private final Options options;

    public DefaultModelElementProcessorContext(ProcessingEnvironment processingEnvironment, Options options) {
        this.processingEnvironment = processingEnvironment;
        this.messager = new DelegatingMessager( processingEnvironment.getMessager() );
        this.options = options;
    }

    @Override
    public Filer getFiler() {
        return processingEnvironment.getFiler();
    }

    @Override
    public Types getTypeUtils() {
        return processingEnvironment.getTypeUtils();
    }

    @Override
    public Elements getElementUtils() {
        return processingEnvironment.getElementUtils();
    }

    @Override
    public Messager getMessager() {
        return messager;
    }

    @Override
    public Options getOptions() {
        return options;
    }

    @Override
    public boolean isErroneous() {
        return messager.isErroneous();
    }

    private static class DelegatingMessager implements Messager {

        private final Messager delegate;
        private boolean isErroneous = false;

        public DelegatingMessager(Messager delegate) {
            this.delegate = delegate;
        }

        public void printMessage(Kind kind, CharSequence msg) {
            delegate.printMessage( kind, msg );
            if ( kind == Kind.ERROR ) {
                isErroneous = true;
            }
        }

        public void printMessage(Kind kind, CharSequence msg, Element e) {
            delegate.printMessage( kind, msg, e );
            if ( kind == Kind.ERROR ) {
                isErroneous = true;
            }
        }

        public void printMessage(Kind kind, CharSequence msg, Element e, AnnotationMirror a) {
            delegate.printMessage( kind, msg, e, a );
            if ( kind == Kind.ERROR ) {
                isErroneous = true;
            }
        }

        public void printMessage(Kind kind, CharSequence msg, Element e, AnnotationMirror a, AnnotationValue v) {
            delegate.printMessage( kind, msg, e, a, v );
            if ( kind == Kind.ERROR ) {
                isErroneous = true;
            }
        }

        public boolean isErroneous() {
            return isErroneous;
        }
    }
}
