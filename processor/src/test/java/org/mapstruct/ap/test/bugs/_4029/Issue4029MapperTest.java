/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4029;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author arimu1
 */
@WithClasses(Issue4029Mapper.class)
@IssueKey("4029")
class Issue4029MapperTest {

    @ProcessorTest
    void adderPreferredFallsBackToSetterWhenDirectMapToListMappingIsGiven() {
        Issue4029Mapper.MapSource source = new Issue4029Mapper.MapSource();
        Map<String, String> values = new LinkedHashMap<>();
        values.put( "a", "1" );
        values.put( "b", "2" );
        source.setValues( values );

        Issue4029Mapper.Target target = Issue4029Mapper.INSTANCE.fromMapSource( source );

        assertThat( target ).isNotNull();
        assertThat( target.getValues() ).containsExactly( "a=1", "b=2" );
        assertThat( target.isSetterUsed() )
            .as( "Map→List with custom method should fall back to setter under ADDER_PREFERRED" )
            .isTrue();
        assertThat( target.isAdderUsed() ).isFalse();
    }

    @ProcessorTest
    void adderPreferredStillUsesAdderWhenElementMappingIsApplicable() {
        Issue4029Mapper.ListSource source = new Issue4029Mapper.ListSource();
        source.setValues( Arrays.asList( "x", "y" ) );

        Issue4029Mapper.Target target = Issue4029Mapper.INSTANCE.fromListSource( source );

        assertThat( target ).isNotNull();
        assertThat( target.getValues() ).containsExactly( "x", "y" );
        assertThat( target.isAdderUsed() )
            .as( "List→List with matching element type should still prefer adder" )
            .isTrue();
        assertThat( target.isSetterUsed() ).isFalse();
    }
}
