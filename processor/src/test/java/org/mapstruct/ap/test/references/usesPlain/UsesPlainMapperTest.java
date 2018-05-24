/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.references.usesPlain;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.references.Bar;
import org.mapstruct.ap.test.references.Foo;
import org.mapstruct.ap.test.references.FooMapper;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.CompilationResult;
import org.mapstruct.ap.testutil.compilation.annotation.Diagnostic;
import org.mapstruct.ap.testutil.compilation.annotation.ExpectedCompilationOutcome;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Christian Bandowski
 *
 */
@IssueKey("1497")
@WithClasses({
    TargetObjectFactory.class, Foo.class, Bar.class, FooMapper.class, Source.class, Target.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class UsesPlainMapperTest {

    private Source source;
    private Target target;

    @Before
    public void setup() {
        this.source = new Source();
        this.source.setValue( new Foo( "prop1" ) );

        this.target = new Target( true );
        this.target.setValue( new Bar( "prop1" ) );
    }

    @Test
    @WithClasses(UsesPlainMapper.class)
    public void testThatCodeForDefaultMapperWorks() {
        Target mappedTarget = UsesPlainMapper.INSTANCE.toTarget( this.source );

        assertThat( mappedTarget ).isNotNull();
        assertThat( mappedTarget ).isEqualTo( this.target );
    }

    @Test
    @WithClasses({
        DecoratedUsesPlainMapper.class, UsesPlainMapperDecorator.class
    })
    public void testThatCodeForDecoratedMapperWorks() {
        Target mappedTarget = DecoratedUsesPlainMapper.INSTANCE.toTarget( this.source );

        assertThat( mappedTarget ).isNotNull();
        assertThat( mappedTarget ).isEqualTo( this.target );
    }

    @Test
    @WithClasses(UsesPlainSpringMapper.class)
    public void testThatCodeForSpringMapperCompilesCorrectly() throws Exception {
        UsesPlainSpringMapper mapper = UsesPlainSpringMapper.INSTANCE;

        assertThat( mapper ).isNotNull();

        // the FooMapper must be loaded using Spring
        // -> annotated with @Autowired
        Field field = mapper.getClass().getDeclaredField( "fooMapper" );

        assertThat( field ).isNotNull();
        assertThat( field.getAnnotation( Autowired.class ) ).isNotNull();

        // the TargetObjectFactory must not be loaded using Spring
        // -> not annotated with @Autowired
        field = mapper.getClass().getDeclaredField( "targetObjectFactory" );

        assertThat( field ).isNotNull();
        assertThat( field.getAnnotation( Autowired.class ) ).isNull();
    }

    @Test
    @WithClasses(UsesPlainSpringMapper.class)
    public void testThatCodeForSpringMapperWorks() throws Exception {
        UsesPlainSpringMapper mapper = UsesPlainSpringMapper.INSTANCE;

        // value would be injected, we need to this manually
        Field field = mapper.getClass().getDeclaredField( "fooMapper" );
        field.setAccessible( true );
        field.set( mapper, FooMapper.INSTANCE );

        Target mappedTarget = mapper.toTarget( this.source );

        assertThat( mappedTarget ).isNotNull();
        assertThat( mappedTarget ).isEqualTo( this.target );
    }

    @Test
    @WithClasses(ErroneousDuplicateUsesMapper.class)
    @ExpectedCompilationOutcome(
        value = CompilationResult.FAILED,
        diagnostics = {
            @Diagnostic(
                type = ErroneousDuplicateUsesMapper.class,
                line = 28,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                messageRegExp = ".* is used in 'uses' and 'usesPlain'."
            ),
            @Diagnostic(
                type = ErroneousDuplicateUsesMapper.class,
                line = 29,
                kind = javax.tools.Diagnostic.Kind.ERROR,
                messageRegExp = "Ambiguous mapping methods found for mapping property .*"
            )
        })
    public void testThatCompiliationFailedForDuplicatedUsedMappers() {
    }
}
