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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementKindVisitor6;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleAnnotationValueVisitor6;
import javax.lang.model.util.TypeKindVisitor6;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

import de.moapa.maple.ap.model.Binding;
import de.moapa.maple.ap.model.Mapper;
import de.moapa.maple.ap.model.MapperMethod;
import de.moapa.maple.ap.model.Parameter;
import de.moapa.maple.ap.model.Property;
import de.moapa.maple.ap.model.Type;
import de.moapa.maple.ap.writer.DozerModelWriter;
import de.moapa.maple.ap.writer.ModelWriter;
import de.moapa.maple.ap.writer.NativeModelWriter;

import static javax.lang.model.util.ElementFilter.methodsIn;

public class MapperGenerationVisitor extends ElementKindVisitor6<Void, Void> {

	private final static String IMPLEMENTATION_SUFFIX = "Impl";

	private final static String MAPPER_ANNOTATION = "de.moapa.maple.Mapper";
	private final static String MAPPING_ANNOTATION = "de.moapa.maple.Mapping";
	private final static String MAPPINGS_ANNOTATION = "de.moapa.maple.Mappings";
	private final static String CONVERTER_TYPE = "de.moapa.maple.converter.Converter";

	private final static String DEFAULT_MAPPER_TYPE = "dozer";

	private final ProcessingEnvironment processingEnvironment;

	private final Types typeUtils;

	private final Elements elementUtils;

	public MapperGenerationVisitor(ProcessingEnvironment processingEnvironment) {

		this.processingEnvironment = processingEnvironment;
		this.typeUtils = processingEnvironment.getTypeUtils();
		this.elementUtils = processingEnvironment.getElementUtils();
	}

	@Override
	public Void visitTypeAsInterface(TypeElement element, Void p) {

		Mapper model = retrieveModel( element );
		String sourceFileName = element.getQualifiedName() + IMPLEMENTATION_SUFFIX;

		writeModelToSourceFile( sourceFileName, model );

		return null;
	}

	private void writeModelToSourceFile(String fileName, Mapper model) {

		JavaFileObject sourceFile;
		try {
			sourceFile = processingEnvironment.getFiler().createSourceFile( fileName );
		}
		catch ( IOException e ) {
			throw new RuntimeException( e );
		}

		ModelWriter modelWriter = model.getMapperType()
				.equals( "native" ) ? new NativeModelWriter() : new DozerModelWriter();
		modelWriter.writeModel( sourceFile, model );
	}

	private Mapper retrieveModel(TypeElement element) {
		return new Mapper(
				retrieveMapperType( element ),
				elementUtils.getPackageOf( element ).getQualifiedName().toString(),
				element.getSimpleName() + IMPLEMENTATION_SUFFIX,
				element.getSimpleName().toString(),
				retrieveMethods( element )
		);
	}

	private String retrieveMapperType(TypeElement element) {

		AnnotationMirror mapperAnnotation = getAnnotation( element, MAPPER_ANNOTATION );
		String mapperType = getStringValue( mapperAnnotation, "value" );

		return mapperType != null && !mapperType.isEmpty() ? mapperType : DEFAULT_MAPPER_TYPE;
	}

	private List<MapperMethod> retrieveMethods(TypeElement element) {

		List<MapperMethod> methods = new ArrayList<MapperMethod>();

		for ( ExecutableElement oneMethod : methodsIn( element.getEnclosedElements() ) ) {

			Type returnType = retrieveReturnType( oneMethod );
			Parameter parameter = retrieveParameter( oneMethod );
			Map<String, Binding> bindings = retrieveBindings( oneMethod );
			methods.add(
					new MapperMethod(
							oneMethod.getSimpleName().toString(),
							returnType,
							parameter,
							bindings
					)
			);
		}

		return methods;
	}

	private Map<String, Binding> retrieveBindings(ExecutableElement method) {

		Map<String, Binding> bindings = new LinkedHashMap<String, Binding>();

		retrieveDefaultBindings( method, bindings );

		for ( AnnotationMirror annotationMirror : method.getAnnotationMirrors() ) {

			String annotationName = annotationMirror.getAnnotationType()
					.asElement()
					.accept( new NameDeterminationVisitor(), null );

			if ( MAPPING_ANNOTATION.equals( annotationName ) ) {
				retrieveBinding( annotationMirror, bindings );
			}
			else if ( MAPPINGS_ANNOTATION.equals( annotationName ) ) {
				retrieveBindings( annotationMirror, bindings );
			}
		}

		return bindings;
	}

