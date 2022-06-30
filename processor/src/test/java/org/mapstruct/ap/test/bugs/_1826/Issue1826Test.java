/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1826;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

@IssueKey("1826")
@WithClasses({
        SourceParent.class,
        SourceChild.class,
        Target.class,
        Issue1826Mapper.class
})
public class Issue1826Test {

  @ProcessorTest
  public void testNestedPropertyMappingChecksForNull() {
    SourceParent sourceParent = new SourceParent();
    sourceParent.setSourceChild( null );

    Target result = Issue1826Mapper.INSTANCE.sourceAToTarget( sourceParent );
    assertThat( result.getContent() ).isNull();
  }
}
