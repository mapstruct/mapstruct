/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3806;

import java.util.HashMap;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({ DestinationType.class, SourceType.class, Issue3806Mapper.class })
public class Issue3806Test {

    @ProcessorTest
    public void shouldNotClearGetterOnlyCollectionsInUpdateMapping() {
        DestinationType target = new TargetTypeObj();

        SourceType source = new SourceType();
        HashMap<String, String> map = new HashMap<>();
        map.put( "key", "value" );
        source.setNormalValue( map );
        Issue3806Mapper.INSTANCE.update( target, source );
        assertThat( target.getNoSetterValue() ).isNotEqualTo( null );
        assertThat( target.getNormalValue() ).isNotEqualTo( null );
        assertThat( target.getNormalValue().get( "key" ) ).isEqualTo( "value" );
    }
}
