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
package org.mapstruct.ap.test.bugs._513;

import java.util.Arrays;
import java.util.HashMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Reproducer for https://github.com/mapstruct/mapstruct/issues/513.
 *
 * @author Sjaak Derksen
 */
@IssueKey( "513" )
    @WithClasses( {
        Issue513Mapper.class,
        Source.class,
        Target.class,
        SourceElement.class,
        TargetElement.class,
        SourceKey.class,
        TargetKey.class,
        SourceValue.class,
        TargetValue.class,
        MappingException.class,
        MappingKeyException.class,
        MappingValueException.class
    } )
@RunWith(AnnotationProcessorTestRunner.class)
public class Issue513Test {

    @Test( expected = MappingException.class )
    public void shouldThrowMappingException() throws Exception {

        Source source = new Source();
        SourceElement sourceElement = new SourceElement();
        sourceElement.setValue( "test" );
        source.setCollection( Arrays.asList( new SourceElement[]{ sourceElement } ) );

        Issue513Mapper.INSTANCE.map( source );

    }

    @Test( expected = MappingKeyException.class )
    public void shouldThrowMappingKeyException() throws Exception {

        Source source = new Source();
        SourceKey sourceKey = new SourceKey();
        sourceKey.setValue( MappingKeyException.class.getSimpleName() );
        SourceValue sourceValue = new SourceValue();
        HashMap<SourceKey, SourceValue> map = new HashMap<SourceKey, SourceValue>();
        map.put( sourceKey, sourceValue );
        source.setMap( map );

        Issue513Mapper.INSTANCE.map( source );

    }

    @Test( expected = MappingValueException.class )
    public void shouldThrowMappingValueException() throws Exception {

        Source source = new Source();
        SourceKey sourceKey = new SourceKey();
        SourceValue sourceValue = new SourceValue();
        sourceValue.setValue( MappingValueException.class.getSimpleName() );
        HashMap<SourceKey, SourceValue> map = new HashMap<SourceKey, SourceValue>();
        map.put( sourceKey, sourceValue );
        source.setMap( map );

        Issue513Mapper.INSTANCE.map( source );

    }

    @Test( expected = MappingException.class )
    public void shouldThrowMappingCommonException() throws Exception {

        Source source = new Source();
        SourceKey sourceKey = new SourceKey();
        SourceValue sourceValue = new SourceValue();
        sourceValue.setValue( MappingException.class.getSimpleName() );
        HashMap<SourceKey, SourceValue> map = new HashMap<SourceKey, SourceValue>();
        map.put( sourceKey, sourceValue );
        source.setMap( map );

        Issue513Mapper.INSTANCE.map( source );

    }
}