	private void retrieveDefaultBindings(ExecutableElement method, Map<String, Binding> bindings) {

		Element returnTypeElement = typeUtils.asElement( method.getReturnType() );

		Set<Property> writableTargetProperties = new LinkedHashSet<Property>();

		//collect writable properties of the target type
		for ( ExecutableElement oneMethod : methodsIn( returnTypeElement.getEnclosedElements() ) ) {
			if ( oneMethod.getSimpleName().toString().startsWith( "set" ) &&
					oneMethod.getParameters().size() == 1 ) {

				writableTargetProperties.add(
						new Property(
								retrieveParameter( oneMethod ).getType(),
								oneMethod.getSimpleName().toString().substring( 3 )
						)
				);
			}
		}

		//collect readable properties of the source type
		Element parameterElement = typeUtils.asElement( method.getParameters().get( 0 ).asType() );

		Set<Property> readableSourceProperties = new LinkedHashSet<Property>();

		for ( ExecutableElement oneMethod : methodsIn( parameterElement.getEnclosedElements() ) ) {
			//TODO: consider is/has
			if ( oneMethod.getSimpleName().toString().startsWith( "get" ) &&
					oneMethod.getParameters().isEmpty() &&
					oneMethod.getReturnType().getKind() != TypeKind.VOID ) {

				readableSourceProperties.add(
						new Property(
								retrieveReturnType( oneMethod ),
								oneMethod.getSimpleName().toString().substring( 3 )
						)
				);
			}
		}

		writableTargetProperties.retainAll( readableSourceProperties );

		for ( Property oneWritableProperty : writableTargetProperties ) {
			bindings.put(
					oneWritableProperty.getName(),
					new Binding( oneWritableProperty.getName(), oneWritableProperty.getName() )
			);
		}
	}

	private void retrieveBindings(AnnotationMirror annotationMirror, Map<String, Binding> bindings) {

		List<? extends AnnotationValue> values = getAnnotationValueListValue( annotationMirror, "value" );

		for ( AnnotationValue oneAnnotationValue : values ) {
			AnnotationMirror oneAnnotation = oneAnnotationValue.accept(
					new AnnotationRetrievingVisitor(),
					null
			);
			retrieveBinding( oneAnnotation, bindings );
		}
	}

	private void retrieveBinding(AnnotationMirror annotationMirror, Map<String, Binding> bindings) {

		String sourcePropertyName = getStringValue( annotationMirror, "source" );
		String targetPropertyName = getStringValue( annotationMirror, "target" );
		TypeMirror converterTypeMirror = getTypeMirrorValue( annotationMirror, "converter" );

		Type converterType = null;
		Type sourceType = null;
		Type targetType = null;

		if ( converterTypeMirror != null ) {
			converterType = getType( typeUtils.asElement( converterTypeMirror ) );

			List<? extends TypeMirror> converterTypeParameters = getTypeParameters(
					converterTypeMirror,
					CONVERTER_TYPE
			);

			sourceType = getType( typeUtils.asElement( converterTypeParameters.get( 0 ) ) );
			targetType = getType( typeUtils.asElement( converterTypeParameters.get( 1 ) ) );
		}

		bindings.put(
				sourcePropertyName,
				new Binding( sourceType, sourcePropertyName, targetType, targetPropertyName, converterType )
		);
	}

	private Type getType(Element sourceTypeElement) {
		return new Type(
				elementUtils.getPackageOf( sourceTypeElement ).toString(),
				sourceTypeElement.getSimpleName().toString()
		);
	}

	//TODO: consider complete type hierarchy
	private List<? extends TypeMirror> getTypeParameters(TypeMirror type, String superTypeName) {

		for ( TypeMirror oneSuperType : typeUtils.directSupertypes( type ) ) {
			String oneSuperTypeName = typeUtils.asElement( oneSuperType )
					.accept( new NameDeterminationVisitor(), null );

			if ( oneSuperTypeName.equals( superTypeName ) ) {
				return oneSuperType.accept( new TypeParameterDeterminationVisitor(), null );
			}
		}

		return Collections.emptyList();
	}

