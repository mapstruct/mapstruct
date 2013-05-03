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
package org.mapstruct.ap.test.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.MapperTestBase;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class CollectionMappingTest extends MapperTestBase {

    @Override
    protected List<Class<?>> getTestClasses() {
        return Arrays.<Class<?>>asList(
            Source.class,
            Target.class,
            SourceTargetMapper.class
        );
    }

    @Test
    @IssueKey("6")
    public void shouldMapNullList() {
        Source source = new Source();

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringList() ).isNull();
    }

    @Test
    @IssueKey("6")
    public void shouldReverseMapNullList() {
        Target target = new Target();

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getStringList() ).isNull();
    }

    @Test
    @IssueKey("6")
    public void shouldMapList() {
        Source source = new Source();
        source.setStringList( Arrays.asList( "Bob", "Alice" ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringList() ).containsExactly( "Bob", "Alice" );
    }

    @Test
    @IssueKey("6")
    public void shouldReverseMapList() {
        Target target = new Target();
        target.setStringList( Arrays.asList( "Bob", "Alice" ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getStringList() ).containsExactly( "Bob", "Alice" );
    }

    @Test
    @IssueKey("6")
    public void shouldMapListAsCopy() {
        Source source = new Source();
        source.setStringList( Arrays.asList( "Bob", "Alice" ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );
        target.getStringList().add( "Bill" );

        assertThat( source.getStringList() ).containsExactly( "Bob", "Alice" );
    }

    @Test
    @IssueKey("6")
    public void shouldReverseMapListAsCopy() {
        Target target = new Target();
        target.setStringList( Arrays.asList( "Bob", "Alice" ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );
        source.getStringList().add( "Bill" );

        assertThat( target.getStringList() ).containsExactly( "Bob", "Alice" );
    }

    @Test
    @IssueKey("6")
    public void shouldMapArrayList() {
        Source source = new Source();
        source.setStringArrayList( new ArrayList<String>( Arrays.asList( "Bob", "Alice" ) ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringArrayList() ).containsExactly( "Bob", "Alice" );
    }

    @Test
    @IssueKey("6")
    public void shouldReverseMapArrayList() {
        Target target = new Target();
        target.setStringArrayList( new ArrayList<String>( Arrays.asList( "Bob", "Alice" ) ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getStringArrayList() ).containsExactly( "Bob", "Alice" );
    }

    @Test
    @IssueKey("6")
    public void shouldMapSet() {
        Source source = new Source();
        source.setStringSet( new HashSet<String>( Arrays.asList( "Bob", "Alice" ) ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getStringSet() ).contains( "Bob", "Alice" );
    }

    @Test
    @IssueKey("6")
    public void shouldReverseMapSet() {
        Target target = new Target();
        target.setStringSet( new HashSet<String>( Arrays.asList( "Bob", "Alice" ) ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getStringSet() ).contains( "Bob", "Alice" );
    }

    @Test
    @IssueKey("6")
    public void shouldMapSetAsCopy() {
        Source source = new Source();
        source.setStringSet( new HashSet<String>( Arrays.asList( "Bob", "Alice" ) ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );
        target.getStringSet().add( "Bill" );

        assertThat( source.getStringSet() ).containsOnly( "Bob", "Alice" );
    }

    @Test
    @IssueKey("6")
    public void shouldReverseMapSetAsCopy() {
        Target target = new Target();
        target.setStringSet( new HashSet<String>( Arrays.asList( "Bob", "Alice" ) ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );
        source.getStringSet().add( "Bill" );

        assertThat( target.getStringSet() ).containsOnly( "Bob", "Alice" );
    }

    @Test
    @IssueKey("6")
    public void shouldMapListToCollection() {
        Source source = new Source();
        source.setIntegerList( Arrays.asList( 1, 2 ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getIntegerCollection() ).containsOnly( 1, 2 );
    }

    @Test
    @IssueKey("6")
    public void shouldReverseMapListToCollection() {
        Target target = new Target();
        target.setIntegerCollection( Arrays.asList( 1, 2 ) );

        Source source = SourceTargetMapper.INSTANCE.targetToSource( target );

        assertThat( source ).isNotNull();
        assertThat( source.getIntegerList() ).containsOnly( 1, 2 );
    }

    @Test
    @IssueKey("6")
    public void shouldMapIntegerSetToRawSet() {
        Source source = new Source();
        source.setIntegerSet( new HashSet<Integer>( Arrays.asList( 1, 2 ) ) );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getSet() ).containsOnly( 1, 2 );
    }
}
