/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._513;

import java.util.Arrays;
import java.util.HashMap;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
public class Issue513Test {

    @ProcessorTest
    public void shouldThrowMappingException() throws Exception {

        Source source = new Source();
        SourceElement sourceElement = new SourceElement();
        sourceElement.setValue( "test" );
        source.setCollection( Arrays.asList( sourceElement ) );

        assertThatThrownBy( () -> Issue513Mapper.INSTANCE.map( source ) )
            .isInstanceOf( MappingException.class );

    }

    @ProcessorTest
    public void shouldThrowMappingKeyException() throws Exception {

        Source source = new Source();
        SourceKey sourceKey = new SourceKey();
        sourceKey.setValue( MappingKeyException.class.getSimpleName() );
        SourceValue sourceValue = new SourceValue();
        HashMap<SourceKey, SourceValue> map = new HashMap<>();
        map.put( sourceKey, sourceValue );
        source.setMap( map );

        assertThatThrownBy( () -> Issue513Mapper.INSTANCE.map( source ) )
            .isInstanceOf( MappingKeyException.class );

    }

    @ProcessorTest
    public void shouldThrowMappingValueException() throws Exception {

        Source source = new Source();
        SourceKey sourceKey = new SourceKey();
        SourceValue sourceValue = new SourceValue();
        sourceValue.setValue( MappingValueException.class.getSimpleName() );
        HashMap<SourceKey, SourceValue> map = new HashMap<>();
        map.put( sourceKey, sourceValue );
        source.setMap( map );

        assertThatThrownBy( () -> Issue513Mapper.INSTANCE.map( source ) )
            .isInstanceOf( MappingValueException.class );

    }

    @ProcessorTest
    public void shouldThrowMappingCommonException() throws Exception {

        Source source = new Source();
        SourceKey sourceKey = new SourceKey();
        SourceValue sourceValue = new SourceValue();
        sourceValue.setValue( MappingException.class.getSimpleName() );
        HashMap<SourceKey, SourceValue> map = new HashMap<>();
        map.put( sourceKey, sourceValue );
        source.setMap( map );

        assertThatThrownBy( () -> Issue513Mapper.INSTANCE.map( source ) )
            .isInstanceOf( MappingException.class );

    }
}
