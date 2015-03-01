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
package org.mapstruct.ap.test.bugs._289;

import static org.fest.assertions.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Reproducer for https://github.com/mapstruct/mapstruct/issues/289.
 *
 * @author Sjaak Derksen
 */
@IssueKey( "289" )
    @WithClasses( {
        Issue289Mapper.class,
        Source.class,
        TargetWithoutSetter.class,
        TargetWithSetter.class,
        SourceElement.class,
        TargetElement.class
    } )
@RunWith(AnnotationProcessorTestRunner.class)
public class Issue289Test {


    @Test
    public void shouldLeaveEmptyTargetSetWhenSourceIsNullAndGetterOnlyForCreateMethod() {

        Source source = new Source();
        source.setCollection( null );

        TargetWithoutSetter target = Issue289Mapper.INSTANCE.sourceToTargetWithoutSetter( source );

        assertThat( target.getCollection() ).isEmpty();
    }

    @Test
    public void shouldLeaveEmptyTargetSetWhenSourceIsNullAndGetterOnlyForUpdateMethod() {

        Source source = new Source();
        source.setCollection( null );
        TargetWithoutSetter target = new TargetWithoutSetter();
        target.getCollection().add( new TargetElement() );

        Issue289Mapper.INSTANCE.sourceToTargetWithoutSetter( source, target );

        assertThat( target.getCollection() ).isEmpty();
    }


    @Test
    public void shouldLeaveNullTargetSetWhenSourceIsNullForCreateMethod() {

        Source source = new Source();
        source.setCollection( null );

        TargetWithSetter target = Issue289Mapper.INSTANCE.sourceToTargetWithSetter( source );

        assertThat( target.getCollection() ).isNull();
    }
}
