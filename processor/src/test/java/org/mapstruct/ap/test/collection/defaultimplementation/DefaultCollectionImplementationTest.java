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
package org.mapstruct.ap.test.collection.defaultimplementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.MapperTestBase;
import org.mapstruct.ap.testutil.WithClasses;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

@WithClasses({
    Source.class,
    SourceFoo.class,
    Target.class,
    TargetFoo.class,
    SourceTargetMapper.class
})
public class DefaultCollectionImplementationTest extends MapperTestBase {

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForList() {
        Source source = new Source();
        source.setFooList( createSourceFooList() );
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFooList() ).containsExactly( new TargetFoo( "Bob" ), new TargetFoo( "Alice" ) );
    }

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForSet() {
        Source source = new Source();
        source.setFooSet( new HashSet<SourceFoo>( createSourceFooList() ) );
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFooSet() ).containsOnly( new TargetFoo( "Bob" ), new TargetFoo( "Alice" ) );
    }

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForCollection() {
        Source source = new Source();
        source.setFooCollection( createSourceFooList() );
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFooCollection() ).containsOnly( new TargetFoo( "Bob" ), new TargetFoo( "Alice" ) );
    }

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForIterable() {
        Source source = new Source();
        source.setFooIterable( createSourceFooList() );
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        Iterable<TargetFoo> fooIterable = target.getFooIterable();
        assertResultList( fooIterable );
    }

    private void assertResultList(Iterable<TargetFoo> fooIterable) {
        assertThat( fooIterable ).isNotNull();
        assertThat( fooIterable ).containsOnly( new TargetFoo( "Bob" ), new TargetFoo( "Alice" ) );
    }

    private List<SourceFoo> createSourceFooList() {
        return Arrays.asList( new SourceFoo( "Bob" ), new SourceFoo( "Alice" ) );
    }

    @Test
    @IssueKey("19")
    public void existingMapping1() {
        List<TargetFoo> target = new ArrayList<TargetFoo>();
        SourceTargetMapper.INSTANCE.sourceFoosToTargetFoos1( createSourceFooList(), target );

        assertResultList( target );
    }

    @Test
    @IssueKey("19")
    public void existingMapping2() {
        List<TargetFoo> target = new ArrayList<TargetFoo>();
        SourceTargetMapper.INSTANCE.sourceFoosToTargetFoos2( target, createSourceFooList() );

        assertResultList( target );
    }

    @Test
    @IssueKey("19")
    public void existingMapping3() {
        List<TargetFoo> target = new ArrayList<TargetFoo>();
        Iterable<TargetFoo> result =
            SourceTargetMapper.INSTANCE.sourceFoosToTargetFoos3( createSourceFooList(), target );

        assertThat( target == result ).isTrue();
        assertResultList( target );
    }
}
