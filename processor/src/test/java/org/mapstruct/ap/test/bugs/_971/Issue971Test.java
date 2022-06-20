/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._971;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 *
 */
@IssueKey("971")
public class Issue971Test {

    @ProcessorTest
    @WithClasses({ CollectionSource.class, CollectionTarget.class, Issue971CollectionMapper.class })
    public void shouldCompileForCollections() {   }

    @ProcessorTest
    @WithClasses({ MapSource.class, MapTarget.class, Issue971MapMapper.class })
    public void shouldCompileForMaps() {   }
}
