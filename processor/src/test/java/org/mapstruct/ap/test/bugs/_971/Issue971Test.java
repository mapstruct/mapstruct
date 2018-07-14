/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._971;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Filip Hrisafov
 *
 */
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("971")
public class Issue971Test {

    @Test
    @WithClasses({ CollectionSource.class, CollectionTarget.class, Issue971CollectionMapper.class })
    public void shouldCompileForCollections() {   }

    @Test
    @WithClasses({ MapSource.class, MapTarget.class, Issue971MapMapper.class })
    public void shouldCompileForMaps() {   }
}
