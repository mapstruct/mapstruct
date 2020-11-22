/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2221;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2221")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    RestConfig.class,
    RestSiteDto.class,
    RestSiteMapper.class,
    SiteDto.class,
})
public class Issue2221Test {

    @Test
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
