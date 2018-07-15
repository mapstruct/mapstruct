/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.supertypegeneration;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * Generates base classes.
 * 
 * @author Gunnar Morling
 *
 */
@SupportedAnnotationTypes("org.mapstruct.itest.supertypegeneration.GenBase")
public class BaseGenerationProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		for (TypeElement annotation : annotations) {
			Set<? extends Element> annotated = roundEnv.getElementsAnnotatedWith( annotation );
			for (Element element : annotated) {
				AnnotationMirror annotationMirror = element.getAnnotationMirrors().get(0);
				Integer level = (Integer) annotationMirror.getElementValues().values().iterator().next().getValue();
				
				if ( level >= 1 ) {
					try {
						PackageElement packageElement = processingEnv.getElementUtils().getPackageOf( element );
						Name name = element.getSimpleName();
						String baseName = name.toString() + "Base";
						String baseBaseName = level > 1 ? baseName + "Base" : null;

						JavaFileObject baseGen = processingEnv.getFiler().createSourceFile( packageElement.getQualifiedName() + "." + baseName );
						Writer writer = baseGen.openWriter();
						writer.append( "package " + packageElement.getQualifiedName() + ";\n" );
						writer.append( "\n" );
						writer.append( "import org.mapstruct.itest.supertypegeneration.GenBase;\n" );
						writer.append( "\n" );
						if ( level > 1 ) {
							writer.append( "@GenBase( " + (level - 1) + " )\n" );
							writer.append( "public class " + baseName + " extends " + baseBaseName + " {\n" );
						}
						else {
							writer.append( "public class " + baseName + " {\n" );
						}
						writer.append( "    private String baseName" + level + ";\n" );
						writer.append( "    public String getBaseName" + level + "() { return baseName" + level + "; }\n" );
						writer.append( "    public void setBaseName" + level + "(String baseName" + level + ") { this.baseName" + level + " = baseName" + level + ";}\n" );
						writer.append( "}\n" );
						writer.flush();
						writer.close();
					} 
					catch (IOException e) {
						throw new RuntimeException( e );
					}
				}
			}
		}

		return false;
	}
}
