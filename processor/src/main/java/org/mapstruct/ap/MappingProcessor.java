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
package org.mapstruct.ap;

import java.util.Arrays;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementKindVisitor6;

import net.java.dev.hickory.prism.GeneratePrism;
import net.java.dev.hickory.prism.GeneratePrisms;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.model.Mapper;
import org.mapstruct.ap.model.Options;
import org.mapstruct.ap.model.ReportingPolicy;
import org.mapstruct.ap.processor.DefaultModelElementProcessorContext;
import org.mapstruct.ap.processor.MapperCreationProcessor;
import org.mapstruct.ap.processor.MapperRenderingProcessor;
import org.mapstruct.ap.processor.MethodRetrievalProcessor;
import org.mapstruct.ap.processor.ModelElementProcessor;
import org.mapstruct.ap.processor.ModelElementProcessor.ProcessorContext;

/**
 * A {@link Processor} which generates the implementations for mapper interfaces
 * (interfaces annotated with {@code @Mapper}.
 * </p>
 * Implementation notes:
 * </p>
 * The generation happens by incrementally building up a model representation of
 * each mapper to be generated (a {@link Mapper} object), which is then written
 * into the resulting Java source file using the FreeMarker template engine.
 * </p>
 * The model instantiation and processing happens in several phases/passes by applying
 * a sequence of {@link ModelElementProcessor}s.
 * </p>
 * For reading annotation attributes, prisms as generated with help of the <a
 * href="https://java.net/projects/hickory">Hickory</a> tool are used. These
 * prisms allow a comfortable access to annotations and their attributes without
 * depending on their class objects.
 *
 * @author Gunnar Morling
 */
@SupportedAnnotationTypes("org.mapstruct.Mapper")
@GeneratePrisms({
    @GeneratePrism(value = org.mapstruct.Mapper.class, publicAccess = true),
    @GeneratePrism(value = Mapping.class, publicAccess = true),
    @GeneratePrism(value = Mappings.class, publicAccess = true)
})
@SupportedOptions({ MappingProcessor.SUPPRESS_GENERATOR_TIMESTAMP, MappingProcessor.UNMAPPED_TARGET_POLICY })
public class MappingProcessor extends AbstractProcessor {

    /**
     * Whether this processor claims all processed annotations exclusively or not.
     */
    private static final boolean ANNOTATIONS_CLAIMED_EXCLUSIVELY = false;

    protected static final String SUPPRESS_GENERATOR_TIMESTAMP = "suppressGeneratorTimestamp";
    protected static final String UNMAPPED_TARGET_POLICY = "unmappedTargetPolicy";

    private Options options;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init( processingEnv );

        options = createOptions();
    }

    private Options createOptions() {
        String unmappedTargetPolicy = processingEnv.getOptions().get( UNMAPPED_TARGET_POLICY );

        return new Options(
            Boolean.valueOf( processingEnv.getOptions().get( SUPPRESS_GENERATOR_TIMESTAMP ) ),
            unmappedTargetPolicy != null ? ReportingPolicy.valueOf( unmappedTargetPolicy ) : null
        );
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnvironment) {
        ProcessorContext context = new DefaultModelElementProcessorContext( processingEnv, options );

        for ( TypeElement annotation : annotations ) {

            //Indicates that the annotation's type isn't on the class path of the compiled
            //project. Let the compiler deal with that and print an appropriate error.
            if ( annotation.getKind() != ElementKind.ANNOTATION_TYPE ) {
                continue;
            }

            for ( Element mapperElement : roundEnvironment.getElementsAnnotatedWith( annotation ) ) {
                TypeElement mapperTypeElement = asTypeElement( mapperElement );
                processMapperTypeElement( context, mapperTypeElement );
            }
        }

        return ANNOTATIONS_CLAIMED_EXCLUSIVELY;
    }

    private void processMapperTypeElement(ProcessorContext context, TypeElement mapperTypeElement) {
        Object mapper = mapperTypeElement;
        for ( ModelElementProcessor<?, ?> processor : getProcessors() ) {
            mapper = process( context, processor, mapperTypeElement, mapper );
        }
    }

    private <P, R> R process(ProcessorContext context, ModelElementProcessor<P, R> processor,
                             TypeElement mapperTypeElement, Object modelElement) {
        @SuppressWarnings("unchecked")
        P sourceElement = (P) modelElement;
        return processor.process( context, mapperTypeElement, sourceElement );
    }

    //TODO Retrieve via service loader
    private Iterable<ModelElementProcessor<?, ?>> getProcessors() {
        return Arrays.<ModelElementProcessor<?, ?>>asList(
            new MethodRetrievalProcessor(),
            new MapperCreationProcessor(),
            new MapperRenderingProcessor()
        );
    }

    private TypeElement asTypeElement(Element element) {
        return element.accept(
            new ElementKindVisitor6<TypeElement, Void>() {
                @Override
                public TypeElement visitTypeAsInterface(TypeElement e, Void p) {
                    return e;
                }

            }, null
        );
    }
}
