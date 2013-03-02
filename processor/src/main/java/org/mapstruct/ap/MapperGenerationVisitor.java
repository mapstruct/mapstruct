/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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

import java.beans.Introspector;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

import org.mapstruct.ap.conversion.Conversion;
import org.mapstruct.ap.conversion.Conversions;
import org.mapstruct.ap.model.BeanMapping;
import org.mapstruct.ap.model.Mapper;
import org.mapstruct.ap.model.MappingMethod;
import org.mapstruct.ap.model.PropertyMapping;
import org.mapstruct.ap.model.Type;
import org.mapstruct.ap.model.source.MappedProperty;
import org.mapstruct.ap.model.source.Mapping;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.model.source.Parameter;
import org.mapstruct.ap.writer.ModelWriter;

import static javax.lang.model.util.ElementFilter.methodsIn;

public class MapperGenerationVisitor extends ElementKindVisitor6<Void, Void> {

	private final static String IMPLEMENTATION_SUFFIX = "Impl";

	private final static String MAPPING_ANNOTATION = "org.mapstruct.Mapping";
	private final static String MAPPINGS_ANNOTATION = "org.mapstruct.Mappings";

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

		ModelWriter modelWriter = new ModelWriter( "mapper-implementation.ftl" );
		modelWriter.writeModel( sourceFile, model );
	}

	private Mapper retrieveModel(TypeElement element) {
		List<Method> methods = retrieveMethods( element );
		Set<Method> processedMethods = new HashSet<Method>();
		List<BeanMapping> mappings = new ArrayList<BeanMapping>();

		for ( Method method : methods ) {
			if ( processedMethods.contains( method ) ) {
				continue;
			}

			MappingMethod mappingMethod = new MappingMethod(
					method.getName(),
					method.getParameterName(),
					getElementMappingMethod( methods, method )
			);

			MappingMethod reverseMappingMethod = null;
			Method rawReverseMappingMethod = getReverseMappingMethod( methods, method );
			if ( rawReverseMappingMethod != null ) {
				processedMethods.add( rawReverseMappingMethod );
//	            MappingMethod reverseElementMappingMethod = rawReverseElementMappingMethod == null ? null : new MappingMethod(rawReverseElementMappingMethod.getName(), rawReverseElementMappingMethod.getParameterName() );

				reverseMappingMethod = new MappingMethod(
						rawReverseMappingMethod.getName(),
						rawReverseMappingMethod.getParameterName(),
						getElementMappingMethod( methods, rawReverseMappingMethod )
				);
			}


			List<PropertyMapping> propertyMappings = new ArrayList<PropertyMapping>();

			for ( MappedProperty property : method.getMappedProperties() ) {
				Method propertyMappingMethod = getPropertyMappingMethod( methods, property );
				Method reversePropertyMappingMethod = getReversePropertyMappingMethod( methods, property );

				Conversion conversion = Conversions.getConversion( property.getSourceType(), property.getTargetType() );

				propertyMappings.add(
						new PropertyMapping(
								property.getSourceName(),
								property.getSourceType(),
								property.getTargetName(),
								property.getTargetType(),
								property.getConverterType(),
								propertyMappingMethod != null ? new MappingMethod(
										propertyMappingMethod.getName(),
										propertyMappingMethod.getParameterName()
								) : null,
								reversePropertyMappingMethod != null ? new MappingMethod(
										reversePropertyMappingMethod.getName(),
										reversePropertyMappingMethod.getParameterName()
								) : null,
								conversion != null ? conversion.to(
										mappingMethod.getParameterName() + "." + getAccessor(
												property.getSourceName()
										)
								) : null,
								conversion != null ? conversion.from(
										reverseMappingMethod.getParameterName() + "." + getAccessor(
												property.getTargetName()
										)
								) : null
						)
				);
			}
			BeanMapping mapping = new BeanMapping(
					method.getSourceType(),
					method.getTargetType(),
					propertyMappings,
					mappingMethod,
					reverseMappingMethod
			);

			mappings.add( mapping );
		}

		Mapper mapper = new Mapper(
				elementUtils.getPackageOf( element ).getQualifiedName().toString(),
				element.getSimpleName().toString(),
				element.getSimpleName() + IMPLEMENTATION_SUFFIX,
				mappings
		);

		return mapper;
	}

	private String getAccessor(String name) {
		return "get" + capitalize( name ) + "()";
	}

	private String capitalize(String name) {
		return name.substring( 0, 1 ).toUpperCase() + name.substring( 1 );
	}

	private MappingMethod getElementMappingMethod(Iterable<Method> methods, Method method) {
		Method elementMappingMethod = null;
		for ( Method oneMethod : methods ) {
			if ( oneMethod.getSourceType().equals( method.getSourceType().getElementType() ) ) {
				elementMappingMethod = oneMethod;
				break;
			}
		}
		return elementMappingMethod == null ? null : new MappingMethod(
				elementMappingMethod.getName(),
				elementMappingMethod.getParameterName()
		);
	}

	private Method getPropertyMappingMethod(Iterable<Method> rawMethods, MappedProperty property) {
		for ( Method oneMethod : rawMethods ) {
			if ( oneMethod.getSourceType().equals( property.getSourceType() ) && oneMethod.getTargetType()
					.equals( property.getTargetType() ) ) {
				return oneMethod;
			}
		}
		return null;
	}

	private Method getReversePropertyMappingMethod(Iterable<Method> methods, MappedProperty property) {
		for ( Method method : methods ) {
			if ( method.getSourceType().equals( property.getTargetType() ) && method.getTargetType()
					.equals( property.getSourceType() ) ) {
				return method;
			}
		}
		return null;
	}

	private Method getReverseMappingMethod(List<Method> rawMethods,
										   Method method) {
		for ( Method oneMethod : rawMethods ) {
			if ( oneMethod.reverses( method ) ) {
				return oneMethod;
			}
		}
		return null;
	}

	private List<Method> retrieveMethods(TypeElement element) {
		List<Method> methods = new ArrayList<Method>();

		for ( ExecutableElement method : methodsIn( element.getEnclosedElements() ) ) {
			Parameter parameter = retrieveParameter( method );
			Type returnType = retrieveReturnType( method );
			List<MappedProperty> properties = retrieveMappedProperties( method );

			methods.add(
					new Method(
							method.getSimpleName().toString(),
							parameter.getName(),
							parameter.getType(),
							returnType,
							properties
					)
			);
		}

		return methods;
	}

	private List<MappedProperty> retrieveMappedProperties(ExecutableElement method) {

		Map<String, Mapping> mappings = new HashMap<String, Mapping>();

		for ( AnnotationMirror annotationMirror : method.getAnnotationMirrors() ) {

			String annotationName = annotationMirror.getAnnotationType()
					.asElement()
					.accept( new NameDeterminationVisitor(), null );

			if ( MAPPING_ANNOTATION.equals( annotationName ) ) {
				Mapping mapping = getMapping( annotationMirror );
				mappings.put( mapping.getSourceName(), mapping );
			}
			else if ( MAPPINGS_ANNOTATION.equals( annotationName ) ) {
				mappings.putAll( getMappings( annotationMirror ) );
			}
		}

		return getMappedProperties( method, mappings );
	}

	private List<MappedProperty> getMappedProperties(ExecutableElement method, Map<String, Mapping> mappings) {
		Element returnTypeElement = typeUtils.asElement( method.getReturnType() );
		Element parameterElement = typeUtils.asElement( method.getParameters().get( 0 ).asType() );

		List<MappedProperty> properties = new ArrayList<MappedProperty>();

		for ( ExecutableElement getterMethod : getterMethodsIn( parameterElement.getEnclosedElements() ) ) {

			String sourcePropertyName = Introspector.decapitalize(
					getterMethod.getSimpleName()
							.toString()
							.substring( 3 )
			);
			Mapping mapping = mappings.get( sourcePropertyName );

			for ( ExecutableElement setterMethod : setterMethodsIn( returnTypeElement.getEnclosedElements() ) ) {

				String targetPropertyName = Introspector.decapitalize(
						setterMethod.getSimpleName()
								.toString()
								.substring( 3 )
				);

				if ( targetPropertyName.equals( mapping != null ? mapping.getTargetName() : sourcePropertyName ) ) {
					properties.add(
							new MappedProperty(
									sourcePropertyName,
									retrieveReturnType( getterMethod ),
									mapping != null ? mapping.getTargetName() : targetPropertyName,
									retrieveParameter( setterMethod ).getType(),
									mapping != null ? mapping.getConverterType() : null
							)
					);
				}
			}
		}

		return properties;
	}

	private Map<String, Mapping> getMappings(AnnotationMirror annotationMirror) {
		Map<String, Mapping> mappings = new HashMap<String, Mapping>();

		List<? extends AnnotationValue> values = getAnnotationValueListValue( annotationMirror, "value" );

		for ( AnnotationValue oneAnnotationValue : values ) {
			AnnotationMirror oneAnnotation = oneAnnotationValue.accept(
					new AnnotationRetrievingVisitor(),
					null
			);
			Mapping mapping = getMapping( oneAnnotation );
			mappings.put( mapping.getSourceName(), mapping );
		}

		return mappings;
	}

	private Mapping getMapping(AnnotationMirror annotationMirror) {
		String sourcePropertyName = getStringValue( annotationMirror, "source" );
		String targetPropertyName = getStringValue( annotationMirror, "target" );
		TypeMirror converterTypeMirror = getTypeMirrorValue( annotationMirror, "converter" );

		Type converterType = null;

		if ( converterTypeMirror != null ) {
			converterType = getType( (DeclaredType) converterTypeMirror );
		}

		return new Mapping( sourcePropertyName, targetPropertyName, converterType );
	}

	private Type getType(DeclaredType type) {
		Type elementType = null;
		if ( !type.getTypeArguments().isEmpty() ) {
			elementType = retrieveType( type.getTypeArguments().iterator().next() );
		}

		return new Type(
				elementUtils.getPackageOf( type.asElement() ).toString(),
				type.asElement().getSimpleName().toString(),
				elementType
		);
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
			return getType( ( (DeclaredType) mirror ) );
		}
		else {
			return new Type( null, mirror.toString() );
		}
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

	private List<ExecutableElement> getterMethodsIn(Iterable<? extends Element> elements) {
		List<ExecutableElement> getterMethods = new LinkedList<ExecutableElement>();

		for ( ExecutableElement method : methodsIn( elements ) ) {
			//TODO: consider is/has
			String name = method.getSimpleName().toString();

			if ( name.startsWith( "get" ) && name.length() > 3 && method.getParameters()
					.isEmpty() && method.getReturnType().getKind() != TypeKind.VOID ) {
				getterMethods.add( method );
			}
		}

		return getterMethods;
	}

	private List<ExecutableElement> setterMethodsIn(Iterable<? extends Element> elements) {
		List<ExecutableElement> setterMethods = new LinkedList<ExecutableElement>();

		for ( ExecutableElement method : methodsIn( elements ) ) {
			//TODO: consider is/has
			String name = method.getSimpleName().toString();

			if ( name.startsWith( "set" ) && name.length() > 3 && method.getParameters()
					.size() == 1 && method.getReturnType().getKind() == TypeKind.VOID ) {
				setterMethods.add( method );
			}
		}

		return setterMethods;
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
