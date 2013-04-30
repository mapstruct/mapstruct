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
package org.mapstruct.ap.test.collection.defaultimplementation;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.MapperTestBase;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class DefaultCollectionImplementationTest extends MapperTestBase {

    @Override
    protected List<Class<?>> getTestClasses() {
        return Arrays.<Class<?>>asList(
            Source.class,
            SourceFoo.class,
            Target.class,
            TargetFoo.class,
            SourceTargetMapper.class
        );
    }

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForList() {
        Source source = new Source();
        source.setFooList( Arrays.asList( new SourceFoo( "Bob" ), new SourceFoo( "Alice" ) ) );
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFooList() ).containsExactly( new TargetFoo( "Bob" ), new TargetFoo( "Alice" ) );
    }

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForSet() {
        Source source = new Source();
        source.setFooSet( new HashSet<SourceFoo>( Arrays.asList( new SourceFoo( "Bob" ), new SourceFoo( "Alice" ) ) ) );
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFooSet() ).containsOnly( new TargetFoo( "Bob" ), new TargetFoo( "Alice" ) );
    }

    @Test
    @IssueKey("6")
    public void shouldUseDefaultImplementationForCollection() {
        Source source = new Source();
        source.setFooCollection( Arrays.asList( new SourceFoo( "Bob" ), new SourceFoo( "Alice" ) ) );
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFooCollection() ).containsOnly( new TargetFoo( "Bob" ), new TargetFoo( "Alice" ) );
    }
}
