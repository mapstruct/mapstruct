/**
 *  Copyright 2012 Gunnar Morling (http://www.gunnarmorling.de/)
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
package de.moapa.maple.ap;


import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

@SupportedAnnotationTypes("de.moapa.maple.Mapper")
public class MappingProcessor extends AbstractProcessor {

	/**
	 * Whether this processor claims all processed annotations exclusively or not.
	 */
	private static final boolean ANNOTATIONS_CLAIMED_EXCLUSIVELY = false;

	private Configuration configuration;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnv) {

		super.init( processingEnv );

		configuration = new Configuration();
		configuration.setClassForTemplateLoading( MappingProcessor.class, "/" );
		configuration.setObjectWrapper( new DefaultObjectWrapper() );
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

	@Override
	public boolean process(
			final Set<? extends TypeElement> annotations,
			final RoundEnvironment roundEnvironment) {

		for ( TypeElement oneAnnotation : annotations ) {

			//Indicates that the annotation's type isn't on the class path of the compiled
			//project. Let the compiler deal with that and print an appropriate error.
			if ( oneAnnotation.getKind() != ElementKind.ANNOTATION_TYPE ) {
				continue;
			}

			for ( Element oneAnnotatedElement : roundEnvironment.getElementsAnnotatedWith( oneAnnotation ) ) {
				oneAnnotatedElement.accept( new MapperGenerationVisitor( processingEnv, configuration ), null );
			}
		}

		return ANNOTATIONS_CLAIMED_EXCLUSIVELY;
	}
}