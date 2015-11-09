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
package org.mapstruct.ap.test.abstractclass.generics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Andreas Gudian
 *
 */
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("644")
@WithClasses({
    AbstractClassExposingItemC.class,
    AbstractClassExposingItemB.class,
    GenericsHierarchyMapper.class,
    ItemA.class,
    ItemB.class,
    ItemC.class,
    ItemProviderSomeItemA.class,
    ItemProviderAny.class,
    ItemProviderSomeItemB.class,
    SourceWithItemB.class,
    SourceWithItemC.class,
    Target.class
})
public class GenericsHierarchyTest {

    @Test
    public void determinesItemCSourceGetter() {
        AbstractClassExposingItemC source = new SourceWithItemC();

        source.setItem( new ItemC() );
        // make sure the jdk compiler resolves the same as we expect
        source.getItem().setTouchedC( false );

        Target target = GenericsHierarchyMapper.INSTANCE.toTarget( source );

        assertThat( target.getItemC().isTouchedC() ).isTrue();
        assertThat( target.getItemC().isTouchedB() ).isFalse();
    }

    @Test
    public void determinesItemBSourceGetter() {
        AbstractClassExposingItemB source = new SourceWithItemB();

        source.setItem( new ItemB() );
        // make sure the jdk compiler resolves the same as we expect
        source.getItem().setTouchedB( false );

        Target target = GenericsHierarchyMapper.INSTANCE.toTarget( source );

        assertThat( target.getItemB().isTouchedB() ).isTrue();
    }

    @Test
    public void determinesItemCSourceSetter() {
        Target target = new Target();

        target.setItemC( new ItemC() );

        SourceWithItemC source = GenericsHierarchyMapper.INSTANCE.toSourceWithItemC( target );

        assertThat( source.getItem().isTouchedC() ).isTrue();
    }

    @Test
    public void determinesItemBSourceSetter() {
        Target target = new Target();

        target.setItemB( new ItemB() );

        SourceWithItemB source = GenericsHierarchyMapper.INSTANCE.toSourceWithItemB( target );

        assertThat( source.getItem().isTouchedB() ).isTrue();
    }
}
