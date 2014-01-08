/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.MapperTestBase;
import org.mapstruct.ap.testutil.WithClasses;
import org.testng.annotations.Test;

@WithClasses({
    SourceFoo.class,
    TargetFoo.class,
 SourceTargetMapper.class
})
public class DefaultCollectionImplementationTest extends MapperTestBase {

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForList() {
        List<TargetFoo> target = SourceTargetMapper.INSTANCE.sourceFoosToTargetFoos( createSourceFooList() );

        assertThat( target ).isNotNull();
        assertThat( target ).containsExactly( new TargetFoo( "Bob" ), new TargetFoo( "Alice" ) );
    }

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForSet() {
        Set<TargetFoo> target =
            SourceTargetMapper.INSTANCE.sourceFoosToTargetFoos( new HashSet<SourceFoo>( createSourceFooList() ) );

        assertThat( target ).isNotNull();
        assertThat( target ).containsOnly( new TargetFoo( "Bob" ), new TargetFoo( "Alice" ) );
    }

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForCollection() {
        Collection<TargetFoo> target =
            SourceTargetMapper.INSTANCE.sourceFoosToTargetFoos( (Collection<SourceFoo>) createSourceFooList() );

        assertThat( target ).isNotNull();
        assertThat( target ).containsOnly( new TargetFoo( "Bob" ), new TargetFoo( "Alice" ) );
    }

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForIterable() {
        Iterable<TargetFoo> target =
            SourceTargetMapper.INSTANCE.sourceFoosToTargetFoos( (Iterable<SourceFoo>) createSourceFooList() );

        assertThat( target ).isNotNull();
        assertResultList( target );
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
    public void shouldUseTargetParameterForMapping() {
        List<TargetFoo> target = new ArrayList<TargetFoo>();
        SourceTargetMapper.INSTANCE.sourceFoosToTargetFoosUsingTargetParameter(
            target,
            createSourceFooList() );

        assertResultList( target );
    }

    @Test
    @IssueKey("19")
    public void shouldUseAndReturnTargetParameterForMapping() {
        List<TargetFoo> target = new ArrayList<TargetFoo>();
        Iterable<TargetFoo> result =
            SourceTargetMapper.INSTANCE
                .sourceFoosToTargetFoosUsingTargetParameterAndReturn( createSourceFooList(), target );

        assertThat( target == result ).isTrue();
        assertResultList( target );
    }
}
