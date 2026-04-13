/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for JSpecify nullness annotations with nested property chains.
 *
 * @author Filip Hrisafov
 */
@IssueKey("1243")
@WithClasses({
    AddressBean.class,
    NestedSourceBean.class,
    FlatTargetBean.class,
    JSpecifyNestedMapper.class
})
@WithJSpecify
class JSpecifyNestedTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void verifyGeneratedCode() {
        generatedSource.addComparisonToFixtureFor( JSpecifyNestedMapper.class );
    }

    @ProcessorTest
    public void nestedNonNullLeafSkipsNullCheck() {
        AddressBean address = new AddressBean();
        address.setStreet( "Main St" );

        NestedSourceBean source = new NestedSourceBean();
        source.setNonNullAddress( address );

        FlatTargetBean target = JSpecifyNestedMapper.INSTANCE.map( source );

        assertThat( target.isStreetSet() ).isTrue();
        assertThat( target.getStreet() ).isEqualTo( "Main St" );
    }

    @ProcessorTest
    public void nestedNullableLeafToNonNullTargetAddsNullCheck() {
        AddressBean address = new AddressBean();
        // city is null (@Nullable)

        NestedSourceBean source = new NestedSourceBean();
        source.setNonNullAddress( address );

        FlatTargetBean target = JSpecifyNestedMapper.INSTANCE.map( source );

        // city: source @Nullable -> target @NonNull -> null check -> setter not called
        assertThat( target.isCitySet() ).isFalse();
    }
}
