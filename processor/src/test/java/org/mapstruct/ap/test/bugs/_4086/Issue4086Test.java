/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4086;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;

/**
 * Reproducer for <a href="https://github.com/mapstruct/mapstruct/issues/4086">4086</a>.
 * <p>
 * When a {@code @Nullable} source property is mapped to a {@code @NonNull} constructor
 * parameter via a reused mapping method that accepts {@code @Nullable} and returns
 * non-null, the mapping is safe. The method explicitly handles null and returns a
 * guaranteed non-null value. No error should be raised.
 *
 * @author Agustin Ranieri
 */
@IssueKey( "4086" )
@WithJSpecify
public class Issue4086Test {

    @ProcessorTest
    @WithClasses( { Issue4086Mapper.class, Source.class, Target.class } )
    public void nullableSourceToNullableParamNonNullReturnShouldCompile() {
    }
}
