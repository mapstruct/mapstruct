/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.presencecheck;

import java.util.ArrayList;
import java.util.List;

import org.fest.assertions.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test for correct handling of source presence checks.
 *
 * @author Sean Huang
 */
@WithClasses({
    SourceTargetMapper.class,
    MyObject.class,
    CustomMapper.class,
    MyLongWrapper.class,
    Source.class,
    Target.class,
    SourceWtCheck.class,
    TargetWtCheck.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class PresenceCheckTest {

    @Test
    public void testNoPresenceCheckWithOtherCheckers() {
        SourceWtCheck source = new SourceWtCheck();
        source.setHasSomeList( false );

        TargetWtCheck target = SourceTargetMapper.INSTANCE.sourceToTargetWithOtherCheckers( source );

        Assert.assertEquals( 0, target.getNoCheckPrimitive() );
        Assert.assertEquals( null, target.getNoCheckObject() );
    }

    @Test
    public void testPresenceCheckWithCustom() {
        MyObject object = new MyObject();
        MyLongWrapper longWrapper = new MyLongWrapper();
        longWrapper.setMyLong( 2L );
        List<String> list = new ArrayList<String>();
        list.add( "first" );
        list.add( "second" );

        Source source = new Source();

        source.setSomeObject( object );
        source.setSomePrimitiveDouble( 5.0 );
        source.setSomeInteger( 7 );
        source.setSomeLong( 2L );
        source.setSomeList( list );

        Target target = SourceTargetMapper.INSTANCE.sourceToTargetWithCustom( source );

        Assert.assertEquals( object, target.getSomeObject() );
        Assert.assertEquals( 5.0, target.getSomePrimitiveDouble(), 0.01 );
        Assert.assertEquals( (Integer) 7, target.getSomeInteger() );
        Assert.assertEquals( longWrapper.getMyLong(), target.getSomeLong1().getMyLong() );

        Assertions.assertThat( target.getSomeList() ).containsExactly( "first", "second" );
    }

    @Test
    public void testPresenceCheckWithCustomAndDefaultValue() {
        List<String> list = new ArrayList<String>();
        list.add( "first" );
        list.add( "second" );

        Source source = new Source();
        source.setSomeList( list );

        source.setHasSomePrimitiveDouble( false );
        source.setHasSomeInteger( false );
        source.setHasSomeLong1( false );

        Target target = SourceTargetMapper.INSTANCE.sourceToTargetWithCustomAndDefault( source );

        Assert.assertEquals( null, target.getSomeObject() );

        //Support default value for primitive type if there is hasX method and config is on
        Assert.assertEquals( 111.1, target.getSomePrimitiveDouble(), 0.01 );
        Assert.assertEquals( (Integer) 222, target.getSomeInteger() );
        Assert.assertEquals( (Long) 333L, target.getSomeLong1().getMyLong() );

        for (int i = 0; i < list.size(); i++) {
            Assert.assertEquals( list.get( i ), target.getSomeList().get( i ) );
        }
    }
}
