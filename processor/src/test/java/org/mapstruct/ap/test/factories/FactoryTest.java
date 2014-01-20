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
package org.mapstruct.ap.test.factories;

import static org.fest.assertions.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.MapperTestBase;
import org.mapstruct.ap.testutil.WithClasses;
import org.testng.annotations.Test;

/**
 * @author Sjaak Derksen
 *
 */
@IssueKey( "81" )
@WithClasses( { Bar1.class, Foo1.class, Bar2.class, Foo2.class, Bar1Factory.class, Source.class,
    SourceTargetMapperAndBar2Factory.class, Target.class } )
public class FactoryTest extends MapperTestBase {
    @Test
    public void shouldUseTwoFactoryMethods() {
        Target target = SourceTargetMapperAndBar2Factory.INSTANCE.sourceToTarget( createSource() );

        assertThat( target ).isNotNull();
        assertThat( target.getProp1() ).isNotNull();
        assertThat( target.getProp1().getProp() ).isEqualTo( "foo1" );
        assertThat( target.getProp1().getSomeTypeProp()).isEqualTo( "BAR1" );
        assertThat( target.getProp2() ).isNotNull();
        assertThat( target.getProp2().getProp() ).isEqualTo( "foo2" );
        assertThat( target.getProp2().getSomeTypeProp()).isEqualTo( "BAR2" );
    }

    private Source createSource() {
        Source source = new Source();

        Foo1 foo1 = new Foo1();
        foo1.setProp( "foo1" );
        source.setProp1( foo1 );

        Foo2 foo2 = new Foo2();
        foo2.setProp( "foo2" );
        source.setProp2( foo2 );

        return source;
    }
}
