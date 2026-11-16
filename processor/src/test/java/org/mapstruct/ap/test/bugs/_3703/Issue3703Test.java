/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3703;

import org.mapstruct.ap.test.bugs._3703.dto.Contact;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

@IssueKey("3703")
@WithClasses({
    Contact.class,
    org.mapstruct.ap.test.bugs._3703.entity.Contact.class,
    Issue3703Mapper.class
})
public class Issue3703Test {

    @ProcessorTest
    void shouldCompile() {
    }
}
