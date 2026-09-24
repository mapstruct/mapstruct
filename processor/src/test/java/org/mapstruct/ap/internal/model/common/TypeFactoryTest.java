/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

import java.net.URI;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.junit.jupiter.api.Test;
import org.mapstruct.ap.internal.option.Options;
import org.mapstruct.ap.internal.processor.DefaultModelElementProcessorContext;
import org.mapstruct.ap.internal.util.AnnotationProcessorContext;
import org.mapstruct.ap.internal.util.RoundContext;
import org.mapstruct.ap.testutil.IssueKey;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link TypeFactory}.
 *
 * @author Ankita Advitot
 */
public class TypeFactoryTest {

    @Test
    @IssueKey("4034")
    void shouldInternTypeInstancesAcrossLookups() {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager( null, null, null );
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        SimpleJavaFileObject dummySource = new SimpleJavaFileObject(
            URI.create( "string:///Dummy.java" ),
            JavaFileObject.Kind.SOURCE
        ) {
            @Override
            public CharSequence getCharContent(boolean ignoreEncodingErrors) {
                return "public class Dummy {}";
            }
        };

        AtomicBoolean verified = new AtomicBoolean( false );

        AbstractProcessor testProcessor = new AbstractProcessor() {
            @Override
            public SourceVersion getSupportedSourceVersion() {
                return SourceVersion.latestSupported();
            }

            @Override
            public Set<String> getSupportedAnnotationTypes() {
                return Collections.singleton( "*" );
            }

            @Override
            public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
                if ( roundEnv.processingOver() || verified.get() ) {
                    return false;
                }

                AnnotationProcessorContext apContext = new AnnotationProcessorContext(
                    processingEnv.getElementUtils(),
                    processingEnv.getTypeUtils(),
                    processingEnv.getMessager(),
                    false,
                    false,
                    false,
                    Collections.emptyMap()
                );
                RoundContext roundContext = new RoundContext( apContext );
                javax.lang.model.util.Elements elements = processingEnv.getElementUtils();
                javax.lang.model.util.Types types = processingEnv.getTypeUtils();

                DefaultModelElementProcessorContext ctx = new DefaultModelElementProcessorContext(
                    processingEnv,
                    new Options( Collections.emptyMap() ),
                    roundContext,
                    Collections.emptyMap(),
                    elements.getTypeElement( "Dummy" )
                );

                TypeFactory typeFactory = ctx.getTypeFactory();

                TypeElement stringElement = elements.getTypeElement( "java.lang.String" );
                TypeElement listElement = elements.getTypeElement( "java.util.List" );
                TypeElement integerElement = elements.getTypeElement( "java.lang.Integer" );

                // 1. getType(TypeElement) returns the exact same instance on repeated calls
                Type stringType1 = typeFactory.getType( stringElement );
                Type stringType2 = typeFactory.getType( stringElement );
                assertThat( stringType1 ).isSameAs( stringType2 );

                // 2. getType(TypeMirror) for declared type matches getType(TypeElement)
                Type stringTypeFromMirror = typeFactory.getType( stringElement.asType() );
                assertThat( stringTypeFromMirror ).isSameAs( stringType1 );

                // 3. getType(Class) matches getType(TypeElement)
                Type stringTypeFromClass = typeFactory.getType( String.class );
                assertThat( stringTypeFromClass ).isSameAs( stringType1 );

                // 4. Parameterized types with identical type arguments are interned
                DeclaredType listStringType1 = types.getDeclaredType( listElement, stringElement.asType() );
                DeclaredType listStringType2 = types.getDeclaredType( listElement, stringElement.asType() );
                Type listString1 = typeFactory.getType( listStringType1 );
                Type listString2 = typeFactory.getType( listStringType2 );
                assertThat( listString1 ).isSameAs( listString2 );

                // 5. Parameterized types with different type arguments are distinct
                DeclaredType listIntType = types.getDeclaredType( listElement, integerElement.asType() );
                Type listInt = typeFactory.getType( listIntType );
                assertThat( listString1 ).isNotSameAs( listInt );

                // 6. Literal variants are distinct
                Type literalString = typeFactory.getTypeForLiteral( String.class );
                assertThat( literalString ).isNotSameAs( stringType1 );
                assertThat( literalString.isLiteral() ).isTrue();

                // 7. Always-imported variants are distinct
                Type alwaysImportedString = typeFactory.getAlwaysImportedType( stringElement.asType() );
                assertThat( alwaysImportedString ).isNotSameAs( stringType1 );

                // 8. isNullMarked() memoization survives across lookups
                assertThat( stringType1.isNullMarked() ).isEqualTo( stringType2.isNullMarked() );

                verified.set( true );
                return false;
            }
        };

        JavaCompiler.CompilationTask task = compiler.getTask(
            null,
            fileManager,
            diagnostics,
            Collections.singletonList( "-proc:only" ),
            null,
            Collections.singletonList( dummySource )
        );
        task.setProcessors( Collections.singletonList( testProcessor ) );
        boolean success = Boolean.TRUE.equals( task.call() );

        assertThat( success ).isTrue();
        assertThat( verified.get() ).isTrue();
    }
}
