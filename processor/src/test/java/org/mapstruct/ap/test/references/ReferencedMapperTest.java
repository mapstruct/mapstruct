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
package org.mapstruct.ap.test.references;

import static org.fest.assertions.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.MapperTestBase;
import org.mapstruct.ap.testutil.WithClasses;
import org.testng.annotations.Test;

/**
 * @author Andreas Gudian
 *
 */
@IssueKey( "82" )
@WithClasses( { Bar.class, Foo.class, FooMapper.class, ReferencedCustomMapper.class, Source.class,
    SourceTargetMapper.class, Target.class } )
public class ReferencedMapperTest extends MapperTestBase {
    @Test
    public void referencedMappersAreInstatiatedCorrectly() {
        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( createSource() );

        assertThat( target ).isNotNull();
        assertThat( target.getProp1() ).isEqualTo( 43 );
        assertThat( target.getProp2() ).isNotNull();
        assertThat( target.getProp2().getProp1() ).isEqualTo( "foo" );
    }

    private Source createSource() {
        Source source = new Source();
        source.setProp1( 42 );

        Foo prop2 = new Foo();
        prop2.setProp1( "foo" );
        source.setProp2( prop2 );

        return source;
    }
}
