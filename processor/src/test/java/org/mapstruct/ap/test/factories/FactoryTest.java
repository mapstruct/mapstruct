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
package org.mapstruct.ap.test.factories;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.factories.a.BarFactory;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Sjaak Derksen
 */
@IssueKey("81")
@WithClasses({
    Bar1.class, Foo1.class, Bar2.class, Foo2.class, Bar3.class, Foo3.class, BarFactory.class,
    org.mapstruct.ap.test.factories.b.BarFactory.class, Source.class, SourceTargetMapperAndBar2Factory.class,
    Target.class, CustomList.class, CustomListImpl.class, CustomMap.class, CustomMapImpl.class, FactoryCreatable.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class FactoryTest {
    @Test
    public void shouldUseTwoFactoryMethods() {
        Target target = SourceTargetMapperAndBar2Factory.INSTANCE.sourceToTarget( createSource() );

        assertThat( target ).isNotNull();
        assertThat( target.getProp1() ).isNotNull();
        assertThat( target.getProp1().getProp() ).isEqualTo( "foo1" );
        assertThat( target.getProp1().getSomeTypeProp() ).isEqualTo( "BAR1" );
        assertThat( target.getProp2() ).isNotNull();
        assertThat( target.getProp2().getProp() ).isEqualTo( "foo2" );
        assertThat( target.getProp2().getSomeTypeProp() ).isEqualTo( "BAR2" );
        assertThat( target.getProp3() ).isNotNull();
        assertThat( target.getProp3().getProp() ).isEqualTo( "foo3" );
        assertThat( target.getProp3().getSomeTypeProp() ).isEqualTo( "BAR3" );
        assertThat( target.getPropList() ).isNotNull();
        assertThat( target.getPropList().get( 0 ) ).isEqualTo( "fooListEntry" );
        assertThat( target.getPropList().getTypeProp() ).isEqualTo( "CUSTOMLIST" );
        assertThat( target.getPropMap() ).isNotNull();
        assertThat( target.getPropMap().get( "key" ) ).isEqualTo( "fooValue" );
        assertThat( target.getPropMap().getTypeProp() ).isEqualTo( "CUSTOMMAP" );
    }

    private Source createSource() {
        Source source = new Source();

        Foo1 foo1 = new Foo1();
        foo1.setProp( "foo1" );
        source.setProp1( foo1 );

        Foo2 foo2 = new Foo2();
        foo2.setProp( "foo2" );
        source.setProp2( foo2 );

        Foo3 foo3 = new Foo3();
        foo3.setProp( "foo3" );
        source.setProp3( foo3 );

        List<String> fooList = new ArrayList<String>();
        fooList.add( "fooListEntry" );
        source.setPropList( fooList );

        Map<String, String> fooMap = new HashMap<String, String>();
        fooMap.put( "key", "fooValue" );
        source.setPropMap( fooMap );
        return source;
    }

    @Test
    @IssueKey( "136" )
    @WithClasses( { GenericFactory.class, SourceTargetMapperWithGenericFactory.class } )
    public void shouldUseGenericFactory() {
        SourceTargetMapperWithGenericFactory mapper = SourceTargetMapperWithGenericFactory.INSTANCE;

        Foo1 foo1 = new Foo1();
        foo1.setProp( "foo1" );
        Bar1 bar1 = mapper.fromFoo1( foo1 );

        assertThat( bar1 ).isNotNull();
        assertThat( bar1.getSomeTypeProp() ).isEqualTo( "created by GenericFactory" );
        assertThat( bar1.getProp() ).isEqualTo( "foo1" );
    }
}
