/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2221;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2221")
@WithClasses({
    RestConfig.class,
    RestSiteDto.class,
    RestSiteMapper.class,
    SiteDto.class,
})
public class Issue2221Test {

    @ProcessorTest
    public void multiSourceInheritConfigurationShouldWork() {
        SiteDto site = RestSiteMapper.INSTANCE.convert(
            new RestSiteDto( "restTenant", "restSite", "restCti" ),
            "parameterTenant",
            "parameterSite"
        );

        assertThat( site ).isNotNull();
        assertThat( site.getTenantId() ).isEqualTo( "parameterTenant" );
        assertThat( site.getSiteId() ).isEqualTo( "parameterSite" );
        assertThat( site.getCtiId() ).isEqualTo( "restCti" );

    }
}
