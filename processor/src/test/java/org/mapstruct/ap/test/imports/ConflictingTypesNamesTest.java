/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.imports.from.Foo;
import org.mapstruct.ap.test.imports.from.FooWrapper;
import org.mapstruct.ap.test.imports.referenced.GenericMapper;
import org.mapstruct.ap.test.imports.referenced.NotImportedDatatype;
import org.mapstruct.ap.test.imports.referenced.Source;
import org.mapstruct.ap.test.imports.referenced.Target;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.WithJavaxInject;
import org.mapstruct.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for generating a mapper which references types whose names clash with names of used annotations and exceptions.
 *
 * @author Gunnar Morling
 */
@IssueKey("112")
@WithClasses({
    Named.class,
    ParseException.class,
    SourceTargetMapper.class,
    List.class,
    Map.class,
    Foo.class,
    org.mapstruct.ap.test.imports.to.Foo.class,
    Source.class,
    Target.class,
    NotImportedDatatype.class,
    GenericMapper.class,
    FooWrapper.class,
    org.mapstruct.ap.test.imports.to.FooWrapper.class,
    SecondSourceTargetMapper.class
})
@WithJavaxInject
public class ConflictingTypesNamesTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void mapperImportingTypesWithConflictingNamesCanBeGenerated() {
        Named source = new Named();
        source.setFoo( 123 );
        source.setBar( 456L );

        ParseException target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();

        Foo foo = new Foo();
        foo.setName( "bar" );

        org.mapstruct.ap.test.imports.to.Foo foo2 = SourceTargetMapper.INSTANCE.fooToFoo( foo );
        assertThat( foo2 ).isNotNull();
        assertThat( foo2.getName() ).isEqualTo( "bar" );
    }

    @ProcessorTest
    @IssueKey("178")
    public void mapperHasNoUnnecessaryImports() {
        Source source = new Source();
        source.setNotImported( new NotImportedDatatype( 42 ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getNotImported() ).isSameAs( source.getNotImported() );

        target = SecondSourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getNotImported() ).isSameAs( source.getNotImported() );

        generatedSource.forMapper( SourceTargetMapper.class ).containsNoImportFor( NotImportedDatatype.class );
        generatedSource.forMapper( SecondSourceTargetMapper.class ).containsNoImportFor( NotImportedDatatype.class );
    }

    @ProcessorTest
    @IssueKey("156")
    public void importsForTargetTypes() {
        FooWrapper source = new FooWrapper();
        Foo value = new Foo();
        value.setName( "foo" );
        source.setValue( value );

        org.mapstruct.ap.test.imports.to.FooWrapper result =
            SecondSourceTargetMapper.INSTANCE.fooWrapperToFooWrapper( source );

        assertThat( result ).isNotNull();
        assertThat( result.getValue() ).isNotNull();
        assertThat( result.getValue().getName() ).isEqualTo( "foo" );
    }
}
