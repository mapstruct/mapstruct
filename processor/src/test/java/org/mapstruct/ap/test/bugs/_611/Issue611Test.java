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
package org.mapstruct.ap.test.bugs._611;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Tillmann Gaida
 */
@IssueKey("611")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    SomeClass.class,
    SomeOtherClass.class
})
public class Issue611Test {
    /**
     * Checks if an implementation of a nested mapper can be loaded at all.
     */
    @Test
    public void mapperIsFound() {
        assertThat( SomeClass.InnerMapper.INSTANCE ).isNotNull();
    }

    /**
     * Checks if an implementation of a nested mapper can be loaded which is nested into an already
     * nested class.
     */
    @Test
    public void mapperNestedInsideNestedClassIsFound() {
        assertThat( SomeClass.SomeInnerClass.InnerMapper.INSTANCE ).isNotNull();
    }

    /**
     * Checks if it is possible to load two mapper implementations which have equal simple names
     * in the same package.
     */
    @Test
    public void rightMapperIsFound() {
        SomeClass.InnerMapper.Source source1 = new SomeClass.InnerMapper.Source();
        SomeOtherClass.InnerMapper.Source source2 = new SomeOtherClass.InnerMapper.Source();

        SomeClass.InnerMapper.Target target1 = SomeClass.InnerMapper.INSTANCE.toTarget( source1 );
        SomeOtherClass.InnerMapper.Target target2 = SomeOtherClass.InnerMapper.INSTANCE.toTarget( source2 );

        assertThat( target1 ).isExactlyInstanceOf( SomeClass.InnerMapper.Target.class );
        assertThat( target2 ).isExactlyInstanceOf( SomeOtherClass.InnerMapper.Target.class );
    }
}
