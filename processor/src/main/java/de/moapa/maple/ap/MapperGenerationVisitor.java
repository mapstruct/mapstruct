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

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementKindVisitor6;
import javax.tools.JavaFileObject;

import de.moapa.maple.ap.model.Mapper;
import de.moapa.maple.ap.model.MapperMethod;
import de.moapa.maple.ap.model.Parameter;
import de.moapa.maple.ap.model.Type;
import freemarker.template.Configuration;
import freemarker.template.Template;

import static javax.lang.model.util.ElementFilter.methodsIn;

class MapperGenerationVisitor extends ElementKindVisitor6<Void, Void> {

	private final static String IMPLEMENTATION_SUFFIX = "Impl";

	private final static String TEMPLATE_NAME = "dozer-mapper-implementation.ftl";

	private final ProcessingEnvironment processingEnvironment;

	private final Configuration configuration;

	public MapperGenerationVisitor(ProcessingEnvironment processingEnvironment, Configuration configuration) {
		this.processingEnvironment = processingEnvironment;
		this.configuration = configuration;
	}

	@Override
	public Void visitTypeAsInterface(TypeElement element, Void p) {

		Mapper model = retrieveModel( element );
		String sourceFileName = element.getQualifiedName() + IMPLEMENTATION_SUFFIX;

		writeModelToSourceFile( sourceFileName, model );

		return null;
	}

	private void writeModelToSourceFile(String fileName, Mapper model) {
		try {
			JavaFileObject sourceFile = processingEnvironment.getFiler().createSourceFile( fileName );
			BufferedWriter writer = new BufferedWriter( sourceFile.openWriter() );

			Template template = configuration.getTemplate( TEMPLATE_NAME );
			template.process( model, writer );
			writer.flush();
			writer.close();
		}
		catch ( Exception e ) {
			throw new RuntimeException( e );
		}
	}

	private Mapper retrieveModel(TypeElement element) {

		return new Mapper(
				processingEnvironment.getElementUtils().getPackageOf( element ).getQualifiedName().toString(),
				element.getSimpleName() + IMPLEMENTATION_SUFFIX,
				element.getSimpleName().toString(),
				retrieveMethods( element )
		);
	}

	private List<MapperMethod> retrieveMethods(TypeElement element) {

		List<MapperMethod> methods = new ArrayList<MapperMethod>();

		for ( ExecutableElement oneMethod : methodsIn( element.getEnclosedElements() ) ) {

			methods.add(
					new MapperMethod(
							oneMethod.getSimpleName().toString(),
							retrieveReturnType( oneMethod ),
							retrieveParameter( oneMethod )
					)
			);
		}

		return methods;
	}

	private Parameter retrieveParameter(ExecutableElement method) {

		List<? extends VariableElement> parameters = method.getParameters();

		if ( parameters.size() != 1 ) {
			//TODO: Log error
			return null;
		}

		return new Parameter(
				parameters.get( 0 ).getSimpleName().toString(),
				new Type(
						processingEnvironment.getElementUtils()
								.getPackageOf( parameters.get( 0 ) )
								.getQualifiedName()
								.toString(),
						processingEnvironment.getTypeUtils()
								.asElement( parameters.get( 0 ).asType() )
								.getSimpleName()
								.toString()
				)
		);
	}

	private Type retrieveReturnType(ExecutableElement method) {

		Element returnTypeElement = processingEnvironment.getTypeUtils().asElement( method.getReturnType() );

		return new Type(
				processingEnvironment.getElementUtils().getPackageOf( returnTypeElement ).getQualifiedName().toString(),
				returnTypeElement.getSimpleName().toString()
		);
	}
}