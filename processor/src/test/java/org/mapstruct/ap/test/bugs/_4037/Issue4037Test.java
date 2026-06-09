/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4037;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Reproducer for <a href="https://github.com/mapstruct/mapstruct/issues/4037">#4037</a>.
 * <p>
 * When the mapping method declares more than one source parameter and a {@code @Condition} method
 * exists whose parameter type matches one of those sibling parameters, the type selector used to
 * silently bind the unrelated sibling to the {@code @Condition} call. The presence check was
 * therefore generated against the wrong value (for example {@code isNotBlank(slotId)} as the guard
 * for an {@code Integer} property) and could discard valid property values whenever the unrelated
 * source happened to be blank.
 */
@IssueKey("4037")
@WithClasses(Issue4037Mapper.class)
public class Issue4037Test {

    @ProcessorTest
    public void presenceCheckOnIntegerPropertyDoesNotConsumeUnrelatedStringParameter() {
        Issue4037Mapper.RequestBean requestBean = new Issue4037Mapper.RequestBean();
        Issue4037Mapper.DeviceInfo deviceInfo = new Issue4037Mapper.DeviceInfo();
        deviceInfo.setBrowserId( 123 );
        deviceInfo.setOsId( 456 );
        deviceInfo.setDeviceName( "Pixel" );
        requestBean.setDeviceInfo( deviceInfo );

        // The sibling slotId parameter is blank. Before the fix, isNotBlank(slotId) was generated as
        // the guard for every property, so blank slotId silently nulled out browserId and osId.
        Issue4037Mapper.AudienceProfileRequest profile = Issue4037Mapper.INSTANCE.map( requestBean, "" );

        assertThat( profile.getBrowserId() ).isEqualTo( 123 );
        assertThat( profile.getOsId() ).isEqualTo( 456 );
        // String property of matching type still gets the @Condition.
        assertThat( profile.getDeviceName() ).isEqualTo( "Pixel" );
    }

    @ProcessorTest
    public void presenceCheckOnStringPropertyStillApplies() {
        // The @Condition is still applied to property sources of matching type. A blank deviceName
        // should be dropped while non-blank ones (and unrelated numeric properties) pass through.
        Issue4037Mapper.RequestBean requestBean = new Issue4037Mapper.RequestBean();
        Issue4037Mapper.DeviceInfo deviceInfo = new Issue4037Mapper.DeviceInfo();
        deviceInfo.setBrowserId( 7 );
        deviceInfo.setOsId( null );
        deviceInfo.setDeviceName( "   " );
        requestBean.setDeviceInfo( deviceInfo );

        Issue4037Mapper.AudienceProfileRequest profile =
            Issue4037Mapper.INSTANCE.map( requestBean, "anything" );

        assertThat( profile.getBrowserId() ).isEqualTo( 7 );
        assertThat( profile.getOsId() ).isNull();
        assertThat( profile.getDeviceName() ).isNull();
    }
}