	private Parameter retrieveParameter(ExecutableElement method) {

		List<? extends VariableElement> parameters = method.getParameters();

		if ( parameters.size() != 1 ) {
			//TODO: Log error
			return null;
		}

		VariableElement parameter = parameters.get( 0 );

		return new Parameter(
				parameter.getSimpleName().toString(),
				retrieveType( parameter.asType() )
		);
	}

	private Type retrieveReturnType(ExecutableElement method) {

		return retrieveType( method.getReturnType() );
	}

	private Type retrieveType(TypeMirror mirror) {

		if ( mirror.getKind() == TypeKind.DECLARED ) {
			return getType( ( (DeclaredType) mirror ).asElement() );
		}
		else {
			return new Type( null, mirror.toString() );
		}
	}

	private AnnotationMirror getAnnotation(TypeElement element, String annotationName) {

		for ( AnnotationMirror annotationMirror : element.getAnnotationMirrors() ) {

			if ( annotationName.equals(
					annotationMirror.getAnnotationType().asElement().accept( new NameDeterminationVisitor(), null )
			) ) {

				return annotationMirror;
			}
		}

		return null;
	}

	private String getStringValue(AnnotationMirror annotationMirror, String attributeName) {

		for ( Entry<? extends ExecutableElement, ? extends AnnotationValue> oneAttribute : annotationMirror.getElementValues()
				.entrySet() ) {

			if ( oneAttribute.getKey().getSimpleName().contentEquals( attributeName ) ) {
				return oneAttribute.getValue().accept( new StringValueRetrievingVisitor(), null );
			}
		}

		return null;
	}

	private TypeMirror getTypeMirrorValue(AnnotationMirror annotationMirror, String attributeName) {

		for ( Entry<? extends ExecutableElement, ? extends AnnotationValue> oneAttribute : annotationMirror.getElementValues()
				.entrySet() ) {

			if ( oneAttribute.getKey().getSimpleName().contentEquals( attributeName ) ) {
				return oneAttribute.getValue().accept( new TypeRetrievingVisitor(), null );
			}
		}

		return null;
	}

	private List<? extends AnnotationValue> getAnnotationValueListValue(AnnotationMirror annotationMirror, String attributeName) {

		for ( Entry<? extends ExecutableElement, ? extends AnnotationValue> oneAttribute : annotationMirror.getElementValues()
				.entrySet() ) {

			if ( oneAttribute.getKey().getSimpleName().contentEquals( "value" ) ) {
				return oneAttribute.getValue().accept( new AnnotationValueListRetrievingVisitor(), null );
			}
		}

		return null;
	}

	private static class TypeParameterDeterminationVisitor extends TypeKindVisitor6<List<? extends TypeMirror>, Void> {

		@Override
		public List<? extends TypeMirror> visitDeclared(DeclaredType type, Void p) {
			return type.getTypeArguments();
		}
	}

	private static class NameDeterminationVisitor extends ElementKindVisitor6<String, Void> {

		@Override
		public String visitType(TypeElement element, Void p) {
			return element.getQualifiedName().toString();
		}
	}

	private static class StringValueRetrievingVisitor extends SimpleAnnotationValueVisitor6<String, Void> {

		@Override
		public String visitString(String value, Void p) {
			return value;
		}
	}

	private static class TypeRetrievingVisitor
			extends SimpleAnnotationValueVisitor6<TypeMirror, Void> {

		@Override
		public TypeMirror visitType(TypeMirror value, Void p) {
			return value;
		}
	}

	private static class AnnotationValueListRetrievingVisitor
			extends SimpleAnnotationValueVisitor6<List<? extends AnnotationValue>, Void> {

		@Override
		public List<? extends AnnotationValue> visitArray(List<? extends AnnotationValue> value, Void p) {
			return value;
		}
	}

	private static class AnnotationRetrievingVisitor extends SimpleAnnotationValueVisitor6<AnnotationMirror, Void> {

		@Override
		public AnnotationMirror visitAnnotation(AnnotationMirror value, Void p) {
			return value;
		}
	}
}
