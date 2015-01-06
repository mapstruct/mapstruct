/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.imports;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.imports.from.Foo;
import org.mapstruct.ap.test.imports.from.FooWrapper;
import org.mapstruct.ap.test.imports.referenced.GenericMapper;
import org.mapstruct.ap.test.imports.referenced.NotImportedDatatype;
import org.mapstruct.ap.test.imports.referenced.Source;
import org.mapstruct.ap.test.imports.referenced.Target;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.fest.assertions.Assertions.assertThat;

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
@RunWith(AnnotationProcessorTestRunner.class)
public class ConflictingTypesNamesTest {

    private final GeneratedSource generatedSource = new GeneratedSource();

    @Rule
    public GeneratedSource getGeneratedSource() {
        return generatedSource;
    }

    @Test
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

    @Test
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

    @Test
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
